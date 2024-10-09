package me.pizzathatcodes.ultimatediscordshop.discord.commands;

import me.pizzathatcodes.ultimatediscordshop.database.DiscordUsersManager;
import me.pizzathatcodes.ultimatediscordshop.database.models.DiscordCoins;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordBalanceCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {


        if(event.getName().equals("balance")) {
            User user = event.getOption("user") == null ? event.getUser() : event.getOption("user").getAsUser();

            EmbedBuilder embed = new EmbedBuilder();

            String balancePath = "embeds.balance-embed.";
            String title = util.getDiscordFile().getConfig().getString(balancePath + "title");
            String description = util.getDiscordFile().getConfig().getString(balancePath + "description").replace("%user%", user.getName());
            int color = util.getDiscordFile().getConfig().getInt(balancePath + "colour");
            String thumbnail = util.getDiscordFile().getConfig().getString(balancePath + "thumbnail");
            boolean ephemeral = util.getDiscordFile().getConfig().get(balancePath + "ephemeral") == null ? false : util.getDiscordFile().getConfig().getBoolean(balancePath + "ephemeral");

            DiscordCoins coins = DiscordUsersManager.findDiscordUserByID(user.getId());
            int balance = (coins == null ? util.getDiscordFile().getConfig().getInt("coin-settings.startingCoins") : coins.getCoinAmount());

            embed.setTitle(title);
            embed.setDescription(description);
            embed.addField("Balance", String.valueOf(balance), true);
            embed.setColor(color);
            embed.setFooter(util.getDiscordFile().getConfig().getString(balancePath + "footer"));
            embed.setThumbnail(thumbnail);

            event.replyEmbeds(embed.build()).setEphemeral(ephemeral).queue();
        }
    }
}
