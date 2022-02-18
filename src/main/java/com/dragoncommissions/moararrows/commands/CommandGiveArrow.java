package com.dragoncommissions.moararrows.commands;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.ArrowsManager;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import com.dragoncommissions.moararrows.gui.impl.GuiGiveArrow;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandGiveArrow implements TabCompleter, CommandExecutor {

    private final Command command;
    private final MoarArrows plugin;

    public CommandGiveArrow(MoarArrows plugin, Command command) {
        this.plugin = plugin;
        this.command = command;

        this.command.setUsage(ChatColor.RED + "Command Usage: " + this.command.getUsage());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
            return true;
        }
        if (args.length == 0) {
            new GuiGiveArrow(1).open(((Player) sender));
            return true;
        }
        Player player = (Player) sender;
        CustomArrow customArrow = ArrowsManager.getCustomArrow(NameSpace.fromString(args[0]));
        int amount = 1;
        try {
            if (args.length > 1) {
                amount = Integer.parseInt(args[1]);
            }
        } catch (Exception ignored) {
            return false;
        }
        if (customArrow == null) return false;
        ItemStack itemStack = customArrow.newItemStack();
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
        sender.sendMessage(ChatColor.GREEN + "You got " + customArrow.getDisplayName() + ChatColor.DARK_GRAY + " x" + amount + ChatColor.GREEN + "!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> out = new ArrayList<>();
        for (CustomArrow value : ArrowsManager.getRegisteredArrows().values()) {
            if (value.getNamespace().toString().startsWith(args[0])) {
                out.add(value.getNamespace()+"");
            }
        }
        return out;
    }
}
