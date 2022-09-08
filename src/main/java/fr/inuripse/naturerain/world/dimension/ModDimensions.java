package fr.inuripse.naturerain.world.dimension;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {

    public static final ResourceKey<Level> SNAIL_HOUSE_KEY =
            ResourceKey.create(Registry.DIMENSION_REGISTRY,
                    new ResourceLocation(NatureRain.MOD_ID, "snail_house_dimension"));

    public static final ResourceKey<DimensionType> SNAIL_HOUSE_TYPE =
            ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY,SNAIL_HOUSE_KEY.getRegistryName());

    public static void register(){
        System.out.println("Registering ModDimensions for " + NatureRain.MOD_ID);
    }

}
