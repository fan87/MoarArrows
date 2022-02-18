package com.dragoncommissions.moararrows.config;

import com.dragoncommissions.moararrows.MoarArrows;
import com.dragoncommissions.moararrows.MoarArrowsConfig;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ConfigManager {

    @Getter
    private final MoarArrows plugin;

    public ConfigManager(MoarArrows plugin) {
        this.plugin = plugin;
        reloadConfig();
    }

    @SneakyThrows
    public void reloadConfig() {
        FileConfiguration config = plugin.getConfig();
        for (Field declaredField : MoarArrowsConfig.class.getDeclaredFields()) {
            if (       !Modifier.isStatic(declaredField.getModifiers())
                    || !Modifier.isPublic(declaredField.getModifiers())
                    ||  Modifier.isFinal(declaredField.getModifiers())
            ) {
                continue;
            }
            // The path of the config value
            String path = declaredField.getName().replace("_", "-").toLowerCase(Locale.ROOT);

            // Process Comments
            List<String> comments = new ArrayList<>();
            comments.add("");
            comments.add("==== " + path + " ====");
            MoarArrowsConfig.ConfigComment[] configComments = declaredField.getAnnotationsByType(MoarArrowsConfig.ConfigComment.class);
            if (configComments.length == 1) {
                comments.add("  " + configComments[0].value());
            }
            comments.add("  Default Value: " + declaredField.get(null));

            // Set / Get value
            if (!config.contains(path)) {
                config.set(path, declaredField.get(null));
            } else {
                if (declaredField.getType() == float.class) {
                    declaredField.setFloat(null, (float) config.getDouble(path));
                } else if (declaredField.getType() == double.class) {
                    declaredField.setDouble(null, config.getDouble(path));
                } else if (declaredField.getType() == String.class) {
                    declaredField.set(null, config.getString(path));
                } else if (declaredField.getType() == int.class) {
                    declaredField.setInt(null, config.getInt(path));
                } else if (declaredField.getType() == long.class) {
                    declaredField.setLong(null, config.getLong(path));
                } else {
                    declaredField.set(null, config.get(path));
                }
            }

            // Save comments
            config.setComments(path, comments);
        }
        plugin.saveConfig();
    }

}
