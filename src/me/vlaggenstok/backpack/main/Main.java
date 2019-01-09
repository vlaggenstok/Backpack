package me.vlaggenstok.backpack.main;

import com.sun.corba.se.impl.orb.DataCollectorBase;
import me.vlaggenstok.backpack.FileData.Backpacks;
import me.vlaggenstok.backpack.FileData.Config;
import me.vlaggenstok.backpack.FileData.Data;
import me.vlaggenstok.backpack.FileData.Database;
import me.vlaggenstok.backpack.commands.BackPack;
import me.vlaggenstok.backpack.events.BlockPlace;
import me.vlaggenstok.backpack.events.ChatLock;
import me.vlaggenstok.backpack.events.Open;
import me.vlaggenstok.backpack.events.Quit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
public Plugin pl;
public static HashMap<byte[] , UUID> ad = new HashMap<byte[], UUID>();
  static  boolean Conf;
private static Main main;
    Config c = Config.getInstance();
Backpacks bp = Backpacks.getInstance();
    public void onEnable(){
 pl = this;
 main = this;
        c.setup(pl);
        c.getData().options().copyDefaults(true);
        c.saveData();

        bp.setup(pl);
        bp.getData().options().copyDefaults(true);
        bp.saveData();



        c.getData().addDefault("Database.Use", false);
        c.getData().addDefault("Database.IP", "127.0.0.1");
        c.getData().addDefault("Database.Port", 3306);
        c.getData().addDefault("Database.Username", "Vlaggenstok");
        c.getData().addDefault("Database.Password", "Password");
        c.getData().options().copyDefaults(true);
        c.saveData();

        getCommand("backpack").setExecutor(new BackPack());
        Bukkit.getPluginManager().registerEvents(new BlockPlace() , this);
        Bukkit.getPluginManager().registerEvents(new Quit() , this);
        Bukkit.getPluginManager().registerEvents(new Open() , this);
        Bukkit.getPluginManager().registerEvents(new ChatLock() , this);
if(c.getData().getBoolean("Database.Use") == true){
    String password = "";
    if(!c.getData().getString("Database.Password").equalsIgnoreCase("none")){
        password =c.getData().getString("Database.Password");
    }
    connec = "jdbc:mysql://" + c.getData().getString("Database.IP") + ":" + c.getData().getInt("Database.Port") + "/?user="+
            c.getData().getString("Database.Username") + "&password=" +  password;

    connecsr = "jdbc:mysql://" + c.getData().getString("Database.IP") + ":" + c.getData().getInt("Database.Port") + "/BasicBackpack?user="+
            c.getData().getString("Database.Username") + "&password=" +  password + "&autoReconnect=true";
    System.out.println(connec);
    System.out.println(connecsr);
    Util.database = true;
    establishConnection();
    try {
        Database.createDatabase();

        establishConnecction();
        Database.test();
    } catch (SQLException e) {

    }
}

    }
    public Connection getConnection(){
        return connection;
    }

    public static Connection connection;
    public static void setConnection(Connection connect) {
        connection = connect;
    }
    public static String connecsr = "";
    public static String connec = "";
    private static void establishConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connec);

            Bukkit.getLogger().info("De plugin is verbonden met de database.");
        }catch(Exception e){
            e.printStackTrace();
            Bukkit.getLogger().log(java.util.logging.Level.WARNING,"Kan geen verbinding maken met de database", new Object());
            Bukkit.getPluginManager().disablePlugin(Main.getInstance().pl);
        }
    }
    private static void establishConnecction(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(connecsr);

            Bukkit.getLogger().info("De plugin is verdonden met de database.");
        }catch(Exception e){
            e.printStackTrace();
            Bukkit.getLogger().log(java.util.logging.Level.WARNING,"Kan geen verbinding maken met de database", new Object());
            Bukkit.getPluginManager().disablePlugin(Main.getInstance().pl);
        }
    }

    public static Main getInstance() {
    return main;
}
    public void onDisable(){

    }
}
