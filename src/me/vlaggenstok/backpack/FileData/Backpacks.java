package me.vlaggenstok.backpack.FileData;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.File;
import java.io.IOException;

public class Backpacks {


    static Backpacks instance = new Backpacks();
    Plugin p;
    FileConfiguration playerdata;
    File playerdatafile;
    FileConfiguration message;
    File messagefile;
    FileConfiguration config;
    File cfile;
    FileConfiguration data;
    File dfile;

    public static Backpacks getInstance() {
        return instance;
    }

    public void setup(Plugin p) {
        this.playerdatafile = new File(p.getDataFolder(), "Backpacks.yml");
        if (!this.playerdatafile.exists()) {
            try {
                this.playerdatafile.createNewFile();
            } catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create Backpacks.yml!");
            }
        }
        this.playerdata = YamlConfiguration.loadConfiguration(this.playerdatafile);
    }

    public FileConfiguration getData() {
        return this.playerdata;
    }

    public void saveData() {
        try {
            this.playerdata.save(this.playerdatafile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save Backpacks.yml!");
        }
    }

    public void reloadData() {
        this.playerdata = YamlConfiguration.loadConfiguration(this.playerdatafile);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveConfig() {
        try {
            this.config.save(this.cfile);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save Backpacks.yml!");
        }
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.cfile);
    }

    public PluginDescriptionFile getDesc() {
        return this.p.getDescription();
    }
}
