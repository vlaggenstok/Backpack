package me.vlaggenstok.backpack.FileData;

import me.vlaggenstok.backpack.main.Main;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    static Statement statement = null;
    public Database(){

    }

    public static  void createDatabase() throws SQLException {
        String sql_stmt = "CREATE DATABASE IF NOT EXISTS  `BasicBackpack`;";

        statement = Main.getInstance().getConnection().createStatement();

        if (statement.executeUpdate(sql_stmt) == 1) {

            System.out.println("Database has successfully been created");
        }
    }

    public static void Tables()   throws SQLException {

        String tesst = "CREATE TABLE IF NOT EXISTS `Backpacks` ( " +
                "ID INT(4) NOT NULL," +
                "size INT(4) NOT NULL," +
                "Content LONGTEXT NOT NULL," +
                "Code TEXT NOT NULL," +
                "Owner TEXT NOT NULL," +
                "Status TEXT NOT NULL)";
        statement = Main.getInstance().getConnection().createStatement();

        statement.executeUpdate(tesst);
    }

}
