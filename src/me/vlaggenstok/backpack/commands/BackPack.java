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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class BackPack extends Util implements CommandExecutor {

    Backpacks ps = Backpacks.getInstance();


    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if (cmd.getName().equalsIgnoreCase("backpack")) {
            //region
            if (!(sender instanceof Player)) {
                // todo admin commands?
                return true;
            }
            Player p = (Player) sender;
            String[] commandArgs = args;
            String[] imASepcialComMaND = new String[]{"setowner", "disable", "setcode"};
            String[] needtobeownerCmds = new String[]{"disable", "setowner", "enable"};
            String[] needCmds = new String[]{"reset", "enable"};

            boolean Admin = false;
            if (commandArgs.length == 0) {
                adminhelp(sender, false);
            } else {
                // admin arguments
                String id = null;
                String argument = null;
                if (commandArgs[0].equalsIgnoreCase("admin")) {
                    ArrayList<String> a = new ArrayList<>(Arrays.asList(args));
                    a.remove(0);
                    commandArgs = a.toArray(new String[0]);
                    if (commandArgs.length > 0 && (sender.hasPermission("BasicBackpack.admin.*") || sender.hasPermission("BasicBackpack.admin." + args[1]))) {
                        Admin = true;
                        if (commandArgs.length == 1) {
                            if (Arrays.asList(imASepcialComMaND).contains(commandArgs[0])) {
                                sender.sendMessage(ChatColor.RED + "Invalid command usage.");
                                sender.sendMessage(ChatColor.RED + invalidarg(commandArgs[0].toLowerCase(), true));
                                return true;
                            }
                            if (isbackpackid(p)) {
                                id = getid(p.getItemInHand().getItemMeta().getLore());
                            } else {
                                sender.sendMessage(red + "That is not a valid backpack.");
                                return true;
                            }
                        } else if (commandArgs.length == 2) {
                            if (Arrays.asList(imASepcialComMaND).contains(commandArgs[0])) {
                                if (isbackpackid(p)) {
                                    argument = commandArgs[1]; // new Owner/ password
                                    id = getid(p.getItemInHand().getItemMeta().getLore()); // todo get backpack id.
                                } else {
                                    sender.sendMessage(red + "That is not a valid backpack.");
                                    return true;
                                }
                            } else {
                                id = commandArgs[1];
                                if (!isbackpackid(id)) {
                                    sender.sendMessage(red + "That is not a valid backpack.");
                                    return true;
                                }
                            }
                        } else {
                            if (Arrays.asList(imASepcialComMaND).contains(commandArgs[0])) {
                                argument = commandArgs[1]; // new Owner/ password
                                id = commandArgs[2];
                                if (!isbackpackid(id)) {
                                    sender.sendMessage(red + "That is not a valid backpack.");
                                    return true;
                                }
                            }
                        }
                    } else {
                        if (sender.hasPermission("BasicBackpack.admin.*")) {
                            adminhelp(sender, Admin);
                            return true;
                        }
                        sender.sendMessage(ChatColor.RED + "You dont have permission to do that!");
                        return true;
                    }
                } else {


                    Admin = false;
                    if (!isbackpackid(p)) {
                        p.sendMessage("geen geldige backpack");
                        return true;
                    }
                    id = getid(p.getItemInHand().getItemMeta().getLore());
                    Backpack b = Backpack.getBackpackSafe(id);
                    if (args.length == 1) {
                        if (!Arrays.asList(needCmds).contains(args[0])) {
                            if (Arrays.asList(needtobeownerCmds).contains(args[0])) {
                                if (!b.getOwner().equalsIgnoreCase(sender.getName())) {
                                    sender.sendMessage(red + "You'r not the owner of this backpack.");
                                    return true;
                                }
                            }
                            sender.sendMessage(ChatColor.RED + "Invalid command usage.");
                            sender.sendMessage(red + invalidarg(args[0].toLowerCase(), false));
                            return true;
                        } else {
                            //reset
                            if (!b.getOwner().equalsIgnoreCase(sender.getName())) {
                                sender.sendMessage(red + "You'r not the owner of this backpack.");
                                return true;
                            }
                        }
                    } else if (args.length == 2) {
                        if (Arrays.asList(needtobeownerCmds).contains(args[0])) {
                            if (!b.getOwner().equalsIgnoreCase(sender.getName())) {
                                sender.sendMessage(red + "You'r not the owner of this backpack.");
                                return true;
                            }

                        }
                        argument = args[1];
                    }
                }

                if (id == null) {
                    id = "";
                }

                switch (commandArgs[0].toLowerCase()) {
                    case "clear":
                        clear(sender, id);
                        break;
                    case "disable":
                        if (argument != null) {
                            disable(sender, id, argument);
                        }
                        break;
                    case "reset":
                        reset(sender, id);
                        break;
                    case "get":
                        getBackpack(sender);
                        break;
                    case "enable":
                        enable(sender, id);
                        break;
                    case "setowner":
                        setowner(sender, id, argument);
                        break;
                    case "setcode":
                        setcode(sender, id, argument);
                        break;
                    case "lock":
                        lock(sender, id, argument, Admin);
                        break;
                    case "unlock":
                        unlock(sender, id, argument, Admin);
                        break;
                    case "info":
                        info(sender, id);
                        break;
                    case "open":
                        open(sender, id);
                        break;
                    case "help":
                        adminhelp(sender, Admin);
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "Invalid usage");
                        break;
                }
            }
        }
        return true;
    }


    private String invalidarg(String ar, boolean admincmd) {
        if (admincmd) {
            switch (ar) {

                case "setowner":
                    return "/backpack admin setowner [newowner] <id-backpack>";
                case "clear":
                    return "/backpack admin clear  <id-backpack>";
                case "lock":
                    return "/backpack admin lock <id-backpack>";
                case "unlock":
                    return "/backpack admin unlock <id-backpack>";
                case "disable":
                    return "/backpack admin disable [code] <id-backpack>";
                case "enable":
                    return "/backpack admin enable <id-backpack>";
                case "reset":
                    return "/backpack admin reset <id-backpack>";
                case "setcode":
                    return "/backpack admin setcode [newcode] <id-backpack>";
                case "open":
                    return "/backpack admin open <id-backpack>";
                case "info":
                    return "/backpack admin info <id-backpack>";


            }
        } else {
            switch (ar) {
                case "setowner":
                    return "/backpack setowner <newowner>";
                case "lock":
                    return "/backpack lock <code>";
                case "unlock":
                    return "/backpack unlock <code>";
                case "disable":
                    return "/backpack disable [code]";
                case "enable":
                    return "/backpack enable <code>";
                case "reset":
                    return "/backpack reset";

            }
        }

        return "d";
    }

    private String getid(List<String> lore) {


        return lore.get(1);
    }

    private boolean setowner(CommandSender sender, String id, String playerName) {
        Backpack b = Backpack.getBackpackSafe(id);


        if (Bukkit.getOfflinePlayer(playerName) == null) {
            sender.sendMessage(red + "That is not a valid username.");
            return true;
        }

        b.setOwner(playerName);
        b.save();
        sender.sendMessage(yellow + "You have changed the ownership of backback " + bold + id + yellow + " to " + bold + playerName + yellow + ".");
        return true;
    }

    private boolean setcode(CommandSender sender, String id, String code) {
        Backpack b = Backpack.getBackpackSafe(id);


        int pw;
        try {

            pw = Integer.parseInt(code);


        } catch (NumberFormatException e) {

            sender.sendMessage(red + "Invalid password.");
            return true;
        }
        if (!(pw > 99 && pw < 1000)) {
            sender.sendMessage(red + "This code is to short or to long, please try again.");
            return true;
        }
        b.setPassword(pw);

        b.save();
        sender.sendMessage(yellow + "You have changed the password of backback " + bold + id + yellow + " to " + bold + code + yellow + ".");
        return true;
    }

    private boolean isbackpackid(Player p) {

        if (p.getItemInHand() == null) {
            return false;
        }
        ItemStack it = p.getItemInHand();
        if (!it.hasItemMeta()) {
            return false;
        }
        ItemMeta itm = it.getItemMeta();
        if (!itm.hasLore()) {
            return false;
        }
        List<String> lo = itm.getLore();
        if (!(lo.size() > 2 && lo.get(0).equalsIgnoreCase("Backpack"))) {
            return false;
        }
        int a = 0;
        try {
            a = Integer.valueOf(getid(lo));
        } catch (Exception e) {
            return false;
        }
        if (!Backpack.isbackpack(a)) {
            return false;
        }
        return true;
    }

    private boolean isbackpackid(ItemStack i) {

        ItemStack it = i;
        if (!it.hasItemMeta()) {
            return false;
        }
        ItemMeta itm = it.getItemMeta();
        if (!itm.hasLore()) {
            return false;
        }
        List<String> lo = itm.getLore();
        if (!(lo.size() > 2 && lo.get(0).equalsIgnoreCase("Backpack"))) {
            return false;
        }
        int a = 0;
        try {
            a = Integer.valueOf(getid(lo));
        } catch (Exception e) {
            return false;
        }
        if (!Backpack.isbackpack(a)) {
            return false;
        }
        return true;
    }

    private boolean isbackpackid(String s) {
        int a = 0;
        try {
            a = Integer.valueOf(s);
        } catch (Exception e) {
            return false;
        }
        if (!Backpack.isbackpack(a)) {
            return false;
        }
        return true;
    }

    private boolean getBackpack(CommandSender sender) {
        Player p = (Player) sender;
        Backpack b = new Backpack((Player) sender);
        ItemStack it = new ItemStack(Material.CHEST);
        ItemMeta itm = it.getItemMeta();
        itm.setLore(Arrays.asList("Backpack", b.getUUID() + "", "Locked"));
        it.setItemMeta(itm);
        p.getInventory().addItem(it);
        p.sendMessage(yellow + "You have recieved a new backpack.");
        return true;
    }

    private boolean reset(CommandSender sender, String id) {

        Backpack b = Backpack.getBackpackSafe(id);
        if (b.getpassword() == 0) {

            sender.sendMessage(yellow + "This backpack isn't enabled.");
            return true;
        }
        int cod = newcode();
        b.setPassword(cod);
        b.save();
        sender.sendMessage(yellow + "Your new code is " + red + cod + yellow + ".");
        return true;
    }

    private boolean lock(CommandSender sender, String id, String password, boolean perms) {
        Backpack b = Backpack.getBackpackSafe(id);


        int a = 0;
        if (!perms) {
            try {
                a = Integer.valueOf(password);
            } catch (Exception e) {
                sender.sendMessage(red + "Invalid code.");
                return true;
            }
        }
        if (b.getstatus() == Backpack.Status.Locked) {
            sender.sendMessage(red + "This backpack is already Locked.");
            return true;
        }
        if (b.getpassword() == 0) {
            sender.sendMessage(red + "This backpack is currently disabled.");
            return true;
        }
        if (!perms) {
            if (b.getpassword() == a) {
                b.setStatus(Backpack.Status.Locked);
                b.save();
                sender.sendMessage(yellow + "You have locked backpack " + bold + id + yellow + ".");
                return true;
            } else {
                sender.sendMessage(red + "Invalid code.");
                return true;
            }
        } else {
            b.setStatus(Backpack.Status.Locked);
            b.save();
            sender.sendMessage(yellow + "You have locked backpack  " + bold + id + yellow + ".");
            return true;
        }


    }

    private boolean unlock(CommandSender sender, String id, String password, boolean perms) {
        Backpack b = Backpack.getBackpackSafe(id);


        int a = 0;
        if (!perms) {
            try {
                a = Integer.valueOf(password);
            } catch (Exception e) {
                sender.sendMessage(red + "Invalid code.");
                return true;
            }
        }
        if (b.getstatus() == Backpack.Status.Open) {
            sender.sendMessage(red + "This backpack is already open.");
            return true;
        }
        if (b.getpassword() == 0) {
            sender.sendMessage(red + "This backpack is currently disabled.");
            return true;
        }
        if (!perms) {
            if (b.getpassword() == a) {
                b.setStatus(Backpack.Status.Open);
                b.save();
                sender.sendMessage(yellow + "You have unlocked backpack " + bold + id + yellow + ".");
                return true;
            }
        } else {
            b.setStatus(Backpack.Status.Open);
            b.save();
            sender.sendMessage(yellow + "You have unlocked backpack " + bold + id + yellow + ".");
        }

        return true;
    }

    private boolean enable(CommandSender sender, String id) {
        Backpack b = Backpack.getBackpackSafe(id);

        if (b.getpassword() != 0) {
            sender.sendMessage(yellow + "This backpack is already enabled.");
            return true;
        } else {


            int co = newcode();
            b.setStatus(Backpack.Status.Locked);
            b.setPassword(co);
            b.save();
            sender.sendMessage(yellow + "You have changed the status of backback " + bold + id + yellow + " to " + bold + "enabled" + yellow + ".");
            sender.sendMessage(yellow + "Your new code is " + red + co + yellow + ".");
            return true;


        }
    }

    private boolean disable(CommandSender sender, String id, String argument) {
        Backpack b = Backpack.getBackpackSafe(id);
        int pw = 0;
        try {
            pw = Integer.valueOf(argument);
        } catch (Exception e) {
            sender.sendMessage(red + "Invalid code.");
            return true;
        }


        if (b.getpassword() == pw) {
            b.setStatus(Backpack.Status.Open);
            b.setPassword(0);
            b.save();
            sender.sendMessage(yellow + "You have changed the status of backback " + bold + id + yellow + " to " + bold + "disabled" + yellow + ".");
            return true;
        } else if (b.getpassword() == 0) {
            sender.sendMessage(yellow + "This backpack is already disabled.");
            return true;

        } else {
            sender.sendMessage(red + "Invalid code");
        }
        return true;
    }

    private boolean clear(CommandSender sender, String id) {
        Backpack b = Backpack.getBackpackSafe(id);
        b.setcontent("0");
        b.save();
        sender.sendMessage(yellow + "You have cleared the content of backback " + bold + id + yellow + ".");
        return true;
    }

    private boolean open(CommandSender sender, String id) {
        Backpack b = Backpack.getBackpackSafe(id);
        b.open((Player) sender);
        sender.sendMessage(yellow + "Opened backpack of " + bold + b.getOwner() + yellow + " with id " + bold + b.getUUID() + yellow + ".");
        return true;
    }

    private boolean info(CommandSender sender, String id) {
        Backpack b = Backpack.getBackpackSafe(id);
        sender.sendMessage(red + "" + bold + "Backpack data");
        sender.sendMessage(yellow + "Id » " + b.getUUID());
        sender.sendMessage(yellow + "Owner » " + b.getOwner());
        sender.sendMessage(yellow + "Password » " + b.getpassword());
        sender.sendMessage(yellow + "Status » " + b.getstatus());
        sender.sendMessage(yellow + "Size » " + b.getSize());


        return true;
    }

    private boolean adminhelp(CommandSender sender, boolean a) {
        if (a) {
            sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Backpack Admin Options");
            sender.sendMessage(ChatColor.RED + "/backpack admin setowner <newowner> [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To set the owner of a Backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack admin clear [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To clear the content of a Backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack admin lock [id-backpack] ");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To lock a Backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack admin unlock [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To unlock a backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack admin enable [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To enable the lockfunction.");
            sender.sendMessage(ChatColor.RED + "/backpack admin disable [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To disable the lock option.");
            sender.sendMessage(ChatColor.RED + "/backpack admin reset [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Reset the password of a backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack admin setcode <newcode> [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Set the password of a backpack");
            sender.sendMessage(ChatColor.RED + "/backpack admin open [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Open a backpack");
            sender.sendMessage(ChatColor.RED + "/backpack admin info [id-backpack]");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Information abbout a backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack admin help");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Help commands");


        } else {
            sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Backpack Options");
            sender.sendMessage(ChatColor.RED + "/backpack reset");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To reset that password of the holding Backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack disable <code>");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To disable the password option of the holding Backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack enable <code>");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To enable the password option of the holding Backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack setowner <new owner>");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Set owner of this backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack lock <code>");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To lock your backpack.");
            sender.sendMessage(ChatColor.RED + "/backpack unlock <code>");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To unlock your backpack.");
        }
        return true;
    }

    //                sender.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Backpack Options");
//                sender.sendMessage(ChatColor.RED + "/backpack reset");
//                sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To reset that password of the holding Backpack.");
//                sender.sendMessage(ChatColor.RED + "/backpack disable <password>");
//                sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To disable the password option of the holding Backpack.");
//                sender.sendMessage(ChatColor.RED + "/backpack enable ");
//                sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To enable the password option of the holding Backpack.");
//                sender.sendMessage(ChatColor.RED + "/backpack setowner <new owner>");
//                sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Set owner of this backpack.");
//                sender.sendMessage(ChatColor.RED + "/backpack lock");
//                sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To lock your backpack.");
//                sender.sendMessage(ChatColor.RED + "/backpack unlock");
//                sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "To unlock your backpack.");
}

