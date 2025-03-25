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
        final Player player = event.getPlayer();
        final FileConfiguration config = plugin.getConfig();
        final String playerClass = config.getString("players." + player.getUniqueId() + ".class");
        final int level = config.getInt("players." + player.getUniqueId() + ".level", 1);

        if (playerClass != null && level == 10) {

            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (playerClass.equalsIgnoreCase("miner")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    player.sendMessage(ChatColor.GREEN + "Öldükten sonra Miner etkisi yeniden uygulandı.");
                } else if (playerClass.equalsIgnoreCase("fishman")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    player.sendMessage(ChatColor.GREEN + "Öldükten sonra Fisherman etkisi yeniden uygulandı.");
                } else if (playerClass.equalsIgnoreCase("timberman")) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    player.sendMessage(ChatColor.GREEN + "Öldükten sonra Timberman etkisi yeniden uygulandı.");
                }
            });
        }
    }
}
