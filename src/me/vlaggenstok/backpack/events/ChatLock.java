package me.vlaggenstok.backpack.events;

import me.vlaggenstok.backpack.main.Backpack;
import me.vlaggenstok.backpack.main.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ChatLock extends Util implements Listener {


    @EventHandler
    public void chatlock(AsyncPlayerChatEvent e) {
        if (getbp(e.getPlayer()) != null) {
            e.setCancelled(true);

            Player p = e.getPlayer();
            Backpack bp = getbp(e.getPlayer());
            ItemStack a = e.getPlayer().getInventory().getItemInMainHand();
            ItemMeta am = a.getItemMeta();
            if (e.getMessage().equalsIgnoreCase("cancel")) {
remove(p);
p.sendMessage("You canceled the process to lock or unlock your backpack.");
            } else {
                int code = 0;
                try {
                    code = Integer.parseInt(e.getMessage());
                } catch (Exception et) {
                    p.sendMessage("This is not a valid code.");
                    return;
                }
                e.setCancelled(true);
                if (code > 99 && code < 1000) {
                    if (bp.getstatus() == Backpack.Status.Open) {

                        if (bp.getpassword() == code) {
bp.setStatus(Backpack.Status.Locked);
bp.save();
                            p.sendMessage("You have locked your backpack.");
                            remove(p);
                        } else {
                            p.sendMessage("This is not a valid code.");
                        }

                    } else if (bp.getstatus() == Backpack.Status.Locked) {
                        if (bp.getpassword() == code) {
               bp.setStatus(Backpack.Status.Open);
                            p.sendMessage("You have opened your backpack.");
                            remove(p);
                            bp.save();
                        } else {
                            p.sendMessage("This is not a valid code.");
                        }
                    }
                } else {
                    p.sendMessage("This code is to short or to long, please try again.");
                }
            }
        }
        if(getownchange(e.getPlayer()) != null){
            e.setCancelled(true);



            ItemStack a = e.getPlayer().getInventory().getItemInMainHand();
            ItemMeta am = a.getItemMeta();
             Backpack bp =    Backpack.getbackpack(Integer.parseInt(am.getLore().get(1)));
                Player p = e.getPlayer();
                if(bp.getUUID() == getownchange(p).getUUID()){
            if(e.getMessage().equalsIgnoreCase("yes")|| e.getMessage().equalsIgnoreCase("no")) {
if(e.getMessage().equalsIgnoreCase("yes")){
    bp.setOwner(ownchangeb.get(p));
    p.sendMessage("You have changed the ownership to " + ownchangeb.get(p) + ".");
    ownchange.remove(p);
    ownchangeb.remove(p);
    bp.save();
    return;
}else{
    p.sendMessage("You have choosen no.");
    p.sendMessage("Owner will not change.");
    ownchange.remove(p);
    ownchangeb.remove(p);
return;
}
            }else{
                p.sendMessage("Please enter yes or no.");
            }
            }else{
                    p.sendMessage("This is not a valid backpack.");

                }
        }
//        if (getpw(e.getPlayer()) != null) {
//            Player p = e.getPlayer();
//            Backpack bp = getpw(e.getPlayer());
//            ItemStack a = e.getPlayer().getInventory().getItemInMainHand();
//            ItemMeta am = a.getItemMeta();
//            int code = 0;
//            try {
//                code = Integer.parseInt(e.getMessage());
//            } catch (Exception et) {
//                p.sendMessage("This is not a valid code.");
//                return;
//            }
//
//            e.setCancelled(true);
//            if (!(code > 99 && code < 1000)) {
//                p.sendMessage("This code is to short or to long, please try again.");
//                return;
//            } else {
//                bp.setPassword(code);
//                p.sendMessage("You have changed the password of " + bp.getUUID().toString() + ".");
//                removePlayer(p);
//            }
//        }
    }
}
