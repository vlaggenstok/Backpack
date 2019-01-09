package me.vlaggenstok.backpack.events;

import me.vlaggenstok.backpack.main.Backpack;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BlockPlace implements Listener {
@EventHandler(priority = EventPriority.HIGHEST)
    public void placebackpack(BlockPlaceEvent e) {
    if (e.getBlock().getType() == Material.CHEST) {
        ItemStack d = e.getItemInHand();
        if (d.getType() == Material.CHEST) {
            if (d.hasItemMeta())
                if (d.getItemMeta().hasLore()) {

                    if (d.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                        List<String> lore = d.getItemMeta().getLore();
                        if (Backpack.isbackpack(Integer.parseInt(lore.get(1)))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(lore.get(1)));
                            ItemMeta itm = d.getItemMeta();
                            itm.setLore(Arrays.asList("Backpack", b.getUUID() + "" , b.getstatus().toString()));
                            e.getItemInHand().setItemMeta(itm);
                            e.setCancelled(true);
                        }
                    }
                }
        }

    }
}
}
