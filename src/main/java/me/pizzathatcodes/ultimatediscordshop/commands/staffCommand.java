package me.pizzathatcodes.ultimatediscordshop.commands;

import me.pizzathatcodes.ultimatediscordshop.Main;
import me.pizzathatcodes.ultimatediscordshop.database.DatabaseItemManager;
import me.pizzathatcodes.ultimatediscordshop.database.databaseManager;
import me.pizzathatcodes.ultimatediscordshop.database.models.DatabaseItems;
import me.pizzathatcodes.ultimatediscordshop.gui.redeemListGUI;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class staffCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(!sender.hasPermission("ultimateshop.admin")) {
            sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.no-permission")
                    .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
            ));
            return true;
        }

        if(args.length < 1) {
            sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.invalid-argument")
                    .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
            ));
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "reload": {
                try {
                    util.getDatabaseFile().reloadConfig();
                    util.getMessageFile().reloadConfig();
                    databaseManager.reloadConnection();
                    Main.getJda().shutdown();
                    Main.getJda().awaitShutdown();
                    Main.getInstance().startJDA();
                    Main.getJda().awaitReady(); // Blocking guarantees that JDA will be ready before proceeding

                    sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.reload")
                            .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    ));
                } catch (InterruptedException e) {
                    sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.reload-error")
                            .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    ));
                }
                break;
            }

            case "additem": {
                if(!checkIfPlayer(sender)) return true;
                Player player = (Player) sender;
                if(args.length < 2) {
                    sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.add-item.usage")
                            .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    ));
                    return true;
                }

                String code = args[1];

                if(DatabaseItemManager.findItemStackByCode(code) != null) {
                    sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.add-item.exists")
                            .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    ));
                    return true;
                }

                if(player.getInventory().getItemInMainHand().getType().isAir()) {
                    sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.add-item.no-item")
                            .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    ));
                    return true;
                }

                DatabaseItems databaseItems = new DatabaseItems(code, player.getInventory().getItemInMainHand());
                DatabaseItemManager.createItemStackInformation(databaseItems);

                sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.add-item.success")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                        .replace("%code%", code)
                ));

                break;
            }


            case "removeitem": {
                if(args.length < 2) {
                    sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.remove-item.usage")
                            .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    ));
                    return true;
                }

                String code = args[1];

                if(DatabaseItemManager.findItemStackByCode(code) == null) {
                    sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.remove-item.not-exists")
                            .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                    ));
                    return true;
                }

                DatabaseItemManager.deleteItemStackInformation(code);

                sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.remove-item.success")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                        .replace("%code%", code)
                ));

                break;
            }

            case "redeem": {
                if(!checkIfPlayer(sender)) return true;
                // TODO: Implement gui to show list of redeemed codes and that.
                redeemListGUI.openListGUI((Player) sender);

                sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.redeem")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                ));

                break;
            }

            default: {
                sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.invalid-argument")
                        .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
                ));
                break;
            }

        }


        return true;
    }

    public boolean checkIfPlayer(CommandSender sender) {

        if (sender instanceof Player) {
            return true;
        } else {
            sender.sendMessage(util.translate(util.getMessageFile().getConfig().getString("staff.no-console")
                    .replace("%prefix%", util.getMessageFile().getConfig().getString("general.prefix"))
            ));
            return false;
        }
    }

}
