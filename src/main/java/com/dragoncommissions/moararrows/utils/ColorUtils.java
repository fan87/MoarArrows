package com.dragoncommissions.moararrows.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;

public class ColorUtils {

    @Getter
    @AllArgsConstructor
    public static class RainbowStyle {
        public static final RainbowStyle CRITICAL = new RainbowStyle(new ChatColor[] {ChatColor.WHITE, ChatColor.WHITE, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.RED});
        public static final RainbowStyle RAINBOW = new RainbowStyle(new ChatColor[] {ChatColor.RED, ChatColor.GOLD, ChatColor.YELLOW, ChatColor.GREEN, ChatColor.BLUE, ChatColor.DARK_PURPLE});

        ChatColor[] colors;
    }

    /**
     * Generate a rainbow text with input text and rainbow style
     * @param input Original text
     * @param rainbowStyle The style you want to use, for example: {@link RainbowStyle#CRITICAL}
     * @return The output text with color
     */
    public static String generateRainbowText(String input, RainbowStyle rainbowStyle) {
        StringBuilder output = new StringBuilder();
        char[] chars = input.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            ChatColor color = rainbowStyle.colors[index % rainbowStyle.colors.length];
            output.append(color).append(chars[index]);
        }
        return output.toString();
    }

}