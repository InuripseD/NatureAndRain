package fr.inuripse.naturerain.block.recipe;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, NatureRain.MOD_ID);

    public static final RegistryObject<RecipeSerializer<RaindropCatcherRecipe>> RAINDROP_CATCHER_SERIALIZER =
            SERIALIZERS.register("hydration", () -> RaindropCatcherRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<RainRitualRecipe>> RAIN_RITUAL_SERIALIZER =
            SERIALIZERS.register("rain_ritual", () -> RainRitualRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
