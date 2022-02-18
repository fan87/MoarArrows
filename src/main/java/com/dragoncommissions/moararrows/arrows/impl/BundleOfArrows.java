package com.dragoncommissions.moararrows.arrows.impl;

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

    private final static int ARROWS_COUNT = 5;
    private final static float SPREAD_ANGLE = 45.0f;

    @Override
    public List<String> getLore() {
        return Arrays.asList(ChatColor.DARK_GRAY + "Fires multiple arrows at the same time.");
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
        float angleDiff = (SPREAD_ANGLE/(ARROWS_COUNT - 1));
        for (int i = 0; i < ARROWS_COUNT; i++) {
            if (ARROWS_COUNT % 2 == 1 && i == ARROWS_COUNT/2) continue; // If it's the middle one
            float additionAngle = (-SPREAD_ANGLE/2f + angleDiff*i);
            ProjectileSource shooter = ((Arrow) arrowEntity).getShooter();
            Location location = arrowEntity.getLocation();
            if (shooter instanceof Player) {
                location.setYaw(((Player) shooter).getLocation().getYaw());
                location.setPitch(((Player) shooter).getLocation().getPitch());
            }
            Vector vector = getVector(location.getYaw(), location.getPitch(), additionAngle);
            arrowEntity.getWorld().spawnArrow(location, vector, ((float) arrowEntity.getVelocity().length()), 1f);
        }
        if (ARROWS_COUNT % 2 == 0) arrowEntity.remove();
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
