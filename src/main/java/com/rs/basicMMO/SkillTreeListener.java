package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class SkillTreeListener implements Listener{
    private BasicMMO plugin;
    public SkillTreeListener(BasicMMO plugin)
    {
        this.plugin = plugin;
    }
    SkillGUI skillGUI = new SkillGUI(plugin);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory clickedInventory = event.getClickedInventory();
        String guiName = ChatColor.DARK_GREEN + "Skill Tree";


        if (clickedInventory != null && event.getView().getTitle().equals(guiName)) {
            event.setCancelled(true);

            if (clickedItem != null) {
                if (clickedItem.getType() == Material.IRON_PICKAXE &&
                        clickedItem.getItemMeta() != null &&
                        clickedItem.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Classes")) {


                    skillGUI.classGUI(player);

                } else if(clickedItem.getType() == Material.NETHER_STAR &&
                        clickedItem.getItemMeta() != null &&
                        clickedItem.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Skills")){

                    skillGUI.skillsGUI(player);

                } else if(clickedItem.getType() == Material.CHEST &&
                        clickedItem.getItemMeta() != null &&
                        clickedItem.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "About")) {

                    skillGUI.aboutGUI(player, plugin);
                }
            }
        }
    }

}
