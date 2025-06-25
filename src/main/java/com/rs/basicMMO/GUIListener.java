package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    private final GUIManager guiManager;

    public GUIListener(GUIManager guiManager) {
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();

        // Sadece bizim GUI'lerimizdeki tıklamaları dikkate al
        if (title.equals(GUIManager.MAIN_MENU_NAME) || title.equals(GUIManager.CLASSES_MENU_NAME) ||
                title.equals(GUIManager.SKILLS_MENU_NAME) || title.equals(GUIManager.ABOUT_MENU_NAME)) {

            event.setCancelled(true); // Oyuncunun item'ı almasını engelle

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            // Ana Menü Tıklamaları
            if (title.equals(GUIManager.MAIN_MENU_NAME)) {
                handleMainMenuClick(player, clickedItem);
            }
            // Sınıflar Menüsü Tıklamaları
            else if (title.equals(GUIManager.CLASSES_MENU_NAME)) {
                handleClassesMenuClick(player, clickedItem);
            }
        }
    }

    private void handleMainMenuClick(Player player, ItemStack clickedItem) {
        String displayName = clickedItem.getItemMeta().getDisplayName();
        if (displayName.equals(ChatColor.YELLOW + "Class")) {
            guiManager.openClassesGUI(player);
        } else if (displayName.equals(ChatColor.YELLOW + "Skill")) {
            guiManager.openSkillsGUI(player);
        } else if (displayName.equals(ChatColor.YELLOW + "About")) {
            guiManager.openAboutGUI(player);
        }
    }

    private void handleClassesMenuClick(Player player, ItemStack clickedItem) {
        String displayName = clickedItem.getItemMeta().getDisplayName();
        String className = null;

        if (displayName.equals("Miner")) {
            className = "miner";
        } else if (displayName.equals("Fisherman")) {
            className = "fisherman";
        } else if (displayName.equals("Timberman")) {
            className = "timberman";
        }

        if (className != null) {
            // Sınıf seçimi için MMOCommandExecutor'daki komutu simüle ediyoruz.
            player.performCommand("mmo class " + className);
            player.closeInventory();
        }
    }
}