package fr.inuripse.naturerain.item.armor;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LeafyZirmsArmor extends ModArmorItem {

    public LeafyZirmsArmor(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    public static void reduceDurability(ServerPlayer sPlayer) {
        for(ItemStack armorPart : sPlayer.getArmorSlots()){
            armorPart.hurt(1, sPlayer.getRandom(), sPlayer);
        }
        ServerLevel sLevel = sPlayer.getLevel();
        sLevel.playSound(null,sPlayer, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0f,2.6F + (sLevel.random.nextFloat() - sLevel.random.nextFloat()) * 0.8F);
        for(int l = 0; l < 8; ++l) {
            sLevel.sendParticles(sPlayer,ParticleTypes.LARGE_SMOKE, true, sPlayer.getX() + Math.random(),sPlayer.getY() + Math.random(),sPlayer.getZ() + Math.random(), 2, 1.0D,2.0D,1.0D,1);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(hasFullArmorSet(player, this.material)){
            if(!level.isClientSide()){
                if(player.isInWaterRainOrBubble()){
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,0));
                }
            }
        }
        super.onArmorTick(stack, level, player);
    }
}
