package com.rs.basicMMO;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerRespawnListener implements Listener {
    private final PlayerManager playerManager;
    private final JavaPlugin plugin;

    public PlayerRespawnListener(JavaPlugin plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // Efektlerin oyuncu yeniden doğduktan hemen sonra uygulanması için küçük bir gecikme ekliyoruz.
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            playerManager.applyClassEffects(player);
        }, 20L); // 1 saniye (20 tick) sonra çalıştır
    }
}