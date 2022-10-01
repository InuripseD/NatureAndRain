package fr.inuripse.naturerain.event.client;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.effect.ModEffects;
import fr.inuripse.naturerain.networking.ModMessages;
import fr.inuripse.naturerain.networking.packet.KeyPressedC2Spacket;
import fr.inuripse.naturerain.util.ModKeyBindings;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void renderFog(final EntityViewRenderEvent.RenderFogEvent event)
    {
        if(event.getCamera().getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(ModEffects.LAVA_VISION.get()) && event.getCamera().getBlockAtCamera().getBlock().equals(Blocks.LAVA)) {
                if (event.isCancelable()) {
                    event.setCanceled(true);
                    event.setFarPlaneDistance(24.0F);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event){
        if(ModKeyBindings.ENTERING_SHELL_KEY.consumeClick()){
            ModMessages.sendToServer(new KeyPressedC2Spacket());
        }
    }

}
