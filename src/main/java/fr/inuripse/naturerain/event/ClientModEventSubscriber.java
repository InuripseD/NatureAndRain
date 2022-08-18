package fr.inuripse.naturerain.event;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.SoftenedSlimeballEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.model.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

    @SubscribeEvent
    public static void layerRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SoftenedHoneycombModel.MODEL_LAYER_LOCATION, SoftenedHoneycombModel::createBodyLayer);
        event.registerLayerDefinition(FlowingGlowInkModel.MODEL_LAYER_LOCATION, FlowingGlowInkModel::createBodyLayer);
        event.registerLayerDefinition(SoftenedSlimeballModel.MODEL_LAYER_LOCATION, SoftenedSlimeballModel::createBodyLayer);
        event.registerLayerDefinition(WetLeafModel.MODEL_LAYER_LOCATION, WetLeafModel::createBodyLayer);
    }

}
