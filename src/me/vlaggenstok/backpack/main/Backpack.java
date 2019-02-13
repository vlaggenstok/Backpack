package me.vlaggenstok.backpack.main;

import me.vlaggenstok.backpack.FileData.Backpacks;
import me.vlaggenstok.backpack.FileData.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;
import java.util.Random;

public class Backpack extends Util {
    private static int uuid;
    private int size;
    private int password;
    private String owner;
    private String b;
    private Status status;


    public enum Status {
        Open,
        Locked,
        OutGame
    }

    private static Backpacks bd = Backpacks.getInstance();

    public static boolean isbackpack(Integer u) {
        boolean a;
        if (!database) {
            //config

            if (bd.getData().get("Backpacks." + u) != null) {
                a = true;
            } else {
                //new
                a = false;
            }
        } else {
            a = Data.isbackpack(u);

        }
        return a;
    }

    //
    public Backpack(Player p) {

        Random rand = new Random();
        uuid = newid();
        b = "0";
        password = newcode();
        owner = p.getName();
        status = Status.Open;
        size = 45;
        if (!database) {
            int id = newid();
            bd.getData().set("Backpacks." + id + ".size", size);
            bd.getData().set("Backpacks." + id + ".Content", b);
            bd.getData().set("Backpacks." + id + ".Code", password);
            bd.getData().set("Backpacks." + id + ".Owner", owner);
            bd.getData().set("Backpacks." + id + ".Status", status.toString());

            bd.saveData();
        } else {
            Data.newdata(password, owner, b, size, uuid, status.toString());

        }

        return;

    }

    public void setPassword(int a) {
        this.password = a;
    }

    public int getpassword() {
        return password;
    }

    public Backpack(int size, int u, String b, Integer code, String own, Status status) {
        uuid = u;
        this.b = b;
        this.size = size;
        password = code;
        owner = own;
        this.status = status;

        return;
    }

    public void setcontent(String content) {
        this.b = content;
    }

    public void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, size, "Backpack");
        if (!b.equalsIgnoreCase("0")) {
            inv.setContents(stringToItems(b));
        }

        p.openInventory(inv);
    }

    public void close(Player p, Inventory invs) {
        Inventory inv = invs;
        setcontent(itemsToString(inv.getContents()));
        save();
    }


    public static Backpack getbackpack(int u) {

        if (!database) {

            int size = bd.getData().getInt("Backpacks." + u + ".size");
            int code = bd.getData().getInt("Backpacks." + u + ".Code");
            String own = bd.getData().getString("Backpacks." + u + ".Owner");
            Status stat = Status.valueOf(bd.getData().getString("Backpacks." + u + ".Status"));

            String content = bd.getData().getString("Backpacks." + u + ".Content");

            return new Backpack(size, u, content, code, own, stat);
        } else {
            Data d = new Data(u);
            return new Backpack(d.getSize(), u, d.getContent(), d.getcode(), d.getOwner(), Status.valueOf(d.getStatus()));
        }

    }

    public void remove() {
        if (Bukkit.getPlayer(owner).isOnline()) {
            Player p = Bukkit.getPlayer(owner);
            ItemStack is = new ItemStack(Material.CHEST);
            ItemMeta ism = is.getItemMeta();
            ism.setLore(Arrays.asList("Backpack", uuid + "", status.toString()));
            is.setItemMeta(ism);
            p.getInventory().remove(is);
        } else {
            setStatus(Status.OutGame);
            save();
        }
    }

    public void save() {
        if (!database) {
            bd.getData().set("Backpacks." + uuid + ".size", size);

            bd.getData().set("Backpacks." + uuid + ".Content", b);
            bd.getData().set("Backpacks." + uuid + ".Code", password);
            bd.getData().set("Backpacks." + uuid + ".Owner", owner);
            bd.getData().set("Backpacks." + uuid + ".Status", status.toString());

            bd.saveData();
            bd.reloadData();
        } else {
            Data d = new Data(uuid);
            d.setSize(size);
            d.setContent(b);
            d.setCode(password);
            d.setOwner(owner);
            d.setStatus(status.toString());
            d.save();
        }
    }

    public int getUUID() {
        return uuid;
    }

    public Status getstatus() {
        return status;
    }

    public void setStatus(Status s) {
        status = s;
    }

    public int getSize() {
        return size;
    }


    public String getOwner() {
        return owner;

    }

    public void setOwner(String s) {
        owner = s;
    }

    public static Backpack getBackpackSafe(String id){
        try {
            int iId = Integer.parseInt(id);
            if (isbackpack(iId))
                return getbackpack(iId);
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
