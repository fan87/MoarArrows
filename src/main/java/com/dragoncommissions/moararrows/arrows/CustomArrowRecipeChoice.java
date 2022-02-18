package com.dragoncommissions.moararrows.arrows;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class CustomArrowRecipeChoice extends RecipeChoice.ExactChoice {

    @Getter
    private final CustomArrow customArrow;

    public CustomArrowRecipeChoice(CustomArrow customArrow) {
        super(customArrow.newItemStack());
        this.customArrow = customArrow;
    }

    @Override
    public ItemStack getItemStack() {
        return customArrow.newItemStack();
    }

    @Override
    public ExactChoice clone() {
        return new CustomArrowRecipeChoice(customArrow);
    }

    @Override
    public boolean test(ItemStack itemStack) {
        return customArrow.is(itemStack);
    }
}
