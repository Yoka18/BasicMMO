package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerFishListener implements Listener {
    private final PlayerManager playerManager;

    public PlayerFishListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            Player player = event.getPlayer();
            PlayerManager.PlayerData data = playerManager.getPlayerData(player);
            double baseXP = 0.5;
            double xpToAdd = baseXP;

            if (data != null && data.getClassName().equalsIgnoreCase("fisherman")) {
                int level = data.getLevel();
                double xpMultiplier = 1.25 + (level - 1) * 0.25;
                xpToAdd = baseXP * xpMultiplier;

                // Ekstra drop oranı
                if (event.getCaught() instanceof Item) {
                    Item caughtItem = (Item) event.getCaught();
                    ItemStack itemStack = caughtItem.getItemStack();
                    double dropMultiplier = 1.0 + (level - 1) * 0.10;
                    itemStack.setAmount((int) Math.ceil(itemStack.getAmount() * dropMultiplier));
                    caughtItem.setItemStack(itemStack);
                }

                // Hazine şansı
                double treasureChance = Math.min(0.10 + (level - 1) * 0.01, 0.50);
                if (Math.random() < treasureChance) {
                    player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(Material.ENCHANTED_BOOK, 1));
                    player.sendMessage(ChatColor.GOLD + "Şansını kullandın! Ekstra hazine buldun!");
                }
            }

            playerManager.addXp(player, xpToAdd);
        }
    }
}