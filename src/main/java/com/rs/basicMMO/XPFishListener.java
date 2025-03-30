package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class XPFishListener implements Listener {
    private XPManager xpManager;
    private BasicMMO plugin;

    public XPFishListener(BasicMMO plugin) {
        this.plugin = plugin;
        this.xpManager = new XPManager(plugin);
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        // Sadece balık yakalandığında işlemleri yapalım
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();
            double baseXP = 0.5;
            double xpToAdd;

            FileConfiguration config = plugin.getConfig();
            String playerClass = config.getString("players." + player.getUniqueId() + ".class");

            // Fisherman sınıfı için özel hesaplamalar
            if (playerClass != null && playerClass.equalsIgnoreCase("fisherman")) {
                int level = config.getInt("players." + player.getUniqueId() + ".level", 1);
                // XP multiplier: seviye 1'de 1.25, her seviye başına 0.25 ekleniyor.
                double xpMultiplier = 1.25 + (level - 1) * 0.25;
                xpToAdd = baseXP * xpMultiplier;

                // Ekstra drop oranı: seviye başına %10 artış (seviye 1'de 1.0, seviye 10'da 1.9 gibi)
                double dropMultiplier = 1.0 + (level - 1) * 0.10;

                // Yakalanan öğe (Item) varsa miktarını artırıyoruz.
                if (event.getCaught() instanceof Item) {
                    Item caughtItem = (Item) event.getCaught();
                    ItemStack itemStack = caughtItem.getItemStack();
                    int originalAmount = itemStack.getAmount();
                    int newAmount = (int) Math.ceil(originalAmount * dropMultiplier);
                    itemStack.setAmount(newAmount);
                    caughtItem.setItemStack(itemStack);
                }

                // Fisherman sınıfı için ek "hazine" drop şansı (Luck of the Sea etkisi gibi)
                // Örnek: Seviye 1'de %10, her seviye %1 ekleniyor, maksimum %20
                double baseTreasureChance = 0.10;
                double levelBonus = (level - 1) * 0.01;
                double treasureChance = Math.min(baseTreasureChance + levelBonus, 0.50);

                if (Math.random() < treasureChance) {
                    // Örnek hazine: ENCHANTED_BOOK drop et
                    ItemStack treasure = new ItemStack(Material.ENCHANTED_BOOK, 1);
                    player.getWorld().dropItemNaturally(player.getLocation(), treasure);
                    player.sendMessage(ChatColor.GOLD + "Şansını kullandın! Ekstra hazine buldun!");
                }
            } else {
                // Diğer sınıflarda base XP oranı kullanılır.
                xpToAdd = baseXP;
            }

            xpManager.addXP(player, xpToAdd);
        }
    }
}
