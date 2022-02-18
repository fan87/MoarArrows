package com.dragoncommissions.moararrows.arrows;

import com.dragoncommissions.moararrows.addons.NameSpace;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public abstract class CustomArrow {

    public abstract List<String> getLore();
    public abstract String getDisplayName();
    public abstract NameSpace getNamespace();
    public abstract int getCustomModelData();

    public ItemStack newItemStack() {
        ItemStack itemStack = new ItemStack(Material.ARROW);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(getCustomModelData());
        itemMeta.setDisplayName(getDisplayName());
        itemMeta.setLore(getLore());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void onTick(Arrow arrowEntity) {}
    public void onSpawn(Entity arrowEntity) {}
    public void onEntityShot(EntityDamageByEntityEvent event) {}
    public void onKill(Arrow arrowEntity, EntityDeathEvent event) {}

    public boolean is(ItemStack itemStack) {
        try {
            return itemStack.getType() == Material.ARROW && itemStack.getItemMeta().getCustomModelData() == getCustomModelData();
        } catch (NullPointerException ignored) {
            return false;
        }
    }

}
