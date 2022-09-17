package fr.inuripse.naturerain.world.gen;

import fr.inuripse.naturerain.world.feature.ModPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.List;

public class ModOreGeneration {

    public static void generateOres(final BiomeLoadingEvent event){
        /*----------Generate zirms ore---------*/
        List<Holder<PlacedFeature>> base =
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES);
        if(event.getCategory()!=Biome.BiomeCategory.DESERT && event.getCategory()!=Biome.BiomeCategory.MESA && event.getCategory()!=Biome.BiomeCategory.ICY){
            base.add(ModPlacedFeatures.ZIRMS_ORE_PLACED);
        }
        /*-------------------------------------*/
    }

}
