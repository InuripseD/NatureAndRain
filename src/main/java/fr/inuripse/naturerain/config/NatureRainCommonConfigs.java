package fr.inuripse.naturerain.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class NatureRainCommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> DURABILITY;

    static {
        BUILDER.push("Config for Nature and Rain");

        DURABILITY = BUILDER.comment("Durability of the launcher!").define("Durability", 842);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
