package com.dragoncommissions.moararrows.arrows;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.impl.BundleOfArrows;
import com.dragoncommissions.moararrows.arrows.impl.DiamondArrow;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Arrows implements Listener {

    @Getter
    private static final Map<NameSpace, CustomArrow> registeredArrows = new HashMap<>();

    private static final Map<Arrow, CustomArrow> specialArrows = new HashMap<>();

    public static final BundleOfArrows BUNDLE_OF_ARROWS = new BundleOfArrows();
    public static final DiamondArrow DIAMOND_ARROW = new DiamondArrow();

    static {
        for (Field field : Arrows.class.getDeclaredFields()) {
            if (    Modifier.isStatic(field.getModifiers())
                    && Modifier.isPublic(field.getModifiers())
                    && CustomArrow.class.isAssignableFrom(field.getType())
            ) {
                try {
                    CustomArrow customArrow = (CustomArrow) field.get(null);
                    registeredArrows.put(customArrow.getNamespace(), customArrow);
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
        registeredArrows.put(arrow.getNamespace(), arrow);
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
    public Arrows(MoarArrows plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Arrow arrow : new HashSet<>(specialArrows.keySet())) {
                if (!arrow.isValid()) {
                    CustomArrow remove = specialArrows.remove(arrow);
                    continue;
                }
                specialArrows.get(arrow).onTick(arrow);
            }
        }, 0, 0);
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
        CustomArrow customArrow = specialArrows.get(lastDamageCause.getDamager());
        if (customArrow == null) return;
        customArrow.onKill(((Arrow) lastDamageCause.getDamager()), event);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event) {

    }

}
