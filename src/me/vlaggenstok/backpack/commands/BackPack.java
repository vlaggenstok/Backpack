package me.vlaggenstok.backpack.commands;

import me.vlaggenstok.backpack.FileData.Backpacks;
import me.vlaggenstok.backpack.main.Backpack;
import me.vlaggenstok.backpack.main.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.omg.CORBA.BAD_QOS;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class BackPack extends Util implements CommandExecutor {

Backpacks ps = Backpacks.getInstance();
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (cmd.getName().equalsIgnoreCase("backpack")) {
          //region
          Player p = (Player) sender;

            if (args.length == 0) {


                sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD +  "Backpack Options");
                sender.sendMessage(ChatColor.RED + "/backpack reset");
                sender.sendMessage(ChatColor.GRAY + ""   + ChatColor.ITALIC + "To reset that password of the holding Backpack.");
                sender.sendMessage(ChatColor.RED + "/backpack disable <password>");
                sender.sendMessage(ChatColor.GRAY+ ""   + ChatColor.ITALIC + "To disable the password option of the holding Backpack.");
                sender.sendMessage(ChatColor.RED + "/backpack enable ");
                sender.sendMessage(ChatColor.GRAY+ ""   + ChatColor.ITALIC + "To enable the password option of the holding Backpack.");
                sender.sendMessage(ChatColor.RED + "/backpack setowner <new owner>");
                sender.sendMessage(ChatColor.GRAY+ ""   + ChatColor.ITALIC + "Set owner of this backpack.");
                sender.sendMessage(ChatColor.RED + "/backpack lock");
                sender.sendMessage(ChatColor.GRAY+ ""   + ChatColor.ITALIC + "To lock your backpack.");
                sender.sendMessage(ChatColor.RED + "/backpack unlock");
                sender.sendMessage(ChatColor.GRAY+ ""   + ChatColor.ITALIC + "To unlock your backpack.");
            }
            if (args.length == 1) {
                if(args[0].equalsIgnoreCase("admin")){
                    if(sender.hasPermission("BasicBackpack.admin")) {
                        sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD +  "Backpack Admin Options");
                        sender.sendMessage(ChatColor.RED +"/backpack admin setowner " + ChatColor.GRAY + ""+ ChatColor.ITALIC  + "<id-backpack>" + ChatColor.RED +  "<newowner>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin clear  " + ChatColor.GRAY + ""+ ChatColor.ITALIC  +"<id-backpack>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin lock " + ChatColor.GRAY+ "" +ChatColor.ITALIC  +"<id-backpack>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin unlock " + ChatColor.GRAY+ "" +ChatColor.ITALIC  +"<id-backpack>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin enable " + ChatColor.GRAY+ "" +ChatColor.ITALIC  +"<id-backpack>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin disable " + ChatColor.GRAY+ "" +ChatColor.ITALIC  +"<id-backpack>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin reset " + ChatColor.GRAY  + ""+ChatColor.ITALIC  +"<id-backpack>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin setcode " + ChatColor.GRAY + "" +ChatColor.ITALIC  +"<id-backpack> " + ChatColor.RED + "<code>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin open " + ChatColor.GRAY + ""+ChatColor.ITALIC  +"<id-backpack>");
                        sender.sendMessage(ChatColor.RED +"/backpack admin info " + ChatColor.GRAY + ""+ChatColor.ITALIC  +"<id-backpack>");
                        return true;
                    }else{
                        sender.sendMessage(red + "You dont have permissions to use that command.");
                        return true;
                    }
                }
                if(args[0].equalsIgnoreCase("get")){
                    Backpack b = new Backpack((Player) sender);
                    ItemStack it = new ItemStack(Material.CHEST);
                    ItemMeta itm = it.getItemMeta();
                    itm.setLore(Arrays.asList("Backpack", b.getUUID() + "" , "Locked"));
                    it.setItemMeta(itm);
                    p.getInventory().addItem(it);
                    p.sendMessage(yellow + "You have recieved a new backpack.");
                    return true;
                }
                if(args[0].equalsIgnoreCase("give")){
                    sender.sendMessage(red + "You forgot a player name.");
                    sender.sendMessage(ChatColor.GRAY + "usage /backpack give <player name>");
                    return true;
                }
                if (args[0].equalsIgnoreCase("reset")) {
                    if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                        ItemStack is = p.getInventory().getItemInMainHand();
                        if (is.getItemMeta().hasLore()) {
                            if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                                Backpack bp = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));

                                if(bp.getOwner().equalsIgnoreCase(p.getName())){
                                    int code = newcode();
                                    sender.sendMessage(yellow + "Your new password is "  + bold + code +yellow +  ".");
                                    bp.setPassword(code);
                                }else{
                                    sender.sendMessage(red + "You can't reset the password of this backpack.");
                                }
                            } else {
                                sender.sendMessage(red + "This is not a valid backpack.");
                            }
                        } else {
                            sender.sendMessage(red + "This is not a valid backpack.");
                        }
                    } else {
                        sender.sendMessage(red +"This is not a valid backpack.");
                    }


                }
                if (args[0].equalsIgnoreCase("setowner")) {
                    if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                        ItemStack is = p.getInventory().getItemInMainHand();
                        if (is.getItemMeta().hasLore()) {
                            if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                                Backpack bp = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                                if(bp.getOwner().equalsIgnoreCase(p.getName())){
   sender.sendMessage(red + "You forgot the new owner.");
   sender.sendMessage(ChatColor.GRAY + "usage /backpack setowner <new owner>");
                                }else{
                                    sender.sendMessage(red +"You can't change the owner of this backpack.");
                                }
                            } else {
                                sender.sendMessage( red +"This is not a valid backpack.");
                            }
                        } else {
                            sender.sendMessage(red +"This is not a valid backpack.");
                        }
                    } else {
                        sender.sendMessage(red +"This is not a valid backpack.");
                    }


                }
            if(args[0].equalsIgnoreCase("disable")){
                if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                    ItemStack is = p.getInventory().getItemInMainHand();
                    if (is.getItemMeta().hasLore()) {
                        if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                            if(b.getpassword() != 0){
                                sender.sendMessage(red +"You forgot the password code");
                                sender.sendMessage(gray +"usage /backpack disable <password>");
                                return true;

                            }else {
                                sender.sendMessage(red +"There is currently no password for this backpack.");
                                sender.sendMessage(red +"Want to add a password? , /backpack enable");
                            }

                        } else {
                            sender.sendMessage(red + "This is not a valid backpack.");
                        }
                    } else {
                        sender.sendMessage(red +"This is not a valid backpack.");
                    }
                } else {
                    sender.sendMessage(red + "This is not a valid backpack.");
                }
            }
            if(args[0].equalsIgnoreCase("lock")){
                    if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                        ItemStack is = p.getInventory().getItemInMainHand();
                        if (is.getItemMeta().hasLore()) {
                            if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                                Backpack b = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                                if (b.getpassword() != 0) {
                                    if (inlock.containsKey(p)) {
                                        Backpack bp = inlock.get(p);

                                        p.sendMessage(yellow + "Type your code in the chat.");

                                        return true;
                                    } else {
                                        if (!is.getItemMeta().getLore().get(2).contains("Locked")) {
                                            inlock.put(p, Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1))));
                                            p.sendMessage(yellow + "Type your code in the chat.");
                                        } else {
                                            p.sendMessage(red + "This backpack is already locked.");
                                        }
                                    }
                                } else {
                                    sender.sendMessage(red +"There are currently no locks on this backpack.");
                                }


                        }
                    }
                }
                }
                if(args[0].equalsIgnoreCase("unlock")){
                    if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                        ItemStack is = p.getInventory().getItemInMainHand();
                        if (is.getItemMeta().hasLore()) {
                            if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                                Backpack b = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                                if (b.getpassword() != 0) {
                                    if (inlock.containsKey(p)) {
                                        Backpack bp = inlock.get(p);

                                        p.sendMessage(yellow + "Type your code in the chat.");

                                        return true;
                                    } else {
                                        if (!is.getItemMeta().getLore().get(2).contains("Open")) {
                                            inlock.put(p, Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1))));
                                            p.sendMessage(yellow + "Type your code in the chat.");
                                        } else {
                                            p.sendMessage(red + "This backpack is already unlocked.");
                                        }
                                    }
                                } else {
                                    sender.sendMessage(red + "There are currently no security locks on this backpack.");
                                }


                            }
                        }
                    }
                }
                if(args[0].equalsIgnoreCase("enable")){
                    if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                        ItemStack is = p.getInventory().getItemInMainHand();
                        if (is.getItemMeta().hasLore()) {
                            if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                                Backpack b = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
if(b.getpassword() == 0){
    int code = newcode();
    sender.sendMessage(yellow + "Your new password is " + bold +code + yellow + ".");
    b.setPassword(code);
}else {
sender.sendMessage(red +"There is currently a password for this backpack.");
sender.sendMessage(red + "Forgotten? , /backpack reset");
}
                            } else {
                                sender.sendMessage(red +"This is not a valid backpack.");
                            }
                        } else {
                            sender.sendMessage(red +"This is not a valid backpack.");
                        }
                    } else {
                        sender.sendMessage(red +"This is not a valid backpack.");
                    }
                }
            }
            if(args.length == 2){


//
//                sender.sendMessage("/backpack admin setowner <newowner>");
//                sender.sendMessage("/backpack admin setowner <id-backpack> <newowner>");
//             **   sender.sendMessage("/backpack admin clear");**
//                sender.sendMessage("/backpack admin clear <id-backpack>");
//              **  sender.sendMessage("/backpack admin lock");**
//                sender.sendMessage("/backpack admin lock <id-backpack>");
//              **  sender.sendMessage("/backpack admin unlock");**
//                sender.sendMessage("/backpack admin unlock <id-backpack>");
//             **   sender.sendMessage("/backpack admin enable");**
//                sender.sendMessage("/backpack admin enable <id-backpack>");
//           **     sender.sendMessage("/backpack admin disable");**
//                sender.sendMessage("/backpack admin disable <id-backpack>");
//            **    sender.sendMessage("/backpack admin reset");**
//                sender.sendMessage("/backpack admin reset <id-backpack>");
//                sender.sendMessage("/backpack admin setcode <id-backpack> <code>");
//                sender.sendMessage("/backpack admin setcode <code>");
//                sender.sendMessage("/backpack admin open <id-backpack>");
//            **    sender.sendMessage("/backpack admin info");**
//                sender.sendMessage("/backpack admin info <id-backpack>");

                if(args[0].equalsIgnoreCase("admin")) {
                    if (p.getInventory().getItemInMainHand().getType() != Material.CHEST) {
                        sender.sendMessage(red +"This is not a valid backpack.");
                        return true;
                    }
                    if (!p.getInventory().getItemInMainHand().hasItemMeta()) {
                        sender.sendMessage(red +"This is not a valid backpack.");
                        return true;
                    }
                    ItemStack is = p.getInventory().getItemInMainHand();
                    ItemMeta ism = is.getItemMeta();
                    if (!ism.hasLore()) {
                        sender.sendMessage(red +"This is not a valid backpack.");
                        return true;
                    }
                    if (!is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                        sender.sendMessage(red +"This is not a valid backpack.");
                        return true;
                    }
                    Backpack b = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                    if (args[1].equalsIgnoreCase("lock")) {
                        if(b.getpassword() == 0) {
                            sender.sendMessage(red +"There are currently no security locks on this backpack.");
                            return true;
                        }
if(b.getstatus() == Backpack.Status.Locked){
    p.sendMessage(red +"This backpack is already locked.");
    return true;
}else{
b.setStatus(Backpack.Status.Locked);
b.save();
    p.sendMessage(yellow + "You have locked your backpack.");
    return true;
}
                    }
                    if (args[1].equalsIgnoreCase("unlock")) {
                        if(b.getpassword() == 0) {
                            sender.sendMessage(red +"There are currently no security locks on this backpack.");
                            return true;
                        }
                        if(b.getstatus() == Backpack.Status.Open){
                            p.sendMessage(red +"This backpack is already unlocked.");
                        }else{
                            b.setStatus(Backpack.Status.Open);
                            b.save();

                            p.sendMessage(red +"You have unlocked your backpack.");
                            return true;
                        }

                    }
                    if(args[1].equalsIgnoreCase("clear")){
                        b.setcontent("0");
                        b.save();
                        p.sendMessage(yellow + "You have cleared the content of this backpack.");
                        return true;
                    }

                    if(args[1].equalsIgnoreCase("enable")){
                        if(b.getpassword() == 0){
                            int code = newcode();
                            sender.sendMessage(yellow + "Your new password is " + code);
                            b.setPassword(code);
                            b.save();

                        }else {
                            sender.sendMessage(red +"There is currently a password for this backpack.");
                            sender.sendMessage(red +"Forgotten? , /backpack reset");
                        }
                    }
                    if(args[1].equalsIgnoreCase("disable")) {
                        if (b.getpassword() == 0) {
                            sender.sendMessage(red +"There is currently no password for this backpack.");
                            sender.sendMessage(red +"Want to add a password? , /backpack enable");
                        } else {
                            sender.sendMessage(yellow + "You have removed the password from this backpack.");
                                                   b.setStatus(Backpack.Status.Open);

                            b.setPassword(0);
                            b.save();

                        }
                    }
                    if(args[1].equalsIgnoreCase("reset")){
                        int code = newcode();
                        sender.sendMessage(yellow + "Your new password is " + code + ".");
                        b.setPassword(code);
                        b.save();

                        return true;
                    }
                    if(args[1].equalsIgnoreCase("info")){
         sender.sendMessage(ChatColor.RED  + "Information about \n"  + b.getUUID());
         sender.sendMessage(ChatColor.RED + "Owner >> " + b.getOwner());
         sender.sendMessage(ChatColor.RED + "Size >> " + b.getSize());
                        sender.sendMessage(ChatColor.RED + "Status >> " + b.getstatus().toString());
                        sender.sendMessage(ChatColor.RED + "Code >> " + b.getpassword());


                    }
                    if(args[1].equalsIgnoreCase("open")){
      b.open(p);
                        return true;
                    }
                    if(args[1].equalsIgnoreCase("setowner")){
                        p.sendMessage(red + "You missed a owner name, Or a owner name and a backpack code.");
                    return true;
                    }
                    if(args[1].equalsIgnoreCase("setcode")){
                        p.sendMessage(red + "You missed a password, Or a password and a backpack code.");
                        return true;
                    }
                    return true;

                }



                if(args[0].equalsIgnoreCase("give")){
                    if(Bukkit.getPlayer(args[1]) == null){
                        sender.sendMessage(red +"This player is not online.");
                        return true;
                    }

                    Player pt = Bukkit.getPlayer(args[1]);
                    Backpack b = new Backpack(pt);
                    ItemStack it = new ItemStack(Material.CHEST);
                    ItemMeta itm = it.getItemMeta();
                    itm.setLore(Arrays.asList("Backpack", b.getUUID() + "" , "Locked"));
                    it.setItemMeta(itm);
                    pt.getInventory().addItem(it);
                    pt.sendMessage(yellow + "You have recieved a new backpack.");
return true;
                }
                if (args[0].equalsIgnoreCase("setowner")) {
                    if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                        ItemStack is = p.getInventory().getItemInMainHand();
                        if (is.getItemMeta().hasLore()) {
                            if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                                Backpack bp = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                                if(bp.getOwner().equalsIgnoreCase(p.getName())){
if(Bukkit.getOfflinePlayer(args[1]) != null){
ownchangeb.put(p,args[1]);
    ownchange.put(p, bp);
    sender.sendMessage(yellow + "Verify this ownership change.");
sender.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Enter yes/no");
return true;
}else{
    sender.sendMessage(red + "This player is never been online before.");
return true;
}
                                }else{
                                    sender.sendMessage(red + "You can't change the owner of this backpack.");
                                    return true;
                                }
                            }else {
                                sender.sendMessage(red + "This is not a valid backpack.");
                                return true;
                            }
                        } else {
                            sender.sendMessage(red +"This is not a valid backpack.");
                            return true;
                        }
                    } else {
                        sender.sendMessage(red + "This is not a valid backpack.");
return true;
                    }


                }
                if(args[0].equalsIgnoreCase("disable")){
                    if (p.getInventory().getItemInMainHand().getType() == Material.CHEST) {
                        ItemStack is = p.getInventory().getItemInMainHand();
                        if (is.getItemMeta().hasLore()) {
                            if (is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                                    int code = 0;
                                    try {
                                        code = Integer.parseInt(args[1]);
                                    } catch (Exception e) {
                                        sender.sendMessage(red + "This is not a valid code.");
                                        return true;
                                    }
                                    if (!(code > 99 && code < 1000)) {
                                        sender.sendMessage(red + "This code is to short or to long, please try again.");
                                        return true;
                                    }else{
                                    }
                                    if (Backpack.isbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)))) {
                                        Backpack b = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                                        if (b.getpassword() != code) {
                                            sender.sendMessage(red + "BackPack not matched, please try again.");
                                        } else {
                                            sender.sendMessage(yellow + "You have removed the password from this backpack.");
                                            b.setStatus(Backpack.Status.Open);
                                            b.setPassword(0);
                                            b.save();

                                            return true;
                                        }
                                    }else{
                                    }

                            } else {
                                sender.sendMessage(red + "This is not a valid backpack.");
                            }
                        } else {
                            sender.sendMessage(red +"This is not a valid backpack.");
                        }
                    } else {
                        sender.sendMessage(red + "This is not a valid backpack.");
                    }
                }
            }
            if(args.length == 3){
                //
//                sender.sendMessage("/backpack admin setowner <newowner>");
//                sender.sendMessage("/backpack admin setowner <id-backpack> <newowner>");
//             **   sender.sendMessage("/backpack admin clear");**
//                sender.sendMessage("/backpack admin clear <id-backpack>");
//              **  sender.sendMessage("/backpack admin lock");**
//                sender.sendMessage("/backpack admin lock <id-backpack>");
//              **  sender.sendMessage("/backpack admin unlock");**
//                sender.sendMessage("/backpack admin unlock <id-backpack>");
//             **   sender.sendMessage("/backpack admin enable");**
//                sender.sendMessage("/backpack admin enable <id-backpack>");
//           **     sender.sendMessage("/backpack admin disable");**
//                sender.sendMessage("/backpack admin disable <id-backpack>");
//            **    sender.sendMessage("/backpack admin reset");**
//                sender.sendMessage("/backpack admin reset <id-backpack>");
//                sender.sendMessage("/backpack admin setcode <id-backpack> <code>");
//             **   sender.sendMessage("/backpack admin setcode <code>");
//                sender.sendMessage("/backpack admin open <id-backpack>");
//            **    sender.sendMessage("/backpack admin info");**
//                sender.sendMessage("/backpack admin info <id-backpack>");
//backpack admin setowner id naam
            //0    1     2    3
                if(args[0].equalsIgnoreCase("admin")) {
                    if (args[1].equalsIgnoreCase("setcode") || args[1].equalsIgnoreCase("setowner")) {

                        if (p.getInventory().getItemInMainHand().getType() != Material.CHEST) {
                            sender.sendMessage(red + "This is not a valid backpack.");
                            return true;
                        }
                        if (!p.getInventory().getItemInMainHand().hasItemMeta()) {
                            sender.sendMessage(red + "This is not a valid backpack.");
                            return true;
                        }
                        ItemStack is = p.getInventory().getItemInMainHand();
                        ItemMeta ism = is.getItemMeta();
                        if (!ism.hasLore()) {
                            sender.sendMessage(red + "This is not a valid backpack.");
                            return true;
                        }
                        if (!is.getItemMeta().getLore().get(0).equalsIgnoreCase("Backpack")) {
                            sender.sendMessage(red + "This is not a valid backpack.");
                            return true;
                        }
                        Backpack b = Backpack.getbackpack(Integer.parseInt(is.getItemMeta().getLore().get(1)));
                        if (args[1].equalsIgnoreCase("setcode")) {
                            if (b.getpassword() == 0) {
                                sender.sendMessage(red + "There are currently no security locks on this backpack.");
                                sender.sendMessage(red + "First add a lock to this backpack then you can change the code.");
                                sender.sendMessage(red + "/password admin enable");
                                sender.sendMessage(red + "/password admin setcode <code>");
                                return true;
                            } else {
                                int code = 0;
                                try {
                                    code = Integer.parseInt(args[2]);
                                } catch (Exception e) {
                                    sender.sendMessage(red + "This is not a valid code.");
                                    return true;
                                }
                                if (!(code > 99 && code < 1000)) {
                                    sender.sendMessage(red + "This code is to short or to long, please try again.");
                                    return true;
                                }
                                b.setPassword(code);
                                b.save();

                                sender.sendMessage(yellow + "You have set the code to " + code + ".");
                                return true;
                            }
                        }
                        if (args[1].equalsIgnoreCase("setowner")) {
                            if (Bukkit.getOfflinePlayer(args[2]) != null) {
                                b.setOwner(args[2]);
                                b.save();

                                p.sendMessage(yellow + "You have changed the ownership to " + args[2] + ".");
                                return true;

                            } else {
                                sender.sendMessage(red + "This player is never been online before.");
                                return true;
                            }

                        }
                    }
                    if (args[1].equalsIgnoreCase("clear")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));
                            b.setcontent("0");
                            b.save();

                            sender.sendMessage("Cleared.");
                        } else {
                            sender.sendMessage(red + "That is not a valid backpack.");
                            return true;
                        }
                    }
                    if (args[1].equalsIgnoreCase("info")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));
                            sender.sendMessage(ChatColor.RED + "Information about \n" + b.getUUID());
                            sender.sendMessage(ChatColor.RED + "Owner >> " + b.getOwner());
                            sender.sendMessage(ChatColor.RED + "Size >> " + b.getSize());
                            sender.sendMessage(ChatColor.RED + "Code >> " + b.getpassword());
                        } else {
                            sender.sendMessage(red + "That is not a valid backpack.");
                            return true;
                        }
                    }
                    if (args[1].equalsIgnoreCase("reset")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));


                            int code = newcode();
                            sender.sendMessage(yellow + "Your new password is " +bold  + code  + yellow+ ".");
                            b.setPassword(code);
                            b.save();

                            return true;
                        } else {
                            sender.sendMessage(red + "That is not a valid backpack.");
                            return true;
                        }
                    }
                    if (args[1].equalsIgnoreCase("enable")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));
                            if (b.getpassword() == 0) {
                                int code = newcode();
                                sender.sendMessage(yellow + "Your new password is " +bold +  code + yellow  + ".");
                                b.setPassword(code);
                                b.save();

                            } else {
                                sender.sendMessage(red + "There is currently a password for this backpack.");
                                sender.sendMessage(red + "Forgotten? , /backpack reset");
                            }
                        } else {
                            sender.sendMessage(red + "That is not a valid backpack.");
                            return true;
                        }
                    }
                    if (args[1].equalsIgnoreCase("disable")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));
                            if (b.getpassword() != 0) {
                                sender.sendMessage(yellow + "You have removed the password from this backpack.");
                                b.setStatus(Backpack.Status.Open);
                                b.setPassword(0);
                                b.save();

                                return true;
                            }

                        }
                    }
                    if (args[1].equalsIgnoreCase("lock")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));
                            if (b.getpassword() == 0) {
                                sender.sendMessage(red + "There are currently no security locks on this backpack.");
                                return true;
                            }
                            if (b.getstatus() == Backpack.Status.Locked) {
                                p.sendMessage(red + "This backpack is already locked.");
                            } else {
                                b.setStatus(Backpack.Status.Locked);
                                b.save();

                                p.sendMessage(yellow + "You have locked your backpack.");
                                return true;
                            }
                        }
                    }
                    if (args[1].equalsIgnoreCase("unlock")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));

                            if (b.getpassword() == 0) {
                                sender.sendMessage(red + "There are currently no security locks on this backpack.");
                                return true;
                            }
                            if (b.getstatus() == Backpack.Status.Open) {
                                p.sendMessage(red +"This backpack is already unlocked.");
                            } else {
                                b.setStatus(Backpack.Status.Open);
                                p.sendMessage(red + "You have unlocked your backpack.");
                                b.save();

                                return true;
                            }
                        }
                    }


                    if (args[1].equalsIgnoreCase("open")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[2]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[2]));
                            b.open(p);
                            return true;
                        }
                    }
                }
            }
                if(args.length == 4){
                    if(args[1].equalsIgnoreCase("setcode")){
                        if (Backpack.isbackpack(Integer.parseInt(args[3]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[3]));
                                int code = 0;
                                try {
                                    code = Integer.parseInt(args[2]);
                                } catch (Exception e) {
                                    sender.sendMessage(red + "This is not a valid code.");
                                    return true;
                                }
                                if (!(code > 99 && code < 1000)) {
                                    sender.sendMessage( red +   "This code is to short or to long, please try again.");

                                    return true;
                                }
                                b.setPassword(code);
                            b.save();

                            p.sendMessage(yellow + "You have changed the password of backpack "  + bold +args[3] + yellow + " to " + bold  + code + yellow + ".");
                                return true;
                        }else{
                            p.sendMessage(red + "That is not a valid backpack.");
                        }

                    }


                    if(args[1].equalsIgnoreCase("setowner")) {
                        if (Backpack.isbackpack(Integer.parseInt(args[3]))) {
                            Backpack b = Backpack.getbackpack(Integer.parseInt(args[3]));
                            if (args[2].length() < 4) {
                                p.sendMessage(red + "The owner name must be longer then 3 characters.");
                                return true;
                            }
                            if (Bukkit.getOfflinePlayer(args[2]) == null) {
                                p.sendMessage(red + "That is not a valid username.");
                                return true;
                            }

                            b.setOwner(args[2]);
                            b.save();

                            p.sendMessage(yellow + "You have changed the ownership of backback " + bold + args[3]  + yellow+ " to "  + bold + args[2] + yellow + ".");
                            return true;
                        } else {
                            p.sendMessage(red +"That is not a valid backpack.");
                        }
                    }
            }
        }
        return false;
    }
}
