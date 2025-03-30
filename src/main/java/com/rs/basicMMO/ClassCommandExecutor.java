package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ClassCommandExecutor implements CommandExecutor {
    private BasicMMO plugin;

    public ClassCommandExecutor(BasicMMO plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player)){
            sender.sendMessage("Sadece oyuncular kullanabilir.");
            return true;
        }
        Player player = (Player) sender;
        if(args.length < 1){
            //player.sendMessage(ChatColor.RED + "Kullanım: /chooseclass <className>");
            return true;
        }

        String className = args[0].toLowerCase();
        FileConfiguration config = plugin.getConfig();

        // Sınıfın config'de tanımlı olup olmadığını kontrol ediyoruz
        if(!config.isConfigurationSection("classes." + className)){
            player.sendMessage(ChatColor.RED + "Bu sınıf bulunamadı.");
            return true;
        }

        // Eğer oyuncunun zaten bir sınıfı varsa, değişiklik durumunda seviye sıfırlanır.
        if(config.contains("players." + player.getUniqueId() + ".class")){
            String oldClass = config.getString("players." + player.getUniqueId() + ".class");
            if(oldClass.equalsIgnoreCase(className)){
                player.sendMessage(ChatColor.YELLOW + "Zaten bu sınıfa sahipsin.");
                return true;
            }
            // Eski sınıfın efektini kaldırıyoruz
            if (oldClass.equals("miner")) {
                player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                player.removePotionEffect(PotionEffectType.HASTE);
            } else if (oldClass.equals("fisherman")) {
                player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
            }
            else if (oldClass.equals("timberman")) {
                player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                player.removePotionEffect(PotionEffectType.STRENGTH);
            }
            player.sendMessage(ChatColor.YELLOW + "Sınıf değiştirildi, seviye sıfırlandı.");
        }

        // Oyuncu bilgilerini güncelliyoruz: seçilen sınıf ve seviye 1
        config.set("players." + player.getUniqueId() + ".class", className);
        config.set("players." + player.getUniqueId() + ".level", 1);
        plugin.saveConfig();

        return true;
    }
}
