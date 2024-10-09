package me.pizzathatcodes.ultimatediscordshop.database.models;

import org.bukkit.inventory.ItemStack;

public class DatabaseItems {
    String code;
    ItemStack itemStack;

    public DatabaseItems(String code, ItemStack itemStack) {
        this.code = code;
        this.itemStack = itemStack;
    }

    public String getCode() {
        return code;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}
