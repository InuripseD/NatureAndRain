package fr.inuripse.naturerain.world.feature;

import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {

    public static final Holder<PlacedFeature> ZIRMS_ORE_PLACED = PlacementUtils.register("zirms_ore_placed",
            ModConfiguredFeatures.ZIRMS_ORE,ModOrePlacements.commonOrePlacement(4,
                    HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(80))));

}
