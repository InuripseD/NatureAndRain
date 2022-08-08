package fr.inuripse.naturerain.event;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.event.loot.LeavesAdditionModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event){
        event.getRegistry().registerAll(
                new LeavesAdditionModifier.ModGlobalLootModifierSerilizer().setRegistryName(
                        new ResourceLocation(NatureRain.MOD_ID, "birch_leaves_glm")),
                new LeavesAdditionModifier.ModGlobalLootModifierSerilizer().setRegistryName(
                        new ResourceLocation(NatureRain.MOD_ID, "dark_oak_leaves_glm")),
                new LeavesAdditionModifier.ModGlobalLootModifierSerilizer().setRegistryName(
                        new ResourceLocation(NatureRain.MOD_ID, "jungle_leaves_glm")),
                new LeavesAdditionModifier.ModGlobalLootModifierSerilizer().setRegistryName(
                        new ResourceLocation(NatureRain.MOD_ID, "oak_leaves_glm"))
        );
    }

}
