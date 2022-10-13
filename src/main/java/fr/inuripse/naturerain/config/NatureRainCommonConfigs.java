package fr.inuripse.naturerain.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class NatureRainCommonConfigs {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> ZirmsSwordPowerDamage;
    public static final ForgeConfigSpec.ConfigValue<Boolean> ZirmsPickaxePowerInNether;
    public static final ForgeConfigSpec.ConfigValue<Integer> WetLeafDamageWithLauncher;
    public static final ForgeConfigSpec.ConfigValue<Integer> SoftenedSlimeballDamageWithLauncher;
    public static final ForgeConfigSpec.ConfigValue<Integer> SoftenedHoneycombDamageWithLauncher;
    public static final ForgeConfigSpec.ConfigValue<Integer> FlowingGlowInkDamageWithLauncher;

    public static final ForgeConfigSpec.ConfigValue<Integer> ZirmsVaineSize;
    public static final ForgeConfigSpec.ConfigValue<Integer> ZirmsVaineFrenquency;

    public static final ForgeConfigSpec.ConfigValue<Boolean> AllowRainRitual;

    public static final ForgeConfigSpec.ConfigValue<Boolean> AllowPersonnalSnailHouse;

    static {
        BUILDER.push("Config for Nature and Rain");

        ZirmsSwordPowerDamage = BUILDER.comment("Zirms sword power damage (Half hearts)")
                .defineInRange("Zirms Sword Power", 8, 1, 255);

        ZirmsPickaxePowerInNether = BUILDER.comment("Zirms pickaxe power allow in the nether")
                .define("Zirms Pickaxe Power in Nether", true);

        WetLeafDamageWithLauncher = BUILDER.comment("Damage deal by Wet Leaf as a projectile (Half hearts)")
                .defineInRange("Wet Leaf", 8, 1, 255);

        SoftenedSlimeballDamageWithLauncher = BUILDER.comment("Damage deal by Softened Slimeball as a projectile (Half hearts)")
                .defineInRange("Softened Slimeball", 16, 1, 255);

        SoftenedHoneycombDamageWithLauncher = BUILDER.comment("Damage deal by Softened Honeycomb as a projectile (Half hearts)")
                .defineInRange("Softened Honeycomb", 14, 1, 255);

        FlowingGlowInkDamageWithLauncher = BUILDER.comment("Damage deal by Flowing Glow Ink as a projectile (Half hearts)")
                .defineInRange("Flowing Glow Ink", 4, 1, 255);

        ZirmsVaineSize = BUILDER.comment("Max vain size for Zirms ore, I suggest to put 3 at the minimum (0 = not generate)")
                .defineInRange("Vain size", 4, 1, 63);

        ZirmsVaineFrenquency = BUILDER.comment("Max number of vain of Zirms ore in a chunk, I suggest to put 2 at the minimum (0 = not generate)")
                .defineInRange("Vain frequency", 4, 1, 63);

        AllowRainRitual = BUILDER.comment("Allow player to summon rain with a ritual")
                .define("Rain Ritual", true);

        AllowPersonnalSnailHouse = BUILDER.comment("Allow player to teleport to his house in his shell")
                .define("Shell House", true);


        BUILDER.pop();
        SPEC = BUILDER.build();
    }

}
