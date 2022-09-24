package fr.inuripse.naturerain.event;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.util.capability.PlayerPosBeforeTpCapability;
import fr.inuripse.naturerain.util.data.PlayerPosBeforeTp;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).isPresent()) {
                event.addCapability(new ResourceLocation(NatureRain.MOD_ID, "properties"), new PlayerPosBeforeTpCapability());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerPosBeforeTpCapability.PLAYER_POS_BEFORE_TP).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerPosBeforeTp.class);
    }

}
