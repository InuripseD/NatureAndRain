package fr.inuripse.naturerain.effect;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NatureRain.MOD_ID);

    public static final RegistryObject<MobEffect> LAVA_VISION = MOB_EFFECTS.register("lava_vision",
            () -> new LavaVisionEffect(MobEffectCategory.BENEFICIAL, 123548));

    public static void register(IEventBus eventBus){
        MOB_EFFECTS.register(eventBus);
    }

}
