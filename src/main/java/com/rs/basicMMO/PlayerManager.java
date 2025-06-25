package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    private final BasicMMO plugin;
    // Oyuncu verilerini UUID'lerine göre hafızada tutmak için bir harita (map)
    private final Map<UUID, PlayerData> playerDataMap = new HashMap<>();
    private final double XP_THRESHOLD = 100.0;
    private final int MAX_LEVEL = 10;

    public PlayerManager(BasicMMO plugin) {
        this.plugin = plugin;
    }

    // Oyuncu sunucuya girdiğinde verilerini config'den yükler
    public void loadPlayerData(Player player) {
        FileConfiguration config = plugin.getConfig();
        UUID uuid = player.getUniqueId();
        String path = "players." + uuid.toString();

        if (config.contains(path)) {
            String className = config.getString(path + ".class");
            int level = config.getInt(path + ".level", 1);
            double xp = config.getDouble(path + ".xp", 0.0);
            playerDataMap.put(uuid, new PlayerData(className, level, xp));
            applyClassEffects(player); // Efektleri uygula
        }
    }

    // Oyuncu sunucudan çıktığında verilerini config'e kaydeder
    public void savePlayerData(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerData data = playerDataMap.get(uuid);

        if (data != null) {
            FileConfiguration config = plugin.getConfig();
            String path = "players." + uuid.toString();
            config.set(path + ".class", data.getClassName());
            config.set(path + ".level", data.getLevel());
            config.set(path + ".xp", data.getXp());
            plugin.saveConfig();
            playerDataMap.remove(uuid); // Hafızadan kaldır
        }
    }

    // Oyuncunun verilerini almak için
    public PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player.getUniqueId());
    }

    // Yeni bir sınıf seçildiğinde
    public void chooseClass(Player player, String className) {
        // Eski efektleri temizle
        clearClassEffects(player);

        // Yeni veriyi oluştur ve haritaya ekle
        PlayerData newData = new PlayerData(className, 1, 0.0);
        playerDataMap.put(player.getUniqueId(), newData);
        player.sendMessage(ChatColor.GREEN + className + " Chose your class!");
    }

    // Oyuncuya XP ekleme ve seviye atlama mantığı
    public void addXp(Player player, double amount) {
        PlayerData data = getPlayerData(player);
        if (data == null || data.getLevel() >= MAX_LEVEL) {
            return; // Sınıf seçilmemişse veya maksimum seviyedeyse XP ekleme
        }

        data.setXp(data.getXp() + amount);

        while (data.getXp() >= XP_THRESHOLD && data.getLevel() < MAX_LEVEL) {
            data.setXp(data.getXp() - XP_THRESHOLD);
            data.setLevel(data.getLevel() + 1);
            player.sendMessage(ChatColor.AQUA + "Congratulations! You leveled up: " + data.getLevel());
            applyClassEffects(player); // Seviye atlandığında efektleri güncelle
        }

        if (data.getLevel() >= MAX_LEVEL) {
            data.setXp(0);
        }
    }

    public void levelUp(Player player) {
        PlayerData data = getPlayerData(player);
        if (data == null) {
            player.sendMessage(ChatColor.RED + "First you have to choose a class!");
            return;
        }
        if (data.getLevel() >= MAX_LEVEL) {
            player.sendMessage(ChatColor.YELLOW + "You're already at max level!");
            return;
        }

        data.setLevel(data.getLevel() + 1);
        player.sendMessage(ChatColor.AQUA + "Congratulations! You leveled up: " + data.getLevel());
        applyClassEffects(player);
    }

    // Sınıf efektlerini uygulama
    public void applyClassEffects(Player player) {
        PlayerData data = getPlayerData(player);
        if (data == null) return;

        clearClassEffects(player); // Önce eski efektleri temizle

        FileConfiguration config = plugin.getConfig();
        String className = data.getClassName();
        int level = data.getLevel();

        if (className.equalsIgnoreCase("miner")) {
            if (level >= 5) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, Integer.MAX_VALUE, config.getInt("classes.miner.haste.amplifier", 0)));
            }
            if (level == 10) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, config.getInt("classes.miner.healthBoost.amplifier", 0)));
            }
        } else if (className.equalsIgnoreCase("fisherman")) {
            if (level >= 5) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, config.getInt("classes.fisherman.night.amplifier", 0)));
            }
            if (level == 10) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, config.getInt("classes.fisherman.healthBoost.amplifier", 0)));
            }
        } else if (className.equalsIgnoreCase("timberman")) {
            if (level >= 5) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, Integer.MAX_VALUE, config.getInt("classes.timberman.strength.amplifier", 0)));
            }
            if (level == 10) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, config.getInt("classes.timberman.healthBoost.amplifier", 0)));
            }
        }
        player.sendMessage(ChatColor.GREEN + "Class effects applied.");
    }

    // Oyuncunun mevcut sınıfına ait tüm efektleri temizler
    private void clearClassEffects(Player player) {
        player.removePotionEffect(PotionEffectType.HASTE);
        player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        player.removePotionEffect(PotionEffectType.STRENGTH);
    }


    // Oyuncu verilerini tutmak için basit bir iç sınıf (inner class)
    public static class PlayerData {
        private String className;
        private int level;
        private double xp;

        public PlayerData(String className, int level, double xp) {
            this.className = className;
            this.level = level;
            this.xp = xp;
        }

        // Getter ve Setter metotları
        public String getClassName() { return className; }
        public int getLevel() { return level; }
        public double getXp() { return xp; }
        public void setLevel(int level) { this.level = level; }
        public void setXp(double xp) { this.xp = xp; }
    }
}