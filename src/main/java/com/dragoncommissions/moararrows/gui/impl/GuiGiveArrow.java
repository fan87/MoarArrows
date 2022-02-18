package com.dragoncommissions.moararrows.gui.impl;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.arrows.ArrowsManager;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import com.dragoncommissions.moararrows.gui.ButtonHandler;
import com.dragoncommissions.moararrows.gui.Gui;
import com.dragoncommissions.moararrows.gui.GuiItem;
import com.dragoncommissions.moararrows.gui.GuiList;
import com.dragoncommissions.moararrows.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class GuiGiveArrow extends GuiList {
    public GuiGiveArrow(int currentPage) {
        super("Arrows", currentPage, new ArrayList<>());
    }

    @Override
    public void init() {
        for (CustomArrow customArrow : ArrowsManager.getRegisteredArrows().values()) {
            getContents().add(new GuiItem(new ItemStackBuilder(customArrow.newItemStack())
                    .addLore("")
                    .addLore(ChatColor.GRAY + "Namespace: " + customArrow.getNamespace())
                    .addLore("")
                    .addLore(ChatColor.YELLOW + "Click to spawn!")
                    .addLore(ChatColor.DARK_GRAY + "Shift-Click to spawn a stack")
                    .addLore(ChatColor.DARK_GRAY + "Right-Click to spawn half a stack")
                    .build(), event -> {
                        int amount = 1;
                        if (event.getClick().isRightClick()) amount = 32;
                        if (event.getClick().isShiftClick()) amount = 64;
                        event.getWhoClicked().getInventory().addItem(new ItemStackBuilder(customArrow.newItemStack()).setAmount(amount).build());
                        event.getWhoClicked().sendMessage(ChatColor.GREEN + "You got " + customArrow.getDisplayName() + ChatColor.DARK_GRAY + " x" + amount + ChatColor.GREEN + "!");
                        Player player = (Player) event.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 4.048f, 8f);
            }));
        }
        fillBorder(new GuiItem(new ItemStackBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(" ").build()));
        set(5, 6, new GuiItem(new ItemStackBuilder(Material.BARRIER)
                .setDisplayName(ChatColor.RED + "Close")
                .build(), event -> {
            event.getWhoClicked().closeInventory();
        }));
        putItems();
    }

    @Override
    public void goPage(int page) {

    }
}
