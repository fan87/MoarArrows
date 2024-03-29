package com.dragoncommissions.moararrows.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemStackBuilder {

    private ItemStack itemStack;

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        itemStack.setAmount(1);
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public ItemStackBuilder(Material material, int data) {
        this.itemStack = new ItemStack(material, 1, (short) data);
        itemStack.setAmount(1);
    }



    public ItemStackBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemStackBuilder clearLore() {
        setLore(new ArrayList<>());
        return this;
    }

    public ItemStackBuilder setData(int durability) {
        itemStack.setDurability((short) durability);
        return this;
    }

    public ItemStackBuilder setMaterial(Material material) {
        itemStack.setType(material);
        return this;
    }

    public ItemStackBuilder addLore(List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore1 = itemMeta.getLore();
        if (lore1 == null) {
            lore1 = new ArrayList<>();
        }
        lore1.addAll(lore);
        itemMeta.setLore(lore1);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addLore(String lore, boolean splitLine) {
        if (splitLine)  return addLore(LoreUtils.splitLoreForLine(lore));
        return addLore(lore);
    }

    public ItemStackBuilder addLore(String... lore) {
        return addLore(Arrays.asList(lore));
    }

    public ItemStackBuilder removeLore(int index) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore1 = itemMeta.getLore();
        if (lore1 == null) {
            return this;
        }
        if (lore1.size() == 0) return this;
        while (index < 0) {
            index += lore1.size();
        }
        lore1.remove(index);
        itemMeta.setLore(lore1);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setDisplayName(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addItemFlags(ItemFlag... flags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(flags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addAllItemFlags() {
        return this.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .addItemFlags(ItemFlag.HIDE_ENCHANTS)
                .addItemFlags(ItemFlag.HIDE_DESTROYS)
                .addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                .addItemFlags(ItemFlag.HIDE_PLACED_ON)
                .addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
    }

    public ItemStackBuilder removeItemFlags(ItemFlag... flags) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.removeItemFlags(flags);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder addEnchantments(EnchantmentObject... enchantments) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (EnchantmentObject enchantment : enchantments) {
            itemMeta.addEnchant(enchantment.enchantment, enchantment.level, true);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder removeEnchantments(Enchantment... enchantments) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        for (Enchantment enchantment : enchantments) {
            itemMeta.removeEnchant(enchantment);
        }
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class EnchantmentObject {
        private int level;
        private Enchantment enchantment;
    }

}