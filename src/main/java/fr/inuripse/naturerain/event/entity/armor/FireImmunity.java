package fr.inuripse.naturerain.event.entity.armor;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.item.armor.LeafyZirmsHelmet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.behavior.PrepareRamNearestTarget;
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
                Player player = (Player)event.getEntityLiving();
                if(LeafyZirmsHelmet.hasFullLeafyZirmsArmor(player)){
                    DamageSource source = event.getSource();
                    if(source.isFire()) {
                        event.setCanceled(true);
                        if(source == DamageSource.HOT_FLOOR || source == DamageSource.IN_FIRE || source == DamageSource.LAVA){
                            if(player.getRandom().nextInt(35)<1) {
                                LeafyZirmsHelmet.reduceDurability(player);
                            }
                        } else {
                            LeafyZirmsHelmet.reduceDurability(player);
                        }
                    }
                }
            }
        }
    }

}
