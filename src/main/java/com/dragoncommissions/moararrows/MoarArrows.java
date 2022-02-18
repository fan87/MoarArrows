package com.dragoncommissions.moararrows;

import com.dragoncommissions.moararrows.arrows.Arrows;
import com.dragoncommissions.moararrows.commands.CommandGiveArrow;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public final class MoarArrows extends JavaPlugin {

    public final Arrows arrows = new Arrows(this);
    public static final Random random = new Random();

    @Override
    public void onEnable() {

        PluginCommand givearrow = getServer().getPluginCommand("givearrow");
        givearrow.setExecutor(new CommandGiveArrow(this, givearrow));

        getServer().getPluginManager().registerEvents(arrows, this);


        arrows.onEnable();
    }

    @Override
    public void onDisable() {

    }
}