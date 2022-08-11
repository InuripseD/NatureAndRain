package fr.inuripse.naturerain.block.blockentity;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, NatureRain.MOD_ID);

    public static final RegistryObject<BlockEntityType<RaindropCatcherEntity>> RAINDROP_CATCHER_ENTITY =
            BLOCK_ENTITIES.register("raindrop_catcher_entity",
                    () -> BlockEntityType.Builder.of(RaindropCatcherEntity::new,
                            ModBlocks.RAINDROP_CATCHER.get()).build(null));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITIES.register(eventBus);
    }

}
