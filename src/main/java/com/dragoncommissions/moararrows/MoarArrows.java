package com.dragoncommissions.moararrows;

import com.dragoncommissions.moararrows.arrows.ArrowsManager;
import com.dragoncommissions.moararrows.commands.CommandGiveArrow;
import com.dragoncommissions.moararrows.commands.CommandReloadConfig;
import com.dragoncommissions.moararrows.config.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class MoarArrows extends JavaPlugin {

    public final ArrowsManager arrows = new ArrowsManager(this);
    private static final List<Listener> listenerRegistrationQueue = new ArrayList<>();

    public static final Random random = new Random();

    @Getter
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        listenerRegistrationQueue.forEach((listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, this)));
        listenerRegistrationQueue.clear();

        PluginCommand giveArrowCommand = getServer().getPluginCommand("givearrow");
        giveArrowCommand.setExecutor(new CommandGiveArrow(this, giveArrowCommand));

        getServer().getPluginCommand("moararrows-reload").setExecutor(new CommandReloadConfig(this));


        configManager = new ConfigManager(this);

        arrows.onEnable();

    }

    @Override
    public void onDisable() {

    }

    public static void registerListener(Listener listener) {
        // Bruh why bukkit api requires plugin as input : |
        if (listenerRegistrationQueue.contains(listener)) return;
        try {
            Bukkit.getServer().getPluginManager().registerEvents(listener, MoarArrows.getPlugin(MoarArrows.class));
        } catch (Exception ignored) {
            listenerRegistrationQueue.add(listener);
        }
    }

    public static void unregisterListener(Listener listener) {
        listenerRegistrationQueue.remove(listener);
        try {
            HandlerList.unregisterAll(listener);
        } catch (Exception ignored) {}
    }

}
