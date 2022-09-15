package fr.inuripse.naturerain.event.entity.armor;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.item.armor.LeafyZirmsArmor;
import fr.inuripse.naturerain.item.tiers.ModArmorMaterials;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NatureRain.MOD_ID)
public class FireImmunity {

    @SubscribeEvent
    public static void livingAttackEvent(final LivingAttackEvent event){
        if(!event.getEntity().getLevel().isClientSide()){
            if(event.getEntityLiving() instanceof ServerPlayer player){
                if(LeafyZirmsArmor.hasFullArmorSet(player, ModArmorMaterials.LEAFY_ZIRMS)){
                    DamageSource source = event.getSource();
                    if(source.isFire()) {
                        event.setCanceled(true);
                        if(source == DamageSource.HOT_FLOOR || source == DamageSource.IN_FIRE || source == DamageSource.LAVA){
                            if(player.getRandom().nextInt(35)<1) {
                                LeafyZirmsArmor.reduceDurability(player);
                            }
                        } else {
                            LeafyZirmsArmor.reduceDurability(player);
                        }
                    }
                }
            }
        }
    }

}
