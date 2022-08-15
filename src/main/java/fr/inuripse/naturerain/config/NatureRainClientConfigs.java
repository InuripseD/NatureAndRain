package fr.inuripse.naturerain.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class NatureRainClientConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Config for Nature and Rain");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
