package com.rs.basicMMO;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SkillGUI  {
    private final String generalSkillTreeName = ChatColor.DARK_GREEN + "Skill Tree";
    private final int generalSkillTreeSize = 9;

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, generalSkillTreeSize, generalSkillTreeName);

        // Yetenekleri oluştur
        ItemStack classesItem = createSkillItem(Material.IRON_PICKAXE, "Classes", "Chose or look at your classes");
        ItemStack skillsItem = createSkillItem(Material.NETHER_STAR, "Skills", "Get new abilities");


        // GUI'ye yetenekleri yerleştir
        gui.setItem(0, classesItem);
        gui.setItem(5, skillsItem);


        player.openInventory(gui);
    }

    private ItemStack createSkillItem(Material material, String name, String... desc) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + name);
        meta.setLore(Arrays.asList(desc));
        item.setItemMeta(meta);
        return item;
    }

    // Sınıf ekranı için
    public void classGUI(Player player)
    {
        player.closeInventory();

        String classGUIName = ChatColor.DARK_GREEN + "Classes";
        int classGUISize = 27;

        Inventory gui = Bukkit.createInventory(null, classGUISize, classGUIName);

        ItemStack minerItem = createSkillItem(Material.DIAMOND_PICKAXE, "Miner", "Can multiplied ores");
        ItemStack fishermanItem = createSkillItem(Material.FISHING_ROD, "Fisherman", "Can multiplied fishes");
        ItemStack timbermanItem = createSkillItem(Material.BOW, "Timberman", "Can multiplied woods");

        gui.setItem(11, minerItem);
        gui.setItem(14, fishermanItem);
        gui.setItem(17, timbermanItem);

        player.openInventory(gui);
    }

    public void skillsGUI(Player player)
    {
        player.closeInventory();

        String skillGUIName = ChatColor.DARK_GREEN + "Skills";
        int skillGUISize = 27;

        Inventory gui = Bukkit.createInventory(null, skillGUISize, skillGUIName);


        for (int i = 0; i < skillGUISize; i++)
        {
            if(i % 2 == 0 || i == 0)
            {
                ItemStack skillItem = createSkillItem(Material.EMERALD, "Skill", "Get new skill");
                gui.setItem(i, skillItem);
            }
            else
            {
                ItemStack paneItem = createSkillItem(Material.RED_STAINED_GLASS_PANE, "", "");
                gui.setItem(i, paneItem);
            }
        }

        player.openInventory(gui);

    }





}