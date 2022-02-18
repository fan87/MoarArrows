package com.dragoncommissions.moararrows.arrows.impl;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.ArrowRecipeChoice;
import com.dragoncommissions.moararrows.arrows.ArrowsManager;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import com.dragoncommissions.moararrows.arrows.CustomArrowRecipeChoice;
import com.dragoncommissions.moararrows.utils.ItemStackBuilder;
import com.dragoncommissions.moararrows.utils.LoreUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FishArrow extends CustomArrow {

    public FishArrow() {
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey("moararrows", "fish_arrow"), new ItemStackBuilder(newItemStack()).setAmount(1).build())
                .shape("scs", "cac", "scs")
                .setIngredient('a', new ArrowRecipeChoice())
                .setIngredient('c', new RecipeChoice.MaterialChoice(Material.COOKED_COD))
                .setIngredient('s', new RecipeChoice.MaterialChoice(Material.COOKED_SALMON))
        );
    }

    @Override
    public List<String> getLore() {
        return LoreUtils.splitLoreForLine(ChatColor.GRAY + "Turns your target into fish.");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.GOLD + "Fish Arrow";
    }

    @Override
    public NameSpace getNamespace() {
        return new NameSpace("fish_arrow");
    }

    @Override
    public int getCustomModelData() {
        return 500;
    }

    @Override
    public void onEntityShot(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType() == EntityType.PLAYER || ((LivingEntity) event.getEntity()).getHealth() > 40) return;
        event.getDamager().remove();
        event.getEntity().remove();
        Class<? extends LivingEntity>[] entitiesToSpawn = new Class[] {Cod.class, PufferFish.class, TropicalFish.class, Salmon.class};
        event.getEntity().getWorld().spawn(event.getEntity().getLocation(), entitiesToSpawn[MoarArrows.random.nextInt(entitiesToSpawn.length - 1)]);
    }

}
