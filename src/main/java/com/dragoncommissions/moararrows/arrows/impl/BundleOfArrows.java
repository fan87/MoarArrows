package com.dragoncommissions.moararrows.arrows.impl;

import com.dragoncommissions.moararrows.MoarArrowsConfig;
import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class BundleOfArrows extends CustomArrow {


    @Override
    public List<String> getLore() {
        return Arrays.asList(ChatColor.GRAY + "Fires multiple arrows at the same time.");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.YELLOW +"Bundle of Arrows";
    }

    @Override
    public NameSpace getNamespace() {
        return new NameSpace("bundle_of_arrows");
    }

    @Override
    public int getCustomModelData() {
        return 100;
    }

    @Override
    public void onSpawn(Entity arrowEntity) {
        float angleDiff = (MoarArrowsConfig.BUNDLE_OF_ARROWS_SPREAD_ANGLE/(MoarArrowsConfig.BUNDLE_OF_ARROWS_SPREAD_AMOUNT - 1));
        for (int i = 0; i < MoarArrowsConfig.BUNDLE_OF_ARROWS_SPREAD_AMOUNT; i++) {
            if (MoarArrowsConfig.BUNDLE_OF_ARROWS_SPREAD_AMOUNT % 2 == 1 && i == MoarArrowsConfig.BUNDLE_OF_ARROWS_SPREAD_AMOUNT/2) continue; // If it's the middle one
            float additionAngle = (-MoarArrowsConfig.BUNDLE_OF_ARROWS_SPREAD_ANGLE/2f + angleDiff*i);
            ProjectileSource shooter = ((Arrow) arrowEntity).getShooter();
            Location location = arrowEntity.getLocation();
            location.setYaw(180 - location.getYaw());
            if (shooter instanceof Player) {
                location.setYaw(((Player) shooter).getLocation().getYaw());
                location.setPitch(((Player) shooter).getLocation().getPitch());
            }
            Vector vector = getVector(location.getYaw(), location.getPitch(), additionAngle);
            arrowEntity.getWorld().spawnArrow(location, vector, ((float) arrowEntity.getVelocity().length()), 1f);
        }
        if (MoarArrowsConfig.BUNDLE_OF_ARROWS_SPREAD_AMOUNT % 2 == 0) arrowEntity.remove();
    }


    private static Vector getVector(float iyaw, float ipitch, float rYaw) {
        iyaw -= 90;
        rYaw -= 90;
        double bx = -Math.cos(Math.toRadians(ipitch));
        double bz = Math.cos(Math.toRadians(rYaw));
        double by = -Math.sin(Math.toRadians(ipitch));
        double ry = Math.toRadians((float) (Math.toDegrees(Math.atan2(-bz, -bx)) + 90) + iyaw);
        double rp = Math.toRadians((float) Math.toDegrees(Math.atan(-by / Math.sqrt(bx*bx + bz*bz))));
        double centerZ = Math.cos(ry) * Math.cos(rp);
        double centerY = -Math.sin(rp);
        double centerX = -Math.sin(ry) * Math.cos(rp);
        return new Vector(centerX, centerY, centerZ);
    }


}
