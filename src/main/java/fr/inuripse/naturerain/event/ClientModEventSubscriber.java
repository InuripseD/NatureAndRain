package fr.inuripse.naturerain.event;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetshooterprojectile.modelrenderer.SoftenedHoneycombModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

    @SubscribeEvent
    public static void layerRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SoftenedHoneycombModel.SOFTENED_HONEYCOMB, SoftenedHoneycombModel::createBodyLayer);
    }

}
