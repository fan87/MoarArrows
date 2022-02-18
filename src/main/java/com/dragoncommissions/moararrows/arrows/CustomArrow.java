package com.dragoncommissions.moararrows.arrows;

import com.dragoncommissions.moararrows.addons.NameSpace;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.xenondevs.particle.ParticleEffect;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CustomArrow implements Listener {

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

    private YamlConfiguration config;

    @SneakyThrows
    private File getConfigFileLocation() {
        File dir = new File("plugins/MoarArrows/arrows"); // Bro why can't I use JavaPlugin.getPlugin() while it's being initialized
        if (!dir.exists()) dir.mkdirs();
        File configFile = new File(dir, getNamespace().toString().replace(NameSpace.getMidString(), ".") + ".yml");
        if (!configFile.exists()) {
            configFile.createNewFile();
        }
        return configFile;
    }

    @SneakyThrows
    public YamlConfiguration getConfig() {
        if (config == null) {
            config = YamlConfiguration.loadConfiguration(getConfigFileLocation());
            reloadConfig();
        }
        return config;
    }

    protected void initConfig(YamlConfiguration config) {

    }

    @SneakyThrows
    public void saveConfig() {
        getConfig().save(getConfigFileLocation());
    }

    public void reloadConfig() {
        if (!getConfig().contains("particle.name")) {
            getConfig().set("particle.name", "NONE");
            getConfig().setComments("particle.name", Arrays.asList("", "Particle Name", "  For particle list, please read the plugin document."));
        }
        if (!getConfig().contains("particle.delay")) {
            getConfig().set("particle.delay", 3);
            getConfig().setComments("particle.delay", Arrays.asList("", "Particle Delay", "  Delay between each particle, unit: Tick (1/20 second). Must be integer."));
        }
        if (!getConfig().contains("particle.not-landed")) {
            getConfig().set("particle.not-landed", true);
            getConfig().setComments("particle.not-landed", Arrays.asList("", "Not Landed", "  Only show particle if arrow has not landed yet"));
        }

        String particleName = getConfig().getString("particle.name");
        List<String> comments = new ArrayList<>(getConfig().getComments("particle.name"));
        try {
            if (!particleName.equalsIgnoreCase("NONE")) {
                particleEffect = ParticleEffect.valueOf(particleName);
            }
            comments.removeIf(comment -> comment.contains("⚠️ Illegal Particle Name: "));
            getConfig().setComments("particle.name", comments);
            saveConfig();
        } catch (Exception ignored) {
            comments.removeIf(comment -> comment.contains("⚠️ Illegal Particle Name: "));
            comments.add("⚠️ Illegal Particle Name: " + getConfig().get("particle.name"));
            getConfig().setComments("particle.name", comments);
            saveConfig();
        }


        initConfig(getConfig());
        saveConfig();
    }

    private ParticleEffect particleEffect = null;

    public void spawnParticle(Entity arrowEntity) {
        if (particleEffect == null) return;
        if (getConfig().getBoolean("particle.not-landed")) {
            if (arrowEntity.isOnGround()) return;
        }
        if (arrowEntity.getTicksLived() % Math.max(getConfig().getInt("particle.delay") + 1, 1) == 0) {
            particleEffect.display(arrowEntity.getLocation());
        }
    }

    public boolean is(ItemStack itemStack) {
        try {
            return itemStack.getType() == Material.ARROW && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == getCustomModelData();
        } catch (NullPointerException ignored) {
            return false;
        }
    }

}
