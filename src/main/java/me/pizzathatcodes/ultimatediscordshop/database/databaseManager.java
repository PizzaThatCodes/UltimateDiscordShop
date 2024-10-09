package me.pizzathatcodes.ultimatediscordshop.database;

import me.pizzathatcodes.ultimatediscordshop.Main;
import me.pizzathatcodes.ultimatediscordshop.utils.util;
import org.bukkit.Bukkit;

import java.sql.*;

public class databaseManager {

    private static Connection connection;

    /**
     * Get a connection to a database
     * @return the connection
     * @throws SQLException
     */
    public static Connection getConnection() {

        try {
            if (connection != null) {
                if(!connection.isClosed() && connection.isValid(500)) {
                    return connection;
                }
            }

            return reloadConnection();

        } catch (SQLException e) {
            Bukkit.getServer().getLogger().info("Cannot connect to database, maybe the configuration is wrong");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
        return null;
    }

    /**
     * Initialize the database
     * @throws SQLException
     */
    public boolean initializeDatabase() {
        try {
            if(getConnection() == null) {
                Bukkit.getServer().getLogger().info("Cannot initialize database, maybe the configuration is wrong");
                return false;
            }
            Statement statement = getConnection().createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS codes ( codeID varchar(35) primary key, itemstack text, boughtBy text, redeemedBy text, amount int, redeemed boolean)";

            String databaseItems = "CREATE TABLE IF NOT EXISTS items ( codeID varchar(35) primary key, itemstack text)";

            String databaseCoins = "CREATE TABLE IF NOT EXISTS coins ( userID varchar(35) primary key, coinAmount int)";

            statement.execute(sql);
            statement.execute(databaseItems);
            statement.execute(databaseCoins);

            statement.close();
        } catch (SQLException e) {
            Bukkit.getServer().getLogger().info("Cannot initialize database, maybe the configuration is wrong");
            return false;
        }
        return true;
    }

    public static Connection reloadConnection() {
        try {
            switch (util.getDatabaseFile().getConfig().getString("schemas.ultimateshop.type")) {
                case "sqlite": {
                    if (Main.getInstance().getDataFolder().mkdirs()) {
                        Main.getInstance().getLogger().info("Created the plugin folder.");
                    }
                    String databasePath = Main.getInstance().getDataFolder().getAbsolutePath() + "/database.db"; // Path to your SQLite database file

                    // Try to connect to SQLite
                    String url = "jdbc:sqlite:" + databasePath;

                    Connection connection1 = DriverManager.getConnection(url);

                    connection = connection1;

                    Main.getInstance().getLogger().info("Connected to SQLite database.");

                    return connection;
                }
                case "mysql": {
                    String schema = "schemas.ultimateshop.";
                    if (
                            util.getDatabaseFile().getConfig().getString(schema + "username").equalsIgnoreCase("root")
                                    && util.getDatabaseFile().getConfig().getString(schema + "password").equalsIgnoreCase("password")
                                    && util.getDatabaseFile().getConfig().getString(schema + "host").equalsIgnoreCase("localhost")
                                    && util.getDatabaseFile().getConfig().getString(schema + "port").equalsIgnoreCase("3306")) {
                        Bukkit.getServer().getLogger().info("Please configure the schema in the database.yml!");
                        return null;
                    }

                    //Try to connect to MySQL
                    String username = util.getDatabaseFile().getConfig().getString("schemas.ultimateshop.username");
                    String password = util.getDatabaseFile().getConfig().getString("schemas.ultimateshop.password");
                    String host = util.getDatabaseFile().getConfig().getString("schemas.ultimateshop.host");
                    String port = util.getDatabaseFile().getConfig().getString("schemas.ultimateshop.port");
                    String database = util.getDatabaseFile().getConfig().getString("schemas.ultimateshop.database");
                    String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";

                    Connection connection1 = DriverManager.getConnection(url, username, password);

                    connection = connection1;

                    Bukkit.getServer().getLogger().info("Connected to MySQL database.");
                    return connection;
                }
            }
        } catch (SQLException e) {
            Bukkit.getServer().getLogger().info("Cannot connect to database, maybe the configuration is wrong");
            Main.getInstance().getServer().getPluginManager().disablePlugin(Main.getInstance());
        }
        return null;
    }


}
