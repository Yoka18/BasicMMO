package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class XPManager {
    private BasicMMO plugin;
    private double xpThreshold = 100.0; // Seviye atlamak için gereken XP

    public XPManager(BasicMMO plugin) {
        this.plugin = plugin;
    }

    public void addXP(Player player, double xpToAdd) {
        FileConfiguration config = plugin.getConfig();
        String uuid = player.getUniqueId().toString();

        // Sınıf seçilmemişse XP eklenmez.
        if (!config.contains("players." + uuid + ".class")) {
            //player.sendMessage(ChatColor.RED + "Önce bir sınıf seçmelisin. /chooseclass <className>");
            return;
        }

        double xp = config.getDouble("players." + uuid + ".xp", 0.0);
        int level = config.getInt("players." + uuid + ".level", 1);

        // Eğer maksimum seviye (10) aşılmışsa XP eklemez veya XP barını sabit tutabilirsiniz.
        if (level >= 10) {
            // player.sendMessage(ChatColor.YELLOW + "Maksimum seviyeye ulaştın!");
            return;
        }

        xp += xpToAdd;

        // XP eşik değerine göre seviye atlama (maksimum 10 seviye)
        while (xp >= xpThreshold && level < 10) {
            xp -= xpThreshold;
            level++;

            // Seviye atladığında oyuncunun seçtiği sınıfa göre efekt güncellemesi yapılabilir.
            String className = config.getString("players." + uuid + ".class");
            updateClassFeature(player, className, level);
        }

        // Eğer seviye 10 olduysa, XP barını sıfırlayabilir veya eşik değeri olarak sabit tutabilirsiniz.
        if (level >= 10) {
            xp = 0; // ya da xpThreshold - 1 gibi bir değer
        }

        config.set("players." + uuid + ".xp", xp);
        config.set("players." + uuid + ".level", level);
        plugin.saveConfig();

        // player.sendMessage(ChatColor.GREEN + "XP eklendi. Şu anki XP: " + xp + ", Seviye: " + level);
    }

    // Her seviye için farklı özellikleri burada tanımlayabilirsiniz.
    private void updateClassFeature(Player player, String className, int level) {
        // Örnek: Her seviye için konsola farklı mesaj gönderme
        player.sendMessage(ChatColor.AQUA + "Tebrikler! " + level + ". seviyeye ulaştın.");

        // Seçili sınıfa göre efekt güncellemesi yapalım:
        if (className.equals("miner")) {
            switch (level) {
                case 10:
                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    player.sendMessage(ChatColor.GREEN + "Miner efektin güncellendi!");
                    break;
                default:
                    player.sendMessage(ChatColor.GREEN + "Miner: Seviye " + level);
                    break;
            }

        } else if (className.equals("fisherman")) {
            switch (level) {
                case 10:
                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    player.sendMessage(ChatColor.GREEN + "Fisherman efektin güncellendi!");
                    break;
                default:
                    player.sendMessage(ChatColor.GREEN + "Fisherman: Seviye " + level);
                    break;
            }

        } else if (className.equals("timberman")) {
            switch (level) {
                case 10:
                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
                    player.sendMessage(ChatColor.GREEN + "Timberman efektin güncellendi!");
                    break;
                default:
                    player.sendMessage(ChatColor.GREEN + "Timberman: Seviye " + level);
                    break;
            }
        }
    }

}
