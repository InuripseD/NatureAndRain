package fr.inuripse.naturerain;

import com.mojang.logging.LogUtils;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.blockentity.ModBlockEntities;
import fr.inuripse.naturerain.block.recipe.ModRecipes;
import fr.inuripse.naturerain.block.screen.ModMenuTypes;
import fr.inuripse.naturerain.block.screen.RaindropCatcherScreen;
import fr.inuripse.naturerain.config.NatureRainClientConfigs;
import fr.inuripse.naturerain.config.NatureRainCommonConfigs;
import fr.inuripse.naturerain.enchantment.ModEnchantments;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.FlowingGlowInkRenderer;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.SoftenedHoneycombRenderer;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.SoftenedSlimeballRenderer;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.render.WetLeafRenderer;
import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NatureRain.MOD_ID)
public class NatureRain
{
    public static final String MOD_ID = "naturerain";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public NatureRain()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEnchantments.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModEntityTypes.register(eventBus);

        eventBus.addListener(this::setup);

        eventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NatureRainClientConfigs.SPEC, "naturerain-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NatureRainCommonConfigs.SPEC, "naturerain-common.toml");

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event){
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.RAINDROP_CATCHER.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WET_HONEY_PUDDLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WET_SLIMEBALL_PUDDLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FLOWING_GLOW_INK_PUDDLE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WET_LEAVES_CARPET.get(), RenderType.translucent());

        MenuScreens.register(ModMenuTypes.RAINDROP_CATCHER_MENU.get(), RaindropCatcherScreen::new);

        EntityRenderers.register(ModEntityTypes.SOFTENED_HONEYCOMB.get(), SoftenedHoneycombRenderer::new);
        EntityRenderers.register(ModEntityTypes.FLOWING_GLOW_INK.get(), FlowingGlowInkRenderer::new);
        EntityRenderers.register(ModEntityTypes.SOFTENED_SLIMEBALL.get(), SoftenedSlimeballRenderer::new);
        EntityRenderers.register(ModEntityTypes.WET_LEAF.get(), WetLeafRenderer::new);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }


}
