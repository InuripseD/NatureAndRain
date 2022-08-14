package fr.inuripse.naturerain.entity;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetshooterprojectile.SoftenedHoneycombEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, NatureRain.MOD_ID);

    public static final RegistryObject<EntityType<SoftenedHoneycombEntity>> SOFTENED_HONEYCOMB = ENTITY_TYPES.register("softened_honeycomb",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SoftenedHoneycombEntity>)SoftenedHoneycombEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(8)
                    .updateInterval(10)
                    .build(new ResourceLocation(NatureRain.MOD_ID, "softened_honeycomb").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

}
