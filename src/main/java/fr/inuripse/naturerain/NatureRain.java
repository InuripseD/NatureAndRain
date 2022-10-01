package fr.inuripse.naturerain;

import com.mojang.logging.LogUtils;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.blockentity.ModBlockEntities;
import fr.inuripse.naturerain.block.recipe.ModRecipes;
import fr.inuripse.naturerain.block.screen.ModMenuTypes;
import fr.inuripse.naturerain.config.NatureRainClientConfigs;
import fr.inuripse.naturerain.config.NatureRainCommonConfigs;
import fr.inuripse.naturerain.effect.ModEffects;
import fr.inuripse.naturerain.enchantment.ModEnchantments;
import fr.inuripse.naturerain.entity.LittleSnailEntity;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import fr.inuripse.naturerain.event.loot.lootitemcondition.ModLootItemConditions;
import fr.inuripse.naturerain.item.ModItems;
import fr.inuripse.naturerain.networking.ModMessages;
import fr.inuripse.naturerain.potion.ModPotions;
import fr.inuripse.naturerain.world.dimension.ModDimensions;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NatureRain.MOD_ID)
public class NatureRain
{
    public static final String MOD_ID = "naturerain";

    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public NatureRain()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NatureRainClientConfigs.SPEC, "naturerain-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, NatureRainCommonConfigs.SPEC, "naturerain-common.toml");

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModEnchantments.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenuTypes.register(eventBus);
        ModRecipes.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModLootItemConditions.register(eventBus);
        ModDimensions.register();
        ModEffects.register(eventBus);
        ModPotions.register(eventBus);

        eventBus.addListener(this::setup);

        GeckoLib.initialize();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());

        event.enqueueWork(() -> {
            ModMessages.register();
        });

        SpawnPlacements.register(ModEntityTypes.LITTLE_SNAIL.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.WORLD_SURFACE,
                LittleSnailEntity::checkSnailSpawnRules);

    }


}
