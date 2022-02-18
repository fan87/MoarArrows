package com.dragoncommissions.moararrows.arrows;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class ArrowRecipeChoice extends RecipeChoice.ExactChoice {

    public ArrowRecipeChoice() {
        super(new ItemStack(Material.ARROW));
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemStack(Material.ARROW);
    }

    @Override
    public RecipeChoice.ExactChoice clone() {
        return new ArrowRecipeChoice();
    }

    @Override
    public boolean test(ItemStack itemStack) {
        if (itemStack.getType() != Material.ARROW) return false;
        for (CustomArrow customArrow : ArrowsManager.getRegisteredArrows().values()) {
            if (customArrow.is(itemStack)) return false;
        }
        return true;
    }


}
