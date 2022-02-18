package com.dragoncommissions.moararrows.commands;

import com.dragoncommissions.moararrows.MoarArrows;
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
        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the config!");
        return true;
    }
}
