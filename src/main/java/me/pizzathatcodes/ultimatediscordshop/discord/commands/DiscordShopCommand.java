package me.pizzathatcodes.ultimatediscordshop.discord.commands;

import me.pizzathatcodes.ultimatediscordshop.utils.util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordShopCommand extends ListenerAdapter {

    private static final int ITEMS_PER_PAGE = 5;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getName().equals("shop")) {
            int totalEntries = getAmountOfEntries();
            int totalPages = (int) Math.ceil((double) totalEntries / ITEMS_PER_PAGE);

            int page = 1; // Default to the first page
            if (event.getOption("page") != null) {
                page = (int) event.getOption("page").getAsLong();
                if (page < 1) page = 1;
                if (page > totalPages) page = totalPages;
            }

            EmbedBuilder embed = createShopEmbed(page, totalPages);
            event.replyEmbeds(embed.build()).setEphemeral(false).queue();
        }
    }

    private EmbedBuilder createShopEmbed(int page, int totalPages) {
        EmbedBuilder embed = new EmbedBuilder();

        String shopPath = "embeds.shop-embed.";
        String title = util.getDiscordFile().getConfig().getString(shopPath + "title");
        String description = util.getDiscordFile().getConfig().getString(shopPath + "description");
        int color = util.getDiscordFile().getConfig().getInt(shopPath + "colour");
        String thumbnail = util.getDiscordFile().getConfig().getString(shopPath + "thumbnail");

        embed.setTitle(title + " (Page " + page + " of " + totalPages + ")");
        embed.setDescription(description);
        embed.setColor(color);
        embed.setFooter(util.getDiscordFile().getConfig().getString(shopPath + "footer"));
        embed.setThumbnail(thumbnail);

        int start = (page - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, getAmountOfEntries());

        int index = 0;
        for(String key : util.getShopFile().getConfig().getConfigurationSection("items").getKeys(false)) {
            if (index >= start && index < end) {
                String itemPath = "items." + key + ".";
                String itemName = util.getShopFile().getConfig().getString(itemPath + "name");
                String itemDescription = util.getShopFile().getConfig().getString(itemPath + "description");
                int itemCost = util.getShopFile().getConfig().getInt(itemPath + "price");

                embed.addField(itemName, itemDescription + "\nCost: " + itemCost, false);
            }
            index++;
        }

        return embed;
    }

    public int getAmountOfEntries() {
        return util.getShopFile().getConfig().getConfigurationSection("items").getKeys(false).size();
    }
}