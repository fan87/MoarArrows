package com.dragoncommissions.moararrows.commands;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.arrows.ArrowsManager;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReloadConfig implements CommandExecutor {

    private final MoarArrows plugin;

    public CommandReloadConfig(MoarArrows plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.reloadConfig();
        plugin.getConfigManager().reloadConfig();
        for (CustomArrow customArrow : ArrowsManager.getRegisteredArrows().values()) {
            customArrow.reloadConfig();
        }
        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the config!");
        return true;
    }
}
