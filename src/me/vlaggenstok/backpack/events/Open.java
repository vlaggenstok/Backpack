package me.vlaggenstok.backpack.events;

import me.vlaggenstok.backpack.main.Backpack;
import me.vlaggenstok.backpack.main.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class Open extends Util implements Listener {


    @EventHandler
    public void click(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.CHEST) {
                ItemStack a = e.getPlayer().getInventory().getItemInMainHand();
                ItemMeta am = a.getItemMeta();
                if (am.hasLore() && am.getLore().size() > 1) {
                    e.setCancelled(true);
                    if (am.getLore().get(0).equalsIgnoreCase("Backpack")) {

                        Backpack b = Backpack.getbackpack(Integer.parseInt(am.getLore().get(1)));

                        ItemMeta itm = a.getItemMeta();
                        itm.setLore(Arrays.asList("Backpack", b.getUUID() + "" , b.getstatus().toString()));
                        e.getPlayer().getItemInHand().setItemMeta(itm);
                        if (b.getpassword() != 0) {
                            if (b.getstatus() != Backpack.Status.Locked && b.getstatus() != Backpack.Status.OutGame) {
                                b.open(e.getPlayer());
                            } else {
                                e.getPlayer().sendMessage("This backpack is locked.");
                            }
                        }else{
                            b.open(e.getPlayer());
                        }
                    }
                }
            }

        }
    }


    @EventHandler
    public void close(InventoryCloseEvent e){
        if(e.getInventory().getName().equalsIgnoreCase("Backpack")) {
            if (Util.inadminusecommand.containsKey(e.getPlayer().getKiller())) {
Backpack b = Util.inadminusecommand.get(e.getPlayer().getKiller());
b.close(e.getPlayer().getKiller(),e.getInventory());
inadminusecommand.remove(e.getPlayer().getKiller());
            } else {
                Backpack b = Backpack.getbackpack(Integer.parseInt(e.getPlayer().getItemInHand().getItemMeta().getLore().get(1)));
                b.close(e.getPlayer().getKiller(), e.getInventory());
            }
        }
    }
}
