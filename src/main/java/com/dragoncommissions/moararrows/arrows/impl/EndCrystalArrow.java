package com.dragoncommissions.moararrows.arrows.impl;

import com.dragoncommissions.moararrows.MoarArrowsConfig;
import com.dragoncommissions.moararrows.addons.NameSpace;
import com.dragoncommissions.moararrows.arrows.ArrowsManager;
import com.dragoncommissions.moararrows.arrows.CustomArrow;
import com.dragoncommissions.moararrows.arrows.CustomArrowRecipeChoice;
import com.dragoncommissions.moararrows.utils.ItemStackBuilder;
import com.dragoncommissions.moararrows.utils.LoreUtils;
import org.bukkit.*;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.util.Vector;

import java.util.List;

public class EndCrystalArrow extends CustomArrow {

    public EndCrystalArrow() {
        Bukkit.addRecipe(new ShapelessRecipe(new NamespacedKey("moararrows", "end_crystal_arrow"), new ItemStackBuilder(newItemStack()).setAmount(1).build())
                .addIngredient(new CustomArrowRecipeChoice(ArrowsManager.FISH_ARROW))
                .addIngredient(new RecipeChoice.MaterialChoice(Material.END_CRYSTAL))
        );
    }

    @Override
    public List<String> getLore() {
        return LoreUtils.splitLoreForLine(ChatColor.GRAY + "Fast, laser like arrow. It goes really fast and will go straight.");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.LIGHT_PURPLE + "End Crystal Arrow";
    }

    @Override
    public NameSpace getNamespace() {
        return new NameSpace("end_crystal_arrow");
    }

    @Override
    public int getCustomModelData() {
        return 400;
    }

    @Override
    public void onSpawn(Entity arrowEntity) {
        arrowEntity.setGravity(false);
        arrowEntity.getLocation().setDirection(arrowEntity.getVelocity());
        arrowEntity.setVelocity(arrowEntity.getVelocity().multiply(MoarArrowsConfig.ENDER_CRYSTAL_ARROW_MULTIPLIER));
    }

    @Override
    public void onTick(Arrow arrowEntity) {
        if (arrowEntity.getTicksLived() > 1) {
            Vector direction = arrowEntity.getLocation().getDirection();
            arrowEntity.setVelocity(new Vector(
                    -direction.getX() * MoarArrowsConfig.ENDER_CRYSTAL_ARROW_MULTIPLIER,
                    -direction.getY() * MoarArrowsConfig.ENDER_CRYSTAL_ARROW_MULTIPLIER,
                    direction.getZ() * MoarArrowsConfig.ENDER_CRYSTAL_ARROW_MULTIPLIER));
        }
    }
}
