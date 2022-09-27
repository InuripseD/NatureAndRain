package fr.inuripse.naturerain.event.client;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.networking.ModMessages;
import fr.inuripse.naturerain.networking.packet.KeyPressedC2Spacket;
import fr.inuripse.naturerain.util.ModKeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event){
        if(ModKeyBindings.ENTERING_SHELL_KEY.consumeClick()){
            ModMessages.sendToServer(new KeyPressedC2Spacket());
        }
    }

}
