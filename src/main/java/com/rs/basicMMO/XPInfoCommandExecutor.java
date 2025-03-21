package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class XPInfoCommandExecutor implements CommandExecutor {

    private BasicMMO plugin;

    public XPInfoCommandExecutor(BasicMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sadece oyuncular kullanabilir.");
            return true;
        }
        Player player = (Player) sender;
        FileConfiguration config = plugin.getConfig();
        String uuid = player.getUniqueId().toString();

        double xp = config.getDouble("players." + uuid + ".xp", 0.0);
        int level = config.getInt("players." + uuid + ".level", 1);

        player.sendMessage(ChatColor.GREEN + "XP: " + xp + " | Seviye: " + level);
        return true;
    }
}
