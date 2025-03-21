package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LevelUpCommandExecutor implements CommandExecutor {
    private BasicMMO plugin;
    private final int MAX_LEVEL = 10;

    public LevelUpCommandExecutor(BasicMMO plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Sadece oyuncular kullanabilir.");
            return true;
        }
        Player player = (Player) sender;
        FileConfiguration config = plugin.getConfig();

        // Oyuncunun seçtiği sınıfı kontrol ediyoruz
        String className = config.getString("players." + player.getUniqueId() + ".class");
        if(className == null){
            //player.sendMessage(ChatColor.RED + "Önce bir sınıf seçmelisin. /chooseclass <className>");
            return true;
        }

        // Mevcut seviye kontrolü
        int level = config.getInt("players." + player.getUniqueId() + ".level", 1);
        if(level >= MAX_LEVEL) {
            player.sendMessage(ChatColor.YELLOW + "Maksimum seviyeye ulaştın!");
            return true;
        }

        // Seviye artırma işlemi: mevcut seviye +1
        level++;
        config.set("players." + player.getUniqueId() + ".level", level);
        plugin.saveConfig();

        // Sınıfa özgü özellikleri güncelle
        updateClassFeature(player, className, level);

        return true;
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
