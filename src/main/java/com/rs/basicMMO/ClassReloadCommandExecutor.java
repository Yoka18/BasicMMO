package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClassReloadCommandExecutor implements CommandExecutor {
    private BasicMMO plugin;

    public ClassReloadCommandExecutor(BasicMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Sadece oyuncular kullanabilir
        if (!(sender instanceof Player)) {
            sender.sendMessage("Sadece oyuncular kullanabilir.");
            return true;
        }
        Player player = (Player) sender;
        FileConfiguration config = plugin.getConfig();
        String playerClass = config.getString("players." + player.getUniqueId() + ".class");
        int level = config.getInt("players." + player.getUniqueId() + ".level", 1);

        if (playerClass == null) {
            //player.sendMessage(ChatColor.RED + "Önce bir sınıf seçmelisin.");
            return true;
        }

        // Seçili class'a göre efektleri yeniden uyguluyoruz
        if (playerClass.equalsIgnoreCase("miner")) {
            if(level == 10){
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                player.sendMessage(ChatColor.GREEN + "Öldükten sonra Miner etkisi yeniden uygulandı.");
            }
        } else if (playerClass.equalsIgnoreCase("fishman")) {
            if(level == 10){
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                player.sendMessage(ChatColor.GREEN + "Öldükten sonra Fisherman etkisi yeniden uygulandı.");
            }
        } else if (playerClass.equalsIgnoreCase("timberman")) {
            if(level == 10){
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                player.sendMessage(ChatColor.GREEN + "Öldükten sonra Timberman etkisi yeniden uygulandı.");
            }
        }

        return true;
    }
}
