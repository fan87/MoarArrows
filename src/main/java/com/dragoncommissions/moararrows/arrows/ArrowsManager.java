package com.dragoncommissions.moararrows.arrows;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.MoarArrowsConfig;
import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.impl.*;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ArrowsManager implements Listener {

    @Getter
    private static final Map<NameSpace, CustomArrow> registeredArrows = new HashMap<>();

    private static final Map<Arrow, CustomArrow> specialArrows = new HashMap<>();

    public static final BundleOfArrows BUNDLE_OF_ARROWS = new BundleOfArrows();
    public static final DiamondArrow DIAMOND_ARROW = new DiamondArrow();
    public static final FishArrow FISH_ARROW = new FishArrow();
    public static final EndCrystalArrow END_CRYSTAL_ARROW = new EndCrystalArrow();
    public static InfinityArrow INFINITY_ARROW;

    static {
        for (Field field : ArrowsManager.class.getDeclaredFields()) {
            if (    Modifier.isStatic(field.getModifiers())
                    && Modifier.isPublic(field.getModifiers())
                    && CustomArrow.class.isAssignableFrom(field.getType())
            ) {
                try {
                    CustomArrow customArrow = (CustomArrow) field.get(null);
                    if (customArrow == null) continue;
                    registerCustomArrow(customArrow);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Failed to register arrow: " + field.getClass().getSimpleName());
                }
            }
        }
    }


    public static CustomArrow getCustomArrow(NameSpace namespace) {
        return registeredArrows.get(namespace);
    }

    public static void registerCustomArrow(CustomArrow arrow) {
        arrow.getConfig();
        registeredArrows.put(arrow.getNamespace(), arrow);
        MoarArrows.registerListener(arrow);
    }

    public static CustomArrow getCustomArrowOfItem(ItemStack itemStack) {
        for (CustomArrow value : registeredArrows.values()) {
            if (value.is(itemStack)) return value;
        }
        return null;
    }

    @Getter
    private final MoarArrows plugin;

    @SneakyThrows
    public ArrowsManager(MoarArrows plugin) {
        this.plugin = plugin;
        INFINITY_ARROW = new InfinityArrow(plugin);
        registerCustomArrow(INFINITY_ARROW);
        MoarArrows.registerListener(this);
    }

    public void onEnable() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Arrow arrow : new HashSet<>(specialArrows.keySet())) {
                if (!arrow.isValid()) {
                    CustomArrow remove = specialArrows.remove(arrow);
                    continue;
                }
                CustomArrow customArrow = specialArrows.get(arrow);
                customArrow.spawnParticle(arrow);
                customArrow.onTick(arrow);
            }
        }, 0, 0);
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow)) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;
        CustomArrow customArrow = specialArrows.get((Arrow) event.getDamager());
        if (customArrow == null) return;
        customArrow.onEntityShot(event);
    }

    @EventHandler
    public void onArrowShot(EntityShootBowEvent event) {
        ItemStack arrow = event.getConsumable();
        CustomArrow customArrow = getCustomArrowOfItem(arrow);
        if (customArrow == null || !(event.getProjectile() instanceof Arrow)) {
            return;
        }
        specialArrows.put((Arrow) event.getProjectile(), customArrow);
        customArrow.onSpawn(event.getProjectile());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDie(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) return;
        if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;
        EntityDamageByEntityEvent lastDamageCause = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
        if (!(lastDamageCause.getDamager() instanceof Arrow)) return;
        CustomArrow customArrow = specialArrows.get((Arrow) lastDamageCause.getDamager());
        if (customArrow == null) return;
        customArrow.onKill(((Arrow) lastDamageCause.getDamager()), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(PlayerPickupArrowEvent event) {
        if (!(event.getArrow() instanceof Arrow)) return;
        CustomArrow customArrow = specialArrows.get((Arrow) event.getArrow());
        if (customArrow == null) return;
        if (MoarArrowsConfig.ARROW_PICKUP) {
            event.getItem().setItemStack(customArrow.newItemStack());
        } else {
            event.getArrow().setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent event){
        if(event.getItem().getType().equals(Material.ARROW)){
            for (CustomArrow customArrow : registeredArrows.values()) {
                if (customArrow.is(event.getItem())) {
                    event.setCancelled(true);
                    return;
                }
            }

        }
    }


}
