package com.rs.basicMMO;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TakeXpCommandExecutor implements CommandExecutor {
    private BasicMMO plugin;

    public TakeXpCommandExecutor(BasicMMO plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof Player))
        {
            sender.sendMessage("Sadece oyuncular kullanabilir.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if(handItem == null || handItem.getType() != Material.EMERALD)
        {
            player.sendMessage(ChatColor.RED + "Elinde zümrüt yok!");
            return true;
        }

        int amount = handItem.getAmount();
        if (amount >= 0){
            player.giveExp(amount*1000);
            handItem.setAmount(0);
        }else{
            player.getInventory().setItemInMainHand(null);
        }

        player.sendMessage(ChatColor.GREEN + "Zümrüt kullanıldı, gerçek XP kazandın!");


        return true;
    }

}
