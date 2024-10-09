package me.pizzathatcodes.ultimatediscordshop.database;

import me.pizzathatcodes.ultimatediscordshop.database.models.DiscordCoins;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscordUsersManager {

    /**
     * Create a new item in the database
     * @throws SQLException
     */
    public static void createDiscordUserInformation(DiscordCoins discordCoins) {

        try {
            PreparedStatement statement = databaseManager.getConnection()
                    .prepareStatement("INSERT INTO coins(userID, coinAmount) VALUES (?, ?)");
            statement.setString(1, discordCoins.getUserID());
            statement.setInt(2, discordCoins.getCoinAmount());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot create item in the database");
            e.printStackTrace();
        }

    }

    /**
     * Find the item information by the item code
     * @param code the item code
     * @return the item model
     * @throws SQLException
     */
    public static DiscordCoins findDiscordUserByID(String code) {

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT * FROM coins WHERE userID = ?");
            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            DiscordCoins redeemableItem;

            if (resultSet.next()) {

                redeemableItem = new DiscordCoins(
                        resultSet.getString("userID"),
                        resultSet.getInt("coinAmount")
                );

                statement.close();

                return redeemableItem;
            }

            statement.close();

        } catch (SQLException e) {
            Bukkit.getLogger().info("An error occurred while trying to find the user in the database");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update the item information
     * @param discordCoins the item model
     * @throws SQLException
     */
    public static void updateDiscordUserInformation(DiscordCoins discordCoins) {

        try {
            PreparedStatement statement = databaseManager.getConnection()
                    .prepareStatement("UPDATE coins SET coinAmount = ? WHERE userID = ?");
            statement.setInt(1, discordCoins.getCoinAmount());
            statement.setString(2, discordCoins.getUserID());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot update item in the database");
            e.printStackTrace();
        }
    }

    /**
     * Delete the item information
     * @param userID the user ID
     * @throws SQLException
     */
    public static void deleteDiscordUserInformation(String userID) {

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("DELETE FROM coins WHERE userID = ?");
            statement.setString(1, userID);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot delete item in the database");
            e.printStackTrace();
        }
    }

    /**
     * Gets all the coins inside the database
     * @return the entire list of coins!
     * @throws SQLException
     */
    public static ArrayList<DiscordCoins> getAllDiscordUsers() {
        try {
            ArrayList<DiscordCoins> shopItems = new ArrayList<>();
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT * FROM coins");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                shopItems.add(new DiscordCoins(
                        resultSet.getString("userID"),
                        resultSet.getInt("coinAmount")
                ));
            }
            statement.close();
            return shopItems;
        } catch (SQLException e) {
            Bukkit.getLogger().info("An error occurred while trying to get all the coins in the database");
            e.printStackTrace();
        }
        return null;
    }
    
}
