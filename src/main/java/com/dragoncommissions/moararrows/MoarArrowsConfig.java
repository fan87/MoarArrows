package com.dragoncommissions.moararrows;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MoarArrowsConfig {

    @ConfigComment("Angle to spread")
    public static final float BUNDLE_OF_ARROWS_SPREAD_ANGLE = 45;
    @ConfigComment("How many arrows will be shot")
    public static final int BUNDLE_OF_ARROWS_SPREAD_AMOUNT = 5;

    public static final int DIAMOND_ARROW_DIAMOND_DROPS_MIN = 4;
    public static final int DIAMOND_ARROW_DIAMOND_DROPS_MAX = 7;
    public static final int DIAMOND_ARROW_GOLD_DROPS_MIN = 0;
    public static final int DIAMOND_ARROW_GOLD_DROPS_MAX = 6;
    public static final int DIAMOND_ARROW_ENDER_PEARL_DROPS_MIN = 2;
    public static final int DIAMOND_ARROW_ENDER_PEARL_DROPS_MAX = 3;

    @ConfigComment("3 is fast enough, it's around 60 BPS, which is super fast in minecraft")
    public static final double ENDER_CRYSTAL_ARROW_MULTIPLIER = 5;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ConfigComment {
        String value();
    }

}
