package com.rs.basicMMO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIManager {

    private final PlayerManager playerManager;

    // GUI İsimleri
    public static final String MAIN_MENU_NAME = ChatColor.DARK_GREEN + "Skill Tree";
    public static final String CLASSES_MENU_NAME = ChatColor.DARK_GREEN + "Classes";
    public static final String SKILLS_MENU_NAME = ChatColor.DARK_GREEN + "Skills";
    public static final String ABOUT_MENU_NAME = ChatColor.DARK_GREEN + "About";

    public GUIManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    // Ana menüyü açar
    public void openMainMenu(Player player) {
        Inventory gui = Bukkit.createInventory(player, 9, MAIN_MENU_NAME);

        gui.setItem(0, createItem(Material.IRON_PICKAXE, ChatColor.YELLOW + "Class", "Select or view your class."));
        gui.setItem(4, createItem(Material.NETHER_STAR, ChatColor.YELLOW + "Skill", "Gain new skills."));
        gui.setItem(8, createItem(Material.CHEST, ChatColor.YELLOW + "About", "Your character stats."));

        player.openInventory(gui);
    }

    // Sınıf seçme menüsünü açar
    public void openClassesGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, CLASSES_MENU_NAME);

        gui.setItem(11, createItem(Material.DIAMOND_PICKAXE, "Miner", "You win by multiplying the mines."));
        gui.setItem(13, createItem(Material.FISHING_ROD, "Fisherman", "You catch fish by folding them."));
        gui.setItem(15, createItem(Material.DIAMOND_AXE, "Timberman", "You break wood by folding it."));

        player.openInventory(gui);
    }

    // Yetenekler menüsünü açar (örnek)
    public void openSkillsGUI(Player player) {
        Inventory gui = Bukkit.createInventory(player, 27, SKILLS_MENU_NAME);
        // Burayı gelecekteki yeteneklerle doldurabilirsiniz.
        gui.setItem(13, createItem(Material.EMERALD, "improving", "It is purchased with skill points."));
        player.openInventory(gui);
    }

    // Hakkında menüsünü açar
    public void openAboutGUI(Player player) {
        PlayerManager.PlayerData data = playerManager.getPlayerData(player);
        if (data == null) {
            player.sendMessage(ChatColor.RED + "You have to choose a class first!");
            return;
        }

        Inventory gui = Bukkit.createInventory(player, 27, ABOUT_MENU_NAME);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Class: " + data.getClassName());
        lore.add(ChatColor.GRAY + "Level: " + data.getLevel());
        lore.add(ChatColor.GRAY + "XP: " + String.format("%.2f", data.getXp()));

        ItemStack statsItem = createItem(Material.PLAYER_HEAD, ChatColor.YELLOW + player.getName(), lore);

        gui.setItem(13, statsItem);
        player.openInventory(gui);
    }

    // GUI için ItemStack oluşturan yardımcı metot
    private ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    private ItemStack createItem(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}