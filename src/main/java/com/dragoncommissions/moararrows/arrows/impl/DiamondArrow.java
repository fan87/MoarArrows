package com.dragoncommissions.moararrows.arrows.impl;

import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import com.dragoncommissions.moararrows.utils.LoreUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.dragoncommissions.moararrows.MoarArrowsConfig.*;

public class DiamondArrow extends CustomArrow {
    @Override
    public List<String> getLore() {
        return LoreUtils.splitLoreForLine(ChatColor.GRAY + "Killed mobs will drop ender pearl, diamonds and gold.");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.AQUA + "Diamond Arrow";
    }

    @Override
    public NameSpace getNamespace() {
        return new NameSpace("diamond_arrow");
    }

    @Override
    public int getCustomModelData() {
        return 200;
    }

    @Override
    public void onKill(Arrow arrowEntity, EntityDeathEvent event) {
        event.getDrops().add(new ItemStack(Material.GOLD_INGOT, new Random().nextInt(DIAMOND_ARROW_GOLD_DROPS_MAX - DIAMOND_ARROW_GOLD_DROPS_MIN) + DIAMOND_ARROW_GOLD_DROPS_MIN));
        event.getDrops().add(new ItemStack(Material.DIAMOND, new Random().nextInt(DIAMOND_ARROW_DIAMOND_DROPS_MAX - DIAMOND_ARROW_DIAMOND_DROPS_MIN) + DIAMOND_ARROW_DIAMOND_DROPS_MIN));
        event.getDrops().add(new ItemStack(Material.ENDER_PEARL, new Random().nextInt(DIAMOND_ARROW_ENDER_PEARL_DROPS_MAX - DIAMOND_ARROW_ENDER_PEARL_DROPS_MIN) + DIAMOND_ARROW_ENDER_PEARL_DROPS_MIN));
    }
}
