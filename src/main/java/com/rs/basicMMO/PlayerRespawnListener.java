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

        if (playerClass != null) {

            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (playerClass.equalsIgnoreCase("miner")) {
                    if(level >= 5)
                    {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, config.getInt("classes.miner.haste.amplifier")));
                    }
                    if(level == 10){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, config.getInt("classes.miner.haste.amplifier")));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, config.getInt("classes.miner.healthBoost.amplifier")));
                    }
                    player.sendMessage(ChatColor.GREEN + "Öldükten sonra Miner etkisi yeniden uygulandı.");
                } else if (playerClass.equalsIgnoreCase("fishman")) {
                    if(level >= 5)
                    {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, config.getInt("classes.fisherman.night.amplifier")));
                    }
                    if(level == 10){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, config.getInt("classes.fisherman.night.amplifier")));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, config.getInt("classes.fisherman.healthBoost.amplifier")));
                    }
                    player.sendMessage(ChatColor.GREEN + "Öldükten sonra Fisherman etkisi yeniden uygulandı.");
                } else if (playerClass.equalsIgnoreCase("timberman")) {
                    if(level >= 5)
                    {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, config.getInt("classes.timberman.strength.amplifier")));
                    }
                    if(level == 10){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, config.getInt("classes.timberman.strength.amplifier")));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, config.getInt("classes.timberman.healthBoost.amplifier")));
                    }
                    player.sendMessage(ChatColor.GREEN + "Öldükten sonra Timberman etkisi yeniden uygulandı.");
                }
            });
        }
    }
}
