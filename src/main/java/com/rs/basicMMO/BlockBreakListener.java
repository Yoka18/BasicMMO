package com.rs.basicMMO;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BlockBreakListener implements Listener {

    private final PlayerManager playerManager;
    private final int MAX_LEVEL = 10;

    public BlockBreakListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerManager.PlayerData data = playerManager.getPlayerData(player);

        if (data == null) {
            return; // Sınıfı yoksa işlem yapma
        }

        String playerClass = data.getClassName();
        Block block = event.getBlock();
        Material type = block.getType();
        double baseXP = 0.5;

        // Miner sınıfı yetenekleri
        if (playerClass.equalsIgnoreCase("miner")) {
            handleMinerAbilities(event, player, data, baseXP);
        }
        // Timberman sınıfı yetenekleri
        else if (playerClass.equalsIgnoreCase("timberman") && isAxe(player.getInventory().getItemInMainHand())) {
            handleTimbermanAbilities(event, player, data);
        }

        // Genel olarak tüm blok kırmalardan gelen XP
        playerManager.addXp(player, baseXP);
    }

    private void handleMinerAbilities(BlockBreakEvent event, Player player, PlayerManager.PlayerData data, double baseXP) {
        Material type = event.getBlock().getType();
        if (isOre(type)) {
            int level = data.getLevel();
            double dropMultiplier = 1.1 + (level - 1) * ((2.0 - 1.1) / (MAX_LEVEL - 1));

            event.setDropItems(false);
            Collection<ItemStack> drops = event.getBlock().getDrops(player.getInventory().getItemInMainHand());
            for (ItemStack drop : drops) {
                int newAmount = (int) Math.ceil(drop.getAmount() * dropMultiplier);
                drop.setAmount(newAmount);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
            }
        } else if (type == Material.STONE || type == Material.COBBLESTONE) {
            playerManager.addXp(player, baseXP * 0.25); // Ekstra XP
        }
    }

    private void handleTimbermanAbilities(BlockBreakEvent event, Player player, PlayerManager.PlayerData data) {
        if (isWoodLog(event.getBlock().getType())) {
            event.setDropItems(false);
            Collection<ItemStack> drops = event.getBlock().getDrops(player.getInventory().getItemInMainHand());
            int level = data.getLevel();
            double dropMultiplier = 1.1 + (level - 1) * ((2.0 - 1.1) / (MAX_LEVEL - 1));

            for (ItemStack drop : drops) {
                int newAmount = (int) Math.ceil(drop.getAmount() * dropMultiplier);
                drop.setAmount(newAmount);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
            }
        }
    }

    // Yardımcı metotlar
    private boolean isOre(Material m) { return m.toString().endsWith("_ORE"); }
    private boolean isWoodLog(Material m) { return m.toString().endsWith("_LOG") || m.toString().endsWith("_STEM"); }
    private boolean isAxe(ItemStack item) { return item != null && item.getType().toString().endsWith("_AXE"); }
}