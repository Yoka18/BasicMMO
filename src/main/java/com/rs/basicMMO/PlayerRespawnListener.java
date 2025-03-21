package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerRespawnListener implements Listener {
    private BasicMMO plugin;

    public PlayerRespawnListener(BasicMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();
        String playerClass = config.getString("players." + player.getUniqueId() + ".class");
        int level = config.getInt("players." + player.getUniqueId() + ".level", 1);

        if (playerClass != null) {
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
        }
    }
}
