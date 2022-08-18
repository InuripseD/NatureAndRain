package fr.inuripse.naturerain.item.armor;

import fr.inuripse.naturerain.item.tiers.ModArmorMaterials;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Random;

public class LeafyZirmsHelmet extends ArmorItem {

    public LeafyZirmsHelmet(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    public static void reduceDurability(Player player) {
        player.getInventory().getArmor(0).hurt(1,new Random(), (ServerPlayer) player);
        player.getInventory().getArmor(1).hurt(1,new Random(), (ServerPlayer) player);
        player.getInventory().getArmor(2).hurt(1,new Random(), (ServerPlayer) player);
        player.getInventory().getArmor(3).hurt(1,new Random(), (ServerPlayer) player);
        Level pLevel = player.getLevel();
        pLevel.playSound(null,player, SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 1.0f,2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);
        double i = player.getX();
        double j = player.getY();
        double k = player.getZ();

        for(int l = 0; l < 8; ++l) {
            ((ServerLevel)pLevel).sendParticles((ServerPlayer) player,ParticleTypes.LARGE_SMOKE, true, i+ Math.random(),j+ Math.random(),k+ Math.random(), 2, 1.0D,1.0D,2.0D,1);
        }
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if(hasFullLeafyZirmsArmor(player)){
            if(!level.isClientSide()){
                if(player.isInWaterRainOrBubble()){
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,0));
                }
            }
        }
        super.onArmorTick(stack, level, player);
    }

    public static boolean hasFullLeafyZirmsArmor(Player player){
        return !player.getInventory().getArmor(0).isEmpty() && !player.getInventory().getArmor(1).isEmpty() &&
                !player.getInventory().getArmor(2).isEmpty() && !player.getInventory().getArmor(3).isEmpty() &&
                ((ArmorItem)player.getInventory().getArmor(0).getItem()).getMaterial()== ModArmorMaterials.LEAFY_ZIRMS &&
                ((ArmorItem)player.getInventory().getArmor(1).getItem()).getMaterial()==ModArmorMaterials.LEAFY_ZIRMS &&
                ((ArmorItem)player.getInventory().getArmor(2).getItem()).getMaterial()==ModArmorMaterials.LEAFY_ZIRMS &&
                ((ArmorItem)player.getInventory().getArmor(3).getItem()).getMaterial()==ModArmorMaterials.LEAFY_ZIRMS;
    }

}
