package com.dragoncommissions.moararrows.arrows.impl;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.MoarArrowsConfig;
import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.Arrows;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import com.dragoncommissions.moararrows.utils.ColorUtils;
import com.dragoncommissions.moararrows.utils.InventoryUtils;
import com.dragoncommissions.moararrows.utils.LoreUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashMap;
import java.util.List;

public class InfinityArrow extends CustomArrow {

    private final MoarArrows plugin;

    public InfinityArrow(MoarArrows plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getLore() {
        return LoreUtils.splitLoreForLine(ChatColor.GRAY + "Deals more damage than a regular arrow, and will return back to your inventory after 2 seconds.");
    }

    @Override
    public String getDisplayName() {
        return ColorUtils.generateRainbowText("Infinity Arrow", ColorUtils.RainbowStyle.RAINBOW);
    }

    @Override
    public NameSpace getNamespace() {
        return new NameSpace("infinity_arrow");
    }

    @Override
    public int getCustomModelData() {
        return 600;
    }

    @Override
    public void onSpawn(Entity arrowEntity) {
        arrowEntity.setGlowing(true);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (arrowEntity.isValid()) {
                ProjectileSource shooter = ((Arrow) arrowEntity).getShooter();
                if (shooter instanceof Player) {
                    InventoryUtils.giveItem(((Player) shooter), newItemStack());
                    arrowEntity.remove();
                    ((Player) shooter).sendMessage(getDisplayName() + ChatColor.GREEN + " has came back to you!");
                }
            }
        }, 40);
    }

    @Override
    public void onEntityShot(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage()* MoarArrowsConfig.INFINITY_ARROW_DAMAGE_MULTIPLIER);
        ProjectileSource shooter = ((Arrow) event.getDamager()).getShooter();
        if (shooter instanceof Player) {
            InventoryUtils.giveItem(((Player) shooter), newItemStack());
            event.getDamager().remove();
            ((Player) shooter).sendMessage(getDisplayName() + ChatColor.GREEN + " has came back to you!");
        }
    }
}