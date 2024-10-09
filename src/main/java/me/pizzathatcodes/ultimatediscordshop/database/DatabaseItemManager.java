package me.pizzathatcodes.ultimatediscordshop.database;

import me.pizzathatcodes.ultimatediscordshop.database.models.DatabaseItems;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import org.bukkit.Bukkit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseItemManager {

    /**
     * Create a new item in the database
     * @throws SQLException
     */
    public static void createItemStackInformation(DatabaseItems databaseItems) {

        try {
            PreparedStatement statement = databaseManager.getConnection()
                    .prepareStatement("INSERT INTO items(codeID, itemstack) VALUES (?, ?)");
            statement.setString(1, databaseItems.getCode());
            statement.setString(2, util.encodeItem(databaseItems.getItemStack()));

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
    public static DatabaseItems findItemStackByCode(String code) {

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT * FROM items WHERE codeID = ?");
            statement.setString(1, code);

            ResultSet resultSet = statement.executeQuery();

            DatabaseItems databaseItems;

            if (resultSet.next()) {

                databaseItems = new DatabaseItems(
                        resultSet.getString("codeID"),
                        util.decodeItem(resultSet.getString("itemstack"))
                );

                statement.close();

                return databaseItems;
            }

            statement.close();

        } catch (SQLException e) {
            Bukkit.getLogger().info("An error occurred while trying to find the item in the database");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Update the item information
     * @param databaseItems the item model
     * @throws SQLException
     */
    public static void updateItemStackInformation(DatabaseItems databaseItems) {

        try {
            PreparedStatement statement = databaseManager.getConnection()
                    .prepareStatement("UPDATE items SET itemstack = ? WHERE codeID = ?");
            statement.setString(1, util.encodeItem(databaseItems.getItemStack()));
            statement.setString(2, databaseItems.getCode());

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot update item in the database");
            e.printStackTrace();
        }
    }

    /**
     * Delete the item information
     * @param code the item code
     * @throws SQLException
     */
    public static void deleteItemStackInformation(String code) {

        try {
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("DELETE FROM items WHERE codeID = ?");
            statement.setString(1, code);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            Bukkit.getLogger().info("Cannot delete item in the database");
            e.printStackTrace();
        }
    }

    /**
     * Gets all the items inside the database
     * @return the entire list of items!
     * @throws SQLException
     */
    public static ArrayList<DatabaseItems> getAllItemStacks() {
        try {
            ArrayList<DatabaseItems> shopItems = new ArrayList<>();
            PreparedStatement statement = databaseManager.getConnection().prepareStatement("SELECT * FROM items");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                shopItems.add(new DatabaseItems(
                        resultSet.getString("codeID"),
                        util.decodeItem(resultSet.getString("itemstack"))
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
