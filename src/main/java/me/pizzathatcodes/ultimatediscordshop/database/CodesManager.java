package me.pizzathatcodes.ultimatediscordshop.database;

import me.pizzathatcodes.ultimatediscordshop.database.models.RedeemableItem;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CodesManager {

    /**
     * Create a new item in the database
     * @param redeemableItem the item model
     * @throws SQLException
     */
    public static void createRedeemableItemInformation(RedeemableItem redeemableItem) {

        try {
            PreparedStatement statement = databaseManager.getConnection()
                    .prepareStatement("INSERT INTO codes(codeID, itemstack, boughtBy, redeemedBy, amount, redeemed) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, redeemableItem.getCode());
            statement.setString(2, redeemableItem.getItemStackCode());
            statement.setString(3, redeemableItem.getBoughtBy());
            statement.setString(4, redeemableItem.getRedeemedBy());
            statement.setInt(5, redeemableItem.getAmount());
            statement.setBoolean(6, redeemableItem.isRedeemed());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot create item in the database");
            e.printStackTrace();
        }
    }

    /**
     * Update the item information
     * @param redeemableItem the item model
     * @throws SQLException
     */
    public static void updateRedeemableItemInformation(RedeemableItem redeemableItem) {

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("UPDATE codes SET itemstack = ?, boughtBy = ?, redeemedBy = ?, amount = ?, redeemed = ? WHERE codeID = ?");
            statement.setString(1, redeemableItem.getItemStackCode());
            statement.setString(2, redeemableItem.getBoughtBy());
            statement.setString(3, redeemableItem.getRedeemedBy());
            statement.setInt(4, redeemableItem.getAmount());
            statement.setBoolean(5, redeemableItem.isRedeemed());
            statement.setString(6, redeemableItem.getCode());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot update item in the database");
            e.printStackTrace();
        }
    }

    /**
     * Delete the item information
     * @param redeemableItem the item model
     * @throws SQLException
     */
    public static void deleteRedeemableItemInformation(RedeemableItem redeemableItem) {

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("DELETE FROM codes WHERE codeID = ?");
            statement.setString(1, redeemableItem.getCode());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot delete item in the database");
            e.printStackTrace();
        }
    }

    /**
     * Find the item information by the item code
     * @param code the item code
     * @return the item model
     * @throws SQLException
     */
    public static RedeemableItem findRedeemableItemInformationByCode(String code) {

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT * FROM codes WHERE codeID = ?");
            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            RedeemableItem redeemableItem;

            if (resultSet.next()) {

                redeemableItem = new RedeemableItem(
                        resultSet.getString("itemstack"),
                        resultSet.getString("codeID"),
                        resultSet.getString("redeemedBy"),
                        resultSet.getString("boughtBy"),
                        resultSet.getInt("amount"),
                        resultSet.getBoolean("redeemed")
                );

                statement.close();

                return redeemableItem;
            }

            statement.close();

        } catch (SQLException e) {
            Bukkit.getLogger().info("An error occurred while trying to find the item in the database");
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Gets all the items inside the database
     * @return the entire list of items!
     * @throws SQLException
     */
    public static ArrayList<RedeemableItem> getAllRedeemableItems() {
        try {
            ArrayList<RedeemableItem> shopItems = new ArrayList<>();
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT * FROM codes");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                shopItems.add(new RedeemableItem(
                        resultSet.getString("itemstack"),
                        resultSet.getString("codeID"),
                        resultSet.getString("redeemedBy"),
                        resultSet.getString("boughtBy"),
                        resultSet.getInt("amount"),
                        resultSet.getBoolean("redeemed")
                ));
            }
            statement.close();
            return shopItems;
        } catch (SQLException e) {
            Bukkit.getLogger().info("An error occurred while trying to get all the items in the database");
            e.printStackTrace();
        }
        return null;
    }

}
