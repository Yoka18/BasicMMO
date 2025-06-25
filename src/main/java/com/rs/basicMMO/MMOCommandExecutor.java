package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class MMOCommandExecutor implements CommandExecutor {

    private final PlayerManager playerManager;
    private final GUIManager guiManager; // GUIManager eklendi
    private final List<String> availableClasses = Arrays.asList("miner", "fisherman", "timberman");

    public MMOCommandExecutor(PlayerManager playerManager, GUIManager guiManager) { // Yapıcı güncellendi
        this.playerManager = playerManager;
        this.guiManager = guiManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            guiManager.openMainMenu(player); // Argümansız /mmo komutu artık GUI'yi açar
            return true;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "help":
                sendHelpMessage(player);
                break;
            case "info":
                sendInfoMessage(player);
                break;
            case "class":
                handleClassCommand(player, args);
                break;
            case "reload":
                playerManager.applyClassEffects(player);
                break;
            case "skills": // Yeni komut
                guiManager.openMainMenu(player);
                break;
            case "levelup": // Yeni komut (Admin/Debug için)
                if (player.isOp()) { // Sadece OP'lar kullanabilsin
                    playerManager.levelUp(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You are not authorized to use this command.");
                }
                break;
            default:
                player.sendMessage(ChatColor.RED + "Unknown command. /mmo help for help.");
                break;
        }
        return true;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(ChatColor.GOLD + "==== BasicMMO Help Menu ====");
        player.sendMessage(ChatColor.AQUA + "/mmo" + ChatColor.WHITE + " - Opens the skill tree menu.");
        player.sendMessage(ChatColor.AQUA + "/mmo info" + ChatColor.WHITE + " - Shows your XP and level information.");
        player.sendMessage(ChatColor.AQUA + "/mmo class <sınıf>" + ChatColor.WHITE + " - Chooses a class.");
        player.sendMessage(ChatColor.AQUA + "/mmo reload" + ChatColor.WHITE + " - Reloads your class effects.");
        player.sendMessage(ChatColor.GOLD + "==== Available Classes ====");
        player.sendMessage(ChatColor.WHITE + "Miner, Fisherman, Timberman");
    }

    private void sendInfoMessage(Player player) {
        PlayerManager.PlayerData data = playerManager.getPlayerData(player);
        if (data == null) {
            player.sendMessage(ChatColor.RED + "You haven't chosen a class yet. /mmo class <class>");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "Class: " + data.getClassName() + " | Level: " + data.getLevel() + " | XP: " + String.format("%.2f", data.getXp()) + " / " + 100);
    }

    private void handleClassCommand(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Usage: /mmo class <class_name>");
            return;
        }
        String className = args[1].toLowerCase();
        if (!availableClasses.contains(className)) {
            player.sendMessage(ChatColor.RED + "Invalid class. Available classes: Miner, Fisherman, Timberman");
            return;
        }

        PlayerManager.PlayerData currentData = playerManager.getPlayerData(player);
        if (currentData != null && currentData.getClassName().equalsIgnoreCase(className)) {
            player.sendMessage(ChatColor.YELLOW + "You already chose this class.");
            return;
        }

        playerManager.chooseClass(player, className);
    }
}