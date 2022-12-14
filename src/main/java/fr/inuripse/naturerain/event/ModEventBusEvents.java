package fr.inuripse.naturerain.event;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.recipe.RainRitualRecipe;
import fr.inuripse.naturerain.block.recipe.RaindropCatcherRecipe;
import fr.inuripse.naturerain.entity.LittleSnailEntity;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import fr.inuripse.naturerain.entity.MountSnailEntity;
import fr.inuripse.naturerain.event.loot.LeavesAdditionModifier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
        event.getRegistry().registerAll(
                new LeavesAdditionModifier.ModGlobalLootModifierSerilizer().setRegistryName(
                        new ResourceLocation(NatureRain.MOD_ID, "leaves_loot_modifier"))
        );
    }

    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event){
        Registry.register(Registry.RECIPE_TYPE, RainRitualRecipe.Type.ID, RainRitualRecipe.Type.INSTANCE);
        Registry.register(Registry.RECIPE_TYPE, RaindropCatcherRecipe.Type.ID, RaindropCatcherRecipe.Type.INSTANCE);
    }

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event){
        event.put(ModEntityTypes.LITTLE_SNAIL.get(), LittleSnailEntity.createAttributes());
        event.put(ModEntityTypes.MOUNT_SNAIL.get(), MountSnailEntity.createAttributes());
    }

}
