package com.dragoncommissions.moararrows.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class InventoryUtils {

    /**
     * Give item to the player, and drop the item if inventory is full
     * @param player The player
     * @param items Items to add
     */
    public static void giveItem(Player player, ItemStack... items) {
        for (ItemStack value : player.getInventory().addItem(Arrays.stream(items).filter(Objects::nonNull).collect(Collectors.toList()).toArray(new ItemStack[0])).values()) {
            if (value == null) continue;
            player.getWorld().dropItem(player.getLocation(), value);
        }
    }

    public static boolean isInventoryFull(Inventory inventory) {
        return inventory.firstEmpty() == -1;
    }

}
