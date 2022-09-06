package fr.inuripse.naturerain.world.gen;

import fr.inuripse.naturerain.entity.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModEntityGeneration {
    public static void onEntitySpawn(final BiomeLoadingEvent event){
        addEntitySnail(event, ModEntityTypes.LITTLE_SNAIL.get(), 30, 1,2);
    }

    private static void addEntitySnail(BiomeLoadingEvent event, EntityType<?> type, int weight, int minCount, int maxCount){
        boolean isGoodBiome = event.getCategory() == Biome.BiomeCategory.FOREST;
        if(isGoodBiome){
            event.getSpawns().getSpawner(type.getCategory()).add(new MobSpawnSettings.SpawnerData(type, weight, minCount, maxCount));
        }
    }

}
