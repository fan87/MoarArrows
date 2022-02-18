package com.dragoncommissions.moararrows.arrows.impl;

import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DiamondArrow extends CustomArrow {
    @Override
    public List<String> getLore() {
        return Arrays.asList(ChatColor.GRAY + "Killed mobs will drop ender pearl, diamonds and gold.");
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
        event.getDrops().add(new ItemStack(Material.GOLD_INGOT, new Random().nextInt(6)));
        event.getDrops().add(new ItemStack(Material.DIAMOND, new Random().nextInt(4) + 3));
        event.getDrops().add(new ItemStack(Material.ENDER_PEARL, new Random().nextInt(3)));
    }
}
