package com.dragoncommissions.moararrows;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MoarArrowsConfig {

    @ConfigComment("Angle to spread")
    public static float BUNDLE_OF_ARROWS_SPREAD_ANGLE = 45;
    @ConfigComment("How many arrows will be shot")
    public static int BUNDLE_OF_ARROWS_SPREAD_AMOUNT = 5;

    @ConfigComment("Diamond Arrow - Min amount of diamond drop")
    public static int DIAMOND_ARROW_DIAMOND_DROPS_MIN = 4;
    @ConfigComment("Diamond Arrow - Max amount of diamond drop")
    public static int DIAMOND_ARROW_DIAMOND_DROPS_MAX = 7;
    @ConfigComment("Diamond Arrow - Min amount of gold drop")
    public static int DIAMOND_ARROW_GOLD_DROPS_MIN = 0;
    @ConfigComment("Diamond Arrow - Max amount of gold drop")
    public static int DIAMOND_ARROW_GOLD_DROPS_MAX = 6;
    @ConfigComment("Diamond Arrow - Min amount of ender pearl drop")
    public static int DIAMOND_ARROW_ENDER_PEARL_DROPS_MIN = 2;
    @ConfigComment("Diamond Arrow - Max amount of ender pearl drop")
    public static int DIAMOND_ARROW_ENDER_PEARL_DROPS_MAX = 3;

    @ConfigComment("3 is fast enough, it's around 60 BPS, which is super fast in minecraft")
    public static double ENDER_CRYSTAL_ARROW_MULTIPLIER = 5;

    @ConfigComment("How many times of damage should Infinity Arrow take?")
    public static double INFINITY_ARROW_DAMAGE_MULTIPLIER = 3;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ConfigComment {
        String value();
    }

}
