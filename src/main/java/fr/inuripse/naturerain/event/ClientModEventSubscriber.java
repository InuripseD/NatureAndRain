package fr.inuripse.naturerain.event;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.blockentity.ModBlockEntities;
import fr.inuripse.naturerain.block.blockentity.blockentityrenderer.RainRitualBlockRenderer;
import fr.inuripse.naturerain.block.blockentity.blockentityrenderer.SimplePillarBlockRenderer;
import fr.inuripse.naturerain.block.screen.ModMenuTypes;
import fr.inuripse.naturerain.block.screen.RaindropCatcherScreen;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.model.*;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.FlowingGlowInkRenderer;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.SoftenedHoneycombRenderer;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.SoftenedSlimeballRenderer;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.WetLeafRenderer;
import fr.inuripse.naturerain.entity.renderer.LittleSnailRenderer;
import fr.inuripse.naturerain.item.armor.SnailShellChestplate;
import fr.inuripse.naturerain.item.armor.renderer.SnailShellChestplateRenderer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEventSubscriber {

    @SubscribeEvent
    public static void layerRegister(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SoftenedHoneycombModel.MODEL_LAYER_LOCATION, SoftenedHoneycombModel::createBodyLayer);
        event.registerLayerDefinition(FlowingGlowInkModel.MODEL_LAYER_LOCATION, FlowingGlowInkModel::createBodyLayer);
        event.registerLayerDefinition(SoftenedSlimeballModel.MODEL_LAYER_LOCATION, SoftenedSlimeballModel::createBodyLayer);
        event.registerLayerDefinition(WetLeafModel.MODEL_LAYER_LOCATION, WetLeafModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.RAIN_RITUAL_BLOCK_ENTITY.get(), RainRitualBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.SIMPLE_PILLAR_BLOCK_ENTITY.get(), SimplePillarBlockRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RAINDROP_CATCHER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RAIN_RITUAL_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.SIMPLE_PILLAR.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WET_HONEY_PUDDLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WET_SLIMEBALL_PUDDLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FLOWING_GLOW_INK_PUDDLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WET_LEAVES_CARPET.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.RAINDROP_CATCHER_MENU.get(), RaindropCatcherScreen::new);

        EntityRenderers.register(ModEntityTypes.SOFTENED_HONEYCOMB.get(), SoftenedHoneycombRenderer::new);
        EntityRenderers.register(ModEntityTypes.FLOWING_GLOW_INK.get(), FlowingGlowInkRenderer::new);
        EntityRenderers.register(ModEntityTypes.SOFTENED_SLIMEBALL.get(), SoftenedSlimeballRenderer::new);
        EntityRenderers.register(ModEntityTypes.WET_LEAF.get(), WetLeafRenderer::new);
        EntityRenderers.register(ModEntityTypes.LITTLE_SNAIL.get(), LittleSnailRenderer::new);
    }

    @SubscribeEvent
    public static void registerArmorRenderers(final EntityRenderersEvent.AddLayers event){
        GeoArmorRenderer.registerArmorRenderer(SnailShellChestplate.class, new SnailShellChestplateRenderer());
    }

}
