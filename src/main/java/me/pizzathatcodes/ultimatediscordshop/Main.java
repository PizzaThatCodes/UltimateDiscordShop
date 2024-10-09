package me.pizzathatcodes.ultimatediscordshop;

import me.pizzathatcodes.ultimatediscordshop.commands.redeemCommand;
import me.pizzathatcodes.ultimatediscordshop.database.databaseManager;
import me.pizzathatcodes.ultimatediscordshop.discord.commands.DiscordBalanceCommand;
import me.pizzathatcodes.ultimatediscordshop.discord.commands.DiscordBuyCommand;
import me.pizzathatcodes.ultimatediscordshop.discord.commands.DiscordShopCommand;
import me.pizzathatcodes.ultimatediscordshop.discord.listeners.DiscordOnChatListener;
import me.pizzathatcodes.ultimatediscordshop.utils.configManager;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static JDA jda;
    private static databaseManager databaseManager;

    public static Main getInstance() {
        return instance;
    }

    public static JDA getJda() {
        return jda;
    }

    public static databaseManager getDatabaseManager() {
        return databaseManager;
    }


    @Override
    public void onEnable() {
        instance = this;

        util.setDatabaseFile(new configManager("database.yml"));
        if(!util.getDatabaseFile().getConfigFile().exists())
            Main.getInstance().saveResource("database.yml", false);

        util.getDatabaseFile().updateConfig(Arrays.asList());
        util.getDatabaseFile().saveConfig();
        util.getDatabaseFile().reloadConfig();

        util.setMessageFile(new configManager("messages.yml"));
        if(!util.getMessageFile().getConfigFile().exists())
            Main.getInstance().saveResource("messages.yml", false);

        util.getMessageFile().updateConfig(Arrays.asList());
        util.getMessageFile().saveConfig();
        util.getMessageFile().reloadConfig();

        util.setShopFile(new configManager("shop-items.yml"));
        if(!util.getShopFile().getConfigFile().exists()) {
            Main.getInstance().saveResource("shop-items.yml", false);
            util.getShopFile().updateConfig(Arrays.asList());
        } else {
            util.getShopFile().updateConfig(Arrays.asList("items"));
        }

        util.getShopFile().saveConfig();
        util.getShopFile().reloadConfig();

        util.setDiscordFile(new configManager("discord-settings.yml"));
        if(!util.getDiscordFile().getConfigFile().exists())
            Main.getInstance().saveResource("discord-settings.yml", false);

        util.getDiscordFile().updateConfig(Arrays.asList());
        util.getDiscordFile().saveConfig();
        util.getDiscordFile().reloadConfig();


        databaseManager = new databaseManager();
        if(!databaseManager.initializeDatabase()) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        getCommand("redeem").setExecutor(new redeemCommand());
        getCommand("ultimatediscordshop").setExecutor(new me.pizzathatcodes.ultimatediscordshop.commands.staffCommand());


        try {
            startJDA();
            jda.awaitReady(); // Blocking guarantees that JDA will be ready before proceeding

            Guild guild = jda.getGuildById(util.getDiscordFile().getConfig().getString("general.guildID"));
            if (guild != null) {
                SlashCommandData buyCommandData = Commands.slash("buy", "Buy an item from the shop");

                OptionData buyOption = new OptionData(OptionType.STRING, "item", "The item you want to buy", true);

                for(String item : util.getShopFile().getConfig().getConfigurationSection("items").getKeys(false)) {
                    String itemName = util.getShopFile().getConfig().getString("items." + item + ".name");
                    buyOption.addChoice(itemName, item);
                }
                buyCommandData.addOptions(buyOption);
                guild.upsertCommand(buyCommandData).queue();
                guild.upsertCommand("shop", "View the shop")
                        .addOption(OptionType.INTEGER, "page", "The page you want to view", false)
                        .queue();
                guild.upsertCommand("balance", "View your balance")
                        .addOption(OptionType.USER, "user", "The user you want to view the balance of", false)
                        .queue();
            } else {
                getLogger().severe("Failed to find guild with ID " + util.getDiscordFile().getConfig().getString("general.guildID"));
                getServer().getPluginManager().disablePlugin(this);
                return;
            }

        } catch (Exception e) {
            getLogger().severe("Failed to start Discord bot! Is the token correct?");
            getServer().getPluginManager().disablePlugin(this);
            e.printStackTrace();
            return;
        }

        getLogger().info("UltimateDiscordShop has been enabled!");
    }

    @Override
    public void onDisable() {
        if(jda != null) {
            jda.shutdown();
            try {
                jda.awaitShutdown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        getLogger().info("UltimateDiscordShop has been disabled!");
    }

    public void startJDA() {
        jda = JDABuilder.createDefault(util.getDiscordFile().getConfig().getString("general.discordToken"))
                .addEventListeners(new DiscordBuyCommand(), new DiscordShopCommand(), new DiscordOnChatListener(), new DiscordBalanceCommand()) // Your listener class for handling Discord events
                .build();
    }

}
