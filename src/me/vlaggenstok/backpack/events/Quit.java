package me.vlaggenstok.backpack.events;

import com.mysql.fabric.xmlrpc.base.Array;
import me.vlaggenstok.backpack.main.Backpack;
import me.vlaggenstok.backpack.main.Util;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Quit extends Util implements Listener {


@EventHandler
public void Join(PlayerJoinEvent e){
 Player p = e.getPlayer();
 if(p.getInventory().contains(Material.CHEST)) {
     System.out.println("1");
     boolean tes = false;
     System.out.println(p.getInventory().getContents());
     List<ItemStack> isd = new ArrayList<>();
     for (ItemStack s : p.getInventory().getContents()) {
         if(s != null && s.getType() != Material.AIR){
         if (s.hasItemMeta()) {
             if (s.getItemMeta().hasLore()) {
                 ItemMeta is = s.getItemMeta();
                 if (is.getLore().get(0).equalsIgnoreCase("Backpack")) {
                     tes = true;
                     isd.add(s);
                 }
             }
             }
         }
     }
     if (tes) {
         List<ItemStack> das = Arrays.asList(p.getInventory().getContents());

         for (ItemStack isb : isd) {

             ItemMeta is = isb.getItemMeta();
             if (Backpack.isbackpack(Integer.parseInt(is.getLore().get(1)))) {
                 Backpack bp = Backpack.getbackpack(Integer.parseInt(is.getLore().get(1)));
                 if (bp.getstatus() == Backpack.Status.Open) {
                     p.getInventory().remove(isb);

                 }

             } else {
             }
         }
//         ItemStack[] dg = das.toArray(new ItemStack[0]);
//         System.out.println(dg);
//         p.getInventory().setContents(dg);
     }
 }
}


    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
if(ownchange.containsKey(p)){
    ownchange.remove(p);
}
if(inlock.containsKey(p)){
    inlock.remove(p);
}
if(inadminusecommand.containsKey(p)){
    inadminusecommand.remove(p);
}
    }
}
