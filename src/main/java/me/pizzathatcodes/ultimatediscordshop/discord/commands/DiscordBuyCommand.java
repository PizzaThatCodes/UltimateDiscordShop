package me.pizzathatcodes.ultimatediscordshop.discord.commands;

import me.pizzathatcodes.ultimatediscordshop.database.CodesManager;
import me.pizzathatcodes.ultimatediscordshop.database.DiscordUsersManager;
import me.pizzathatcodes.ultimatediscordshop.database.models.DiscordCoins;
import me.pizzathatcodes.ultimatediscordshop.database.models.RedeemableItem;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordBuyCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {


        if(event.getName().equals("buy")) {
            String selectedItem = event.getOption("item").getAsString();

            DiscordCoins user = DiscordUsersManager.findDiscordUserByID(event.getUser().getId());

            if(user == null) {
                user = new DiscordCoins(event.getUser().getId(), util.getDiscordFile().getConfig().getInt("coin-settings.startingCoins"));
                DiscordUsersManager.createDiscordUserInformation(user);
            }

            String displayName = util.getShopFile().getConfig().getString("items." + selectedItem + ".name");
            int price = util.getShopFile().getConfig().getInt("items." + selectedItem + ".price");

            if(user.getCoinAmount() < price) {

                EmbedBuilder embed = new EmbedBuilder();

                String buyPath = "embeds.not-enough-coins-embed.";
                String title = util.getDiscordFile().getConfig().getString(buyPath + "title");
                String description = util.getDiscordFile().getConfig().getString(buyPath + "description");
                int color = util.getDiscordFile().getConfig().getInt(buyPath + "colour");
                String thumbnail = util.getDiscordFile().getConfig().getString(buyPath + "thumbnail");

                embed.setTitle(title);
                embed.setDescription(description);
                embed.setColor(color);
                embed.setFooter(util.getDiscordFile().getConfig().getString(buyPath + "footer"));
                embed.setThumbnail(thumbnail);

                event.replyEmbeds(embed.build()).setEphemeral(true).queue();
                return;
            }

            user.removeCoins(price);
            DiscordUsersManager.updateDiscordUserInformation(user);
            event.reply("You have successfully purchased " + displayName + " for " + price + " coins").setEphemeral(true).queue();

            String code = util.generateCode();
            while (CodesManager.findRedeemableItemInformationByCode(code) != null) {
                code = util.generateCode();
            }

            RedeemableItem item = new RedeemableItem(
                    selectedItem,
                    code,
                    "",
                    event.getUser().getId(),
                    1,
                    false
            );

            CodesManager.createRedeemableItemInformation(item);

            event.getUser().openPrivateChannel().queue((channel) -> {

                EmbedBuilder embed = new EmbedBuilder();

                String buyPath = "embeds.buy-embed.";
                String title = util.getDiscordFile().getConfig().getString(buyPath + "title");
                String description = util.getDiscordFile().getConfig().getString(buyPath + "description");
                int color = util.getDiscordFile().getConfig().getInt(buyPath + "colour");
                String thumbnail = util.getDiscordFile().getConfig().getString(buyPath + "thumbnail");

                embed.setTitle(title);
                embed.setDescription(description);
                embed.addField(displayName + ", Code: " + item.getCode(), "Amount 1x", true);
                embed.setColor(color);
                embed.setFooter(util.getDiscordFile().getConfig().getString(buyPath + "footer"));
                embed.setThumbnail(thumbnail);

                channel.sendMessageEmbeds(embed.build()).queue();

            });




        }
    }
}
