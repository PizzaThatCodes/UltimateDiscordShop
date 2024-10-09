package me.pizzathatcodes.ultimatediscordshop.commands;

import me.pizzathatcodes.ultimatediscordshop.database.CodesManager;
import me.pizzathatcodes.ultimatediscordshop.database.DatabaseItemManager;
import me.pizzathatcodes.ultimatediscordshop.database.models.DatabaseItems;
import me.pizzathatcodes.ultimatediscordshop.database.models.RedeemableItem;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class redeemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {

        if(sender instanceof Player player) {

            if(!player.hasPermission("ultimateshop.redeem")) {
                player.sendMessage(util.translate(util.getMessageFile().getConfig().getString(
                        "redeem.no-permission")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                ));
                return true;
            }

            if(args.length == 0) {
                player.sendMessage(util.translate(util.getMessageFile().getConfig().getString(
                        "redeem.usage")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                ));
                return true;
            }

            String code = args[0];

            RedeemableItem item = CodesManager.findRedeemableItemInformationByCode(code);

            if(item == null) {
                player.sendMessage(util.translate(util.getMessageFile().getConfig().getString(
                        "redeem.invalid-code")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                ));
                return true;
            }

            if(item.isRedeemed()) {
                player.sendMessage(util.translate(util.getMessageFile().getConfig().getString(
                        "redeem.already-redeemed")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                ));
                return true;
            }

            item.setRedeemed(true);
            item.setRedeemedBy(player.getUniqueId().toString());

            CodesManager.updateRedeemableItemInformation(item);

            String itemName = "";
            for(String key : util.getShopFile().getConfig().getConfigurationSection("items").getKeys(false)) {
                if(util.getShopFile().getConfig().getString("items." + key + ".itemCode").equals(item.getItemStackCode())) {
                    itemName = util.getShopFile().getConfig().getString("items." + key + ".name");
                    break;
                }
            }

            player.sendMessage(util.translate(util.getMessageFile().getConfig().getString(
                    "redeem.success")
                    .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    .replace("%item%", itemName)
            ));

            String itemStackCode = util.getShopFile().getConfig().getString("items." + item.getItemStackCode() + ".itemCode");

            DatabaseItems itemStack = DatabaseItemManager.findItemStackByCode(itemStackCode);
            if(itemStack == null) {
                player.sendMessage(util.translate(util.getMessageFile().getConfig().getString(
                        "redeem.item-not-found")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                ));
                return true;
            }

            player.getInventory().addItem(itemStack.getItemStack());


        } else {
            sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString(
                    "redeem.no-console")
                    .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
            ));
        }

        return true;
    }
}
