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

public class XPBlockListener implements Listener {
    private XPManager xpManager;
    private BasicMMO plugin;
    private final int MAX_LEVEL = 10;

    public XPBlockListener(BasicMMO plugin) {
        this.plugin = plugin;
        this.xpManager = new XPManager(plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        double baseXP = 0.5;
        double xpToAdd = baseXP;

        Block block = event.getBlock();
        Material type = block.getType();

        FileConfiguration config = plugin.getConfig();
        String playerClass = config.getString("players." + player.getUniqueId() + ".class");

        if (playerClass != null && playerClass.equalsIgnoreCase("miner")) {
            // Eğer kırılan blok ore ise, Fortune benzeri seviye bazlı ekstra drop uygula
            if (isOre(type)) {
                int level = config.getInt("players." + player.getUniqueId() + ".level", 1);
                // Multiplier formülü: seviye 1'de 1.1, seviye 10'da 2.0 olacak şekilde lineer artış
                double dropMultiplier = 1.1 + (level - 1) * ((2.0 - 1.1) / (MAX_LEVEL - 1));
                event.setDropItems(false);
                Collection<ItemStack> drops = block.getDrops(player.getInventory().getItemInMainHand());
                for (ItemStack drop : drops) {
                    int originalAmount = drop.getAmount();
                    int newAmount = (int) Math.ceil(originalAmount * dropMultiplier);
                    drop.setAmount(newAmount);
                    block.getWorld().dropItemNaturally(block.getLocation(), drop);
                }
            }
            // Eğer kırılan blok taş grubuna aitse, XP bonusu uygula
            else if (type == Material.STONE || type == Material.COBBLESTONE ||
                    type == Material.DIORITE || type == Material.ANDESITE || type == Material.GRANITE) {
                xpToAdd = baseXP * 1.25;
            }
        }

        xpManager.addXP(player, xpToAdd);
    }

    // Ore bloklarını kontrol eden yardımcı metod
    private boolean isOre(Material material) {
        switch(material) {
            case COAL_ORE:
            case DIAMOND_ORE:
            case EMERALD_ORE:
            case REDSTONE_ORE:
            case LAPIS_ORE:
            case IRON_ORE:
            case GOLD_ORE:
            case NETHER_QUARTZ_ORE:
            case NETHER_GOLD_ORE:
                return true;
            default:
                return false;
        }
    }
}
