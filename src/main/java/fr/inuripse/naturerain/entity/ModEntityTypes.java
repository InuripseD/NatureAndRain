package fr.inuripse.naturerain.entity;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.FlowingGlowInkEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.SoftenedHoneycombEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.SoftenedSlimeballEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.WetLeafEntity;
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
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(8)
                    .updateInterval(20)
                    .build(new ResourceLocation(NatureRain.MOD_ID, "softened_honeycomb").toString()));

    public static final RegistryObject<EntityType<FlowingGlowInkEntity>> FLOWING_GLOW_INK = ENTITY_TYPES.register("flowing_glow_ink",
            () -> EntityType.Builder.of((EntityType.EntityFactory<FlowingGlowInkEntity>) FlowingGlowInkEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(8)
                    .updateInterval(20)
                    .build(new ResourceLocation(NatureRain.MOD_ID, "flowing_glow_ink").toString()));

    public static final RegistryObject<EntityType<SoftenedSlimeballEntity>> SOFTENED_SLIMEBALL = ENTITY_TYPES.register("softened_slimeball",
            () -> EntityType.Builder.of((EntityType.EntityFactory<SoftenedSlimeballEntity>) SoftenedSlimeballEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(8)
                    .updateInterval(20)
                    .build(new ResourceLocation(NatureRain.MOD_ID, "softened_slimeball").toString()));

    public static final RegistryObject<EntityType<WetLeafEntity>> WET_LEAF = ENTITY_TYPES.register("wet_leaf",
            () -> EntityType.Builder.of((EntityType.EntityFactory<WetLeafEntity>) WetLeafEntity::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .clientTrackingRange(8)
                    .updateInterval(20)
                    .build(new ResourceLocation(NatureRain.MOD_ID, "wet_leaf").toString()));

    public static final RegistryObject<EntityType<LittleSnailEntity>> LITTLE_SNAIL = ENTITY_TYPES.register("little_snail",
            () -> EntityType.Builder.of(LittleSnailEntity::new, MobCategory.AMBIENT)
                    .sized(0.1F, 0.1F)
                    .clientTrackingRange(6)
                    .build(new ResourceLocation(NatureRain.MOD_ID, "little_snail").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

}
