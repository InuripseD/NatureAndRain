package fr.inuripse.naturerain.world;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.world.gen.ModEntityGeneration;
import fr.inuripse.naturerain.world.gen.ModOreGeneration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID)
public class ModWorldEvents {
    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        ModOreGeneration.generateOres(event);
        ModEntityGeneration.onEntitySpawn(event);
    }
}
