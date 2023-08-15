package com.jeff_media.dupe;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Dupe extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("dupe").setExecutor(this);
    }

    public boolean getRequirePermission() {
        return getConfig().getBoolean("require-permission", true);
    }

    public boolean getRequirePermissionPerItem() {
        return getConfig().getBoolean("require-permission-per-item", true);
    }

    public boolean hasPermissionToDupe(Player player, ItemStack item) {
        if(!getRequirePermission()) return true;
        if(getRequirePermissionPerItem()) {
            return player.hasPermission("dupe." + item.getType().name().toLowerCase());
        } else {
            return player.hasPermission("dupe");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "[Dupe] Configuration reloaded.");
            return true;
        }
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null) return true;
        if(!player.hasPermission("dupe." + item.getType().name().toLowerCase())) {
            player.sendMessage(ChatColor.RED + "You cannot dupe this item!");
            return true;
        }
        item = item.clone();
        player.getInventory().addItem(item);
        return true;
    }
}
