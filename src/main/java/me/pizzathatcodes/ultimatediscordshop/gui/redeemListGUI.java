package me.pizzathatcodes.ultimatediscordshop.gui;

import me.pizzathatcodes.ultimatediscordshop.Main;
import me.pizzathatcodes.ultimatediscordshop.database.CodesManager;
import me.pizzathatcodes.ultimatediscordshop.database.DatabaseItemManager;
import me.pizzathatcodes.ultimatediscordshop.database.models.RedeemableItem;
import me.pizzathatcodes.ultimatediscordshop.gui.buttons.BackItem;
import me.pizzathatcodes.ultimatediscordshop.gui.buttons.ForwardItem;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class redeemListGUI {

    private static Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"));

    public static void openListGUI(Player player) {

        List<Item> items = new ArrayList<>();
        List<RedeemableItem> redeemableItems;

        redeemableItems = CodesManager.getAllRedeemableItems().stream().filter(n -> n.isRedeemed() == true).collect(Collectors.toList());

        for(RedeemableItem redeemableItem : redeemableItems) {
            List<String> lore = Arrays.asList(
                    util.translate("&fItem Code: &e" + redeemableItem.getItemStackCode()),
                    util.translate("&fRedeemed By: &e" + redeemableItem.getRedeemedBy()),
                    util.translate("&fBought By: &e" + redeemableItem.getBoughtBy()),
                    util.translate("&fAmount: &e" + redeemableItem.getAmount())
            );

            Item item = new SimpleItem(new ItemBuilder(Material.PAPER)
                    .setDisplayName(redeemableItem.getCode())
                    .setLegacyLore(lore)
            );

            items.add(item);
        }

        Gui gui = PagedGui.items()
                .setStructure(
                        "# # # # # # # # #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# x x x x x x x #",
                        "# # # < # > # # #")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('<', new BackItem())
                .addIngredient('>', new ForwardItem())
                .setContent(items)
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle(util.translate("Redeem list GUI"))
                .build();
        window.open();

    }

}
