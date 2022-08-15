package fr.inuripse.naturerain.entity.projectile.event.entity.armor;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.item.armor.LeafyZirmsHelmet;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID)
public class FireImmunity {

    @SubscribeEvent
    public static void livingAttackEvent(final LivingAttackEvent event){
        if(!event.getEntity().getLevel().isClientSide()){
            if(event.getEntityLiving() instanceof Player){
                if(LeafyZirmsHelmet.hasFullLeafyZirmsArmor((Player)event.getEntityLiving())){
                    if(event.getSource().isFire()){
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}
