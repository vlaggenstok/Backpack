package me.vlaggenstok.backpack.main;

import me.vlaggenstok.backpack.FileData.Backpacks;
import me.vlaggenstok.backpack.FileData.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.logging.Logger;

public class Util {
    public ChatColor yellow = ChatColor.YELLOW;
    public ChatColor bold = ChatColor.BOLD;
    public ChatColor red = ChatColor.RED;
    public ChatColor gray = ChatColor.GRAY;

    public static boolean database;
Backpacks bd = Backpacks.getInstance();
    public static Backpack getbp(Player p){
        return inlock.get(p);
    }
    public static  void remove(Player p){
        inlock.remove(p);
    }
    public static   HashMap<Player, Backpack>inlock  = new HashMap<>();
public static HashMap<Player , Backpack> inadminusecommand   = new HashMap<>();
    public static Backpack getownchange(Player p){
        return ownchange.get(p);
    }
    public static void removePlayer(Player p){
        ownchange.remove(p);
    }
    public static   HashMap<Player, Backpack> ownchange  = new HashMap<>();

public static HashMap<Player, String> ownchangeb = new HashMap<>();

    public static String itemsToString(ItemStack[] items) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(serializeItemStack(items));
            oos.flush();
            return DatatypeConverter.printBase64Binary(bos.toByteArray());
        }
        catch (Exception e) {
e.printStackTrace();        }
        return "";
    }
    public int newid(){

        Random rand = new Random();
        int    password = rand.nextInt((999 - 100) + 1) + 100;
            while (Backpack.isbackpack(password)) {
                password = rand.nextInt((999 - 100) + 1) + 100;


        }
        return password;
    }


    public int newcode(){
        if(!database) {
            List<Integer> codes = new ArrayList<>();
            for (String s : bd.getData().getConfigurationSection("Backpacks.").getKeys(false)) {
                codes.add(bd.getData().getInt("Backpacks." + s + ".Code"));
            }

            Random rand = new Random();
            int password = rand.nextInt((999 - 100) + 1) + 100;
            while (codes.contains(password)) {
                password = rand.nextInt((999 - 100) + 1) + 100;

            }
            return password;

        }else{
            Random rand = new Random();
            int password = rand.nextInt((999 - 100) + 1) + 100;
            while (Data.iscode(password)) {
                password = rand.nextInt((999 - 100) + 1) + 100;
            }
            return password;
        }
    }


    @SuppressWarnings("unchecked")
    public static ItemStack[] stringToItems(String s) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(
                    DatatypeConverter.parseBase64Binary(s));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return deserializeItemStack(
                    (Map<String, Object>[]) ois.readObject());
        }
        catch (Exception e) {
e.printStackTrace();        }
        return new ItemStack[] {
                new ItemStack(Material.AIR) };
    }

    private static Map<String, Object>[] serializeItemStack(ItemStack[] items) {

        Map<String, Object>[] result = new Map[items.length];

        for (int i = 0; i < items.length; i++) {
            ItemStack is = items[i];
            if (is == null) {
                result[i] = new HashMap<>();
            }
            else {
                result[i] = is.serialize();
                if (is.hasItemMeta()) {
                    result[i].put("meta", is.getItemMeta().serialize());
                }
            }
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private static ItemStack[] deserializeItemStack(Map<String, Object>[] map) {
        ItemStack[] items = new ItemStack[map.length];

        for (int i = 0; i < items.length; i++) {
            Map<String, Object> s = map[i];
            if (s.size() == 0) {
                items[i] = null;
            }
            else {
                try {
                    if (s.containsKey("meta")) {
                        Map<String, Object> im = new HashMap<>(
                                (Map<String, Object>) s.remove("meta"));
                        im.put("==", "ItemMeta");
                        ItemStack is = ItemStack.deserialize(s);
                        is.setItemMeta((ItemMeta) ConfigurationSerialization
                                .deserializeObject(im));
                        items[i] = is;
                    }
                    else {
                        items[i] = ItemStack.deserialize(s);
                    }
                }
                catch (Exception e) {
e.printStackTrace();
items[i] = null;
                }
            }

        }

        return items;
    }
}
