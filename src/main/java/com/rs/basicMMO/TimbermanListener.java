package com.rs.basicMMO;

import java.util.Collection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TimbermanListener implements Listener {
    private BasicMMO plugin;
    private final int MAX_LEVEL = 10;

    public TimbermanListener(BasicMMO plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = plugin.getConfig();
        String playerClass = config.getString("players." + player.getUniqueId() + ".class");

        // Timberman sınıfı seçili ise ve oyuncunun elinde geçerli balta varsa
        if (playerClass != null && playerClass.equalsIgnoreCase("timberman")
                && isAxe(player.getInventory().getItemInMainHand())) {
            Block block = event.getBlock();
            Material type = block.getType();

            // Eğer kırılan blok ağaç odunu (log) ise
            if (isWoodLog(type)) {
                // Varsayılan dropları iptal ediyoruz
                event.setDropItems(false);
                Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());

                // Oyuncunun seviyesini config'den alıyoruz (varsayılan seviye 1)
                int level = config.getInt("players." + player.getUniqueId() + ".level", 1);
                // Multiplier: seviye 1'de 1.1, maksimum seviye (MAX_LEVEL) olduğunda 2.0 olacak şekilde lineer artış
                double dropMultiplier = 1.1 + (level - 1) * ((2.0 - 1.1) / (MAX_LEVEL - 1));

                // Her drop miktarını hesaplanan multiplier ile güncelliyoruz
                for (ItemStack drop : drops) {
                    int originalAmount = drop.getAmount();
                    int newAmount = (int) Math.ceil(originalAmount * dropMultiplier);
                    drop.setAmount(newAmount);
                    block.getWorld().dropItemNaturally(block.getLocation(), drop);
                }
            }
        }
    }

    // Hangi blokların ağaç odunu olduğunu kontrol eden yardımcı metod
    private boolean isWoodLog(Material material) {
        switch (material) {
            case OAK_LOG:
            case SPRUCE_LOG:
            case BIRCH_LOG:
            case JUNGLE_LOG:
            case ACACIA_LOG:
            case DARK_OAK_LOG:
            case CRIMSON_STEM:
            case WARPED_STEM:
                return true;
            default:
                return false;
        }
    }

    // Oyuncunun elindeki eşyayı kontrol ederek balta olup olmadığını tespit eder
    private boolean isAxe(ItemStack item) {
        if (item == null) return false;
        Material type = item.getType();
        switch (type) {
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                return true;
            default:
                return false;
        }
    }
}
