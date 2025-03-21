package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommandExecutor implements CommandExecutor {
    private BasicMMO plugin;

    public HelpCommandExecutor(BasicMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sadece oyuncular kullanabilir.");
            return true;
        }
        Player player = (Player) sender;
        player.sendMessage(ChatColor.GOLD + "==== Sınıf Listesi ====");
        player.sendMessage(ChatColor.WHITE + "Miner");
        player.sendMessage(ChatColor.WHITE + "Fisherman");
        player.sendMessage(ChatColor.WHITE + "Timberman");
        player.sendMessage(ChatColor.GOLD + "==== Yardım Menüsü ====");
        player.sendMessage(ChatColor.AQUA + "/chooseclass <className>" + ChatColor.WHITE + " - Sınıf seçimi yapar.");
        player.sendMessage(ChatColor.AQUA + "/xpinfo" + ChatColor.WHITE + " - XP ve seviye bilgilerinizi gösterir.");
        player.sendMessage(ChatColor.AQUA + "/classreload" + ChatColor.WHITE + " - Class efektlerini yeniden yükler.");
        player.sendMessage(ChatColor.GOLD + "=======================");
        return true;
    }
}
