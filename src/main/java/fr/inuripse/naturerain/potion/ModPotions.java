package fr.inuripse.naturerain.potion;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, NatureRain.MOD_ID);

    public static final RegistryObject<Potion> LAVA_VISION = POTIONS.register("lava_vision_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.LAVA_VISION.get(), 9600, 0)));

    public static void register(IEventBus eventBus){
        POTIONS.register(eventBus);
    }


}
