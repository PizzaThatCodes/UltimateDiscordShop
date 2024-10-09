package me.pizzathatcodes.ultimatediscordshop.utils;

import jakarta.xml.bind.DatatypeConverter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class util {
    private static configManager databaseFile;
    private static configManager messageFile;
    private static configManager shopFile;
    private static configManager discordFile;

    /**
     * Translate a string with color codes to have colored text (hex supported)
     * @param message returns a colored string using & (hex supported)
     * @return
     */
    public static String translate(String message) {
        // TODO: check if server version is 1.16 or above
        try {
            Method method = Class.forName("net.md_5.bungee.api.ChatColor").getMethod("of", String.class);
            message = message.replaceAll("&#",  "#");
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);

            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                matcher = pattern.matcher(message);
            }
        } catch (Exception e) {
            // Server version is below 1.16
            return ChatColor.translateAlternateColorCodes('&', message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Get the database file
     * @return databaseFile
     */
    public static configManager getDatabaseFile() {
        return databaseFile;
    }

    /**
     * Set the database file
     * @param databaseFile
     */
    public static void setDatabaseFile(configManager databaseFile) {
        util.databaseFile = databaseFile;
    }

    /**
     * Get the message file
     * @return messageFile
     */
    public static configManager getMessageFile() {
        return messageFile;
    }

    /**
     * Set the message file
     * @param messageFile
     */
    public static void setMessageFile(configManager messageFile) {
        util.messageFile = messageFile;
    }

    /**
     * Get the shop file
     * @return shopFile
     */
    public static configManager getShopFile() {
        return shopFile;
    }

    /**
     * Set the shop file
     * @param shopFile
     */
    public static void setShopFile(configManager shopFile) {
        util.shopFile = shopFile;
    }

    /**
     * Get the discord file
     * @return discordFile
     */
    public static configManager getDiscordFile() {
        return discordFile;
    }

    /**
     * Set the discord file
     * @param discordFile
     */
    public static void setDiscordFile(configManager discordFile) {
        util.discordFile = discordFile;
    }


    /**
     * Generate a random code
     * @return the code
     */
    public static String generateCode() {

        String charSet = "abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNOPQRSTUVWXYZ0123456789!@#$%^&*()_-+=<>?";
        int targetStringLength = 8; // Length of the code
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);

        for (int i = 0; i < targetStringLength; i++) {
            int randomIndex = random.nextInt(charSet.length());
            buffer.append(charSet.charAt(randomIndex));
        }

        return buffer.toString();
    }



    /**
     * Encode an item to base64
     * @param itemStack the itemStack
     * @return the base64 string
     */
    public static String encodeItem(ItemStack itemStack) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("i", itemStack);
        return DatatypeConverter.printBase64Binary(config.saveToString().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Decode an item from base64
     * @param string base64 string
     * @return the itemStack
     */
    public static ItemStack decodeItem(String string) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(new String(DatatypeConverter.parseBase64Binary(string), StandardCharsets.UTF_8));
        } catch (IllegalArgumentException | InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("i", null);
    }

}
