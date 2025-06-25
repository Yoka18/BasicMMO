package com.rs.basicMMO;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.rs.basicMMO.SkillGUI;

public class SkillGUICommandExecutor implements CommandExecutor {

    private BasicMMO plugin;
    public SkillGUICommandExecutor(BasicMMO plugin) {
        this.plugin = plugin;
    }

    private final SkillGUI skillGUI = new SkillGUI(plugin);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("skills")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This command can only be used by players.");
                return true;
            }
            Player player = (Player) sender;
            skillGUI.open(player);
            return true;
        }
        return false;
    }
}