package com.rs.basicMMO;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class BasicMMO extends JavaPlugin {

    private PlayerManager playerManager;
    private GUIManager guiManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Yönetici sınıflarını başlat
        this.playerManager = new PlayerManager(this);
        this.guiManager = new GUIManager(playerManager);

        // Komut yöneticisini kaydet (GUIManager'ı da constructor'a ekliyoruz)
        getCommand("mmo").setExecutor(new MMOCommandExecutor(playerManager, guiManager));
        getCommand("takexp").setExecutor(new TakeXpCommandExecutor(this)); // Bu komut hala bağımsız çalışabilir.

        // Listener'ları kaydet
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerFishListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new GUIListener(guiManager), this); // Yeni GUI listener
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this, playerManager), this); // Güncellenmiş Respawn listener

        // Sunucudaki mevcut oyuncuları yükle (reload durumları için)
        for (Player player : getServer().getOnlinePlayers()) {
            playerManager.loadPlayerData(player);
        }

        getLogger().info("Basic MMO was successfully loaded.");
    }

    @Override
    public void onDisable() {
        // Sunucudaki tüm oyuncuların verilerini kaydet
        for (Player player : getServer().getOnlinePlayers()) {
            playerManager.savePlayerData(player);
        }
        getLogger().info("BasicMMO was successfully shut down.");
    }

    // Diğer sınıfların yöneticilere erişmesi için getter metotları (isteğe bağlı)
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }
}