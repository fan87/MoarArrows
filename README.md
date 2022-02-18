
![banner](https://storage.gato.host/61068f9c11c02e002297ebf2/3y8JQYgn2.png)
A plugin that adds some cool arrows to your game.
- Version: 1.18 (Or above)
- License: *PLACEHOLDER LICENSE*

## Commands
- `/moararrows-reload` - Reload the config file
- `/givearrow [Arrow Name] [Amount]` - Gives you arrows. Opens the Gui if there's no argument.

## Features
### Arrows
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/sNwX03NND.png)
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/LKfkL6cKV.png)
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/8BOEWlvfH.png)
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/d17tlwxBn.png)
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/x85rnXBqW.png)

### Gui
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/90kD3n9BA.png)

### Configurable
> Configurable Values:
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/Az4__sXl7.png)


> Configurable Particles:
![img](https://storage.gato.host/61068f9c11c02e002297ebf2/LCTW73pBR.png)



### Addon API
```java
ArrowsManager.registerCustomArrow(new DiamondArrow());
// Register an arrow type
// Do this in onEnable() method


public class DiamondArrow extends CustomArrow {

    public DiamondArrow() {
        // Registers the recipe
        // Note that only use new ArrowRecipeChoice() insteadof new MaterialChoice(Material.ARROW) 
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey("moararrows", "diamond_arrow"), new ItemStackBuilder(newItemStack()).setAmount(1).build())
                .shape("ddd", "dad", "ddd")
                .setIngredient('d', new RecipeChoice.MaterialChoice(Material.DIAMOND))
                .setIngredient('a', new CustomArrowRecipeChoice(ArrowsManager.BUNDLE_OF_ARROWS))
        );
    }

    @Override
    public List<String> getLore() {
        // You can use LoreUtils to let it line wrap for you
        return LoreUtils.splitLoreForLine(ChatColor.GRAY + "Killed mobs will drop ender pearl, diamonds and gold.");
    }

    @Override
    public String getDisplayName() {
        return ChatColor.AQUA + "Diamond Arrow";
    }

    @Override
    public NameSpace getNamespace() {
        // To let the user know where's the item from, you must provide addon name.
        // Default addon name is `built-in`, which shouldn't be used by addons
        // The namespace (In this case: diamond_arrow) can be duplicated as long as addon name isn't the same
        return new NameSpace("mycooladdon", "diamond_arrow");
    }

    @Override
    public int getCustomModelData() {
        // The custom model data.
        // If you need to save more information into ItemStack, override the newItemStack() method
        return 200;
    }

    // What will happen when an entity was killed with this arrow
    @Override
    public void onKill(Arrow arrowEntity, EntityDeathEvent event) {
        event.getDrops().add(new ItemStack(Material.GOLD_INGOT, new Random().nextInt(DIAMOND_ARROW_GOLD_DROPS_MAX - DIAMOND_ARROW_GOLD_DROPS_MIN) + DIAMOND_ARROW_GOLD_DROPS_MIN));
        event.getDrops().add(new ItemStack(Material.DIAMOND, new Random().nextInt(DIAMOND_ARROW_DIAMOND_DROPS_MAX - DIAMOND_ARROW_DIAMOND_DROPS_MIN) + DIAMOND_ARROW_DIAMOND_DROPS_MIN));
        event.getDrops().add(new ItemStack(Material.ENDER_PEARL, new Random().nextInt(DIAMOND_ARROW_ENDER_PEARL_DROPS_MAX - DIAMOND_ARROW_ENDER_PEARL_DROPS_MIN) + DIAMOND_ARROW_ENDER_PEARL_DROPS_MIN));
    }
}
// You can also use `getConfig()` method to get the arrow config
// Each arrow has its own config
// To tell the plugin how to initialize the config, overwrite `initConfig()` method
// We are not serializing/deserializing config object for this.
```

-----

### Developing
We are using maven, to build this project:
```shell
mvn clean package
```
You can also hotswap without an issue.

### Particle List
```
ASH
BARRIER
BLOCK_CRACK
BLOCK_DUST
BUBBLE_COLUMN_UP
BLOCK_MARKER
BUBBLE_POP
CAMPFIRE_COSY_SMOKE
CAMPFIRE_SIGNAL_SMOKE
CLOUD
COMPOSTER
CRIMSON_SPORE
CRIT
CRIT_MAGIC
CURRENT_DOWN
DAMAGE_INDICATOR
DOLPHIN
DRAGON_BREATH
DRIP_LAVA
DRIP_WATER
DRIPPING_DRIPSTONE_LAVA
DRIPPING_DRIPSTONE_WATER
DRIPPING_HONEY
DRIPPING_OBSIDIAN_TEAR
DUST_COLOR_TRANSITION
ELECTRIC_SPARK
ENCHANTMENT_TABLE
END_ROD
EXPLOSION_HUGE
EXPLOSION_LARGE
EXPLOSION_NORMAL
FALLING_DRIPSTONE_LAVA
FALLING_DRIPSTONE_WATER
FALLING_DUST
FALLING_HONEY
FALLING_NECTAR
FALLING_OBSIDIAN_TEAR
FALLING_SPORE_BLOSSOM
FIREWORKS_SPARK
FLAME
FLASH
FOOTSTEP
GLOW
GLOW_SQUID_INK
HEART
ITEM_CRACK
LANDING_HONEY
LANDING_OBSIDIAN_TEAR
LAVA
LIGHT
MOB_APPEARANCE
NAUTILUS
NOTE
PORTAL
REDSTONE
REVERSE_PORTAL
SCRAPE
SLIME
SMOKE_LARGE
SMOKE_NORMAL
SNEEZE
SNOWBALL
SNOWFLAKE
SNOW_SHOVEL
SOUL
SOUL_FIRE_FLAME
SPELL
SPELL_INSTANT
SPELL_MOB
SPELL_MOB_AMBIENT
SPELL_WITCH
SPIT
SPORE_BLOSSOM_AIR
SQUID_INK
SUSPENDED
SUSPENDED_DEPTH
SWEEP_ATTACK
TOTEM
TOWN_AURA
VIBRATION
VILLAGER_ANGRY
VILLAGER_HAPPY
WARPED_SPORE
WATER_BUBBLE
WATER_DROP
WATER_SPLASH
WATER_WAKE
WAX_OFF
WAX_ON
WHITE_ASH
```