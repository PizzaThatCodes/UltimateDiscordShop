package me.pizzathatcodes.ultimatediscordshop.gui.buttons;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class BackItem extends PageItem {

    public BackItem() {
        super(false);
    }

    @Override
    public ItemProvider getItemProvider(PagedGui gui) {
        ItemBuilder builder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE);
        builder.setDisplayName("§7Previous page")
                .addLoreLines(gui.hasPreviousPage()
                        ? "§7Go to page §e" + gui.getCurrentPage() + "§7/§e" + gui.getPageAmount()
                        : "§cYou can't go further back");

        return builder;
    }

}
