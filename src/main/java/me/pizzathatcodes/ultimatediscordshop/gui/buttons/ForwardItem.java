package me.pizzathatcodes.ultimatediscordshop.gui.buttons;

import org.bukkit.Material;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class ForwardItem extends PageItem {

    public ForwardItem() {
        super(true);
    }

    @Override
    public ItemProvider getItemProvider(PagedGui gui) {
        ItemBuilder builder = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE);
        builder.setDisplayName("§7Next page")
                .addLoreLines(gui.hasNextPage()
                        ? "§7Go to page §e" + (gui.getCurrentPage() + 1) + "§7/§e" + gui.getPageAmount()
                        : "§cThere are no more pages");

        return builder;
    }

}
