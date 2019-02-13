package me.vlaggenstok.backpack.events;

import me.vlaggenstok.backpack.main.Backpack;
import me.vlaggenstok.backpack.main.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ChatLock extends Util implements Listener {


    @EventHandler
    public void chatlock(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        ItemStack a = e.getPlayer().getInventory().getItemInMainHand();
        ItemMeta am = a.getItemMeta();
        if (getbp(e.getPlayer()) != null) {
            e.setCancelled(true);
            Backpack bp = getbp(e.getPlayer());
            if (e.getMessage().equalsIgnoreCase("cancel")) {
            remove(p);
            p.sendMessage("You canceled the process to lock or unlock your backpack.");
            return;
            }
                int code = 0;
                try {
                    code = Integer.parseInt(e.getMessage());
                } catch (Exception et) {
                    p.sendMessage("This is not a valid code.");
                    return;
                }
                if (!(code > 99 && code < 1000)) {
                    p.sendMessage("This code is to short or to long, please try again.");

                    return;
                }
                    if (bp.getstatus() == Backpack.Status.Open) {

                        if (bp.getpassword() != code) {
                            p.sendMessage("This is not a valid code.");
                            return;
                        }

                            bp.setStatus(Backpack.Status.Locked);
                            bp.save();
                            p.sendMessage("You have locked your backpack.");
                            remove(p);
                            return;
                    }
                    if (bp.getstatus() == Backpack.Status.Locked) {
                        if (bp.getpassword() != code) {
                            p.sendMessage("This is not a valid code.");
                            return;
                        }
                            bp.setStatus(Backpack.Status.Open);
                            p.sendMessage("You have opened your backpack.");
                            remove(p);
                            bp.save();
                        return;
                    }
                return;
            }

        if(getownchange(e.getPlayer()) != null){
            e.setCancelled(true);
             Backpack bp =    Backpack.getbackpack(Integer.parseInt(am.getLore().get(1)));
                if(bp.getUUID() != getownchange(p).getUUID()) {
                    p.sendMessage("This is not a valid backpack.");
return;
                }

        if(e.getMessage().equalsIgnoreCase("yes")){
    bp.setOwner(ownchangeb.get(p));
    p.sendMessage("You have changed the ownership to " + ownchangeb.get(p) + ".");
    ownchange.remove(p);
    ownchangeb.remove(p);
    bp.save();
    return;
}
        if(e.getMessage().equalsIgnoreCase("yes")){

    p.sendMessage("You have choosen no.");
    p.sendMessage("Owner will not change.");
    ownchange.remove(p);
    ownchangeb.remove(p);
return;
}

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

