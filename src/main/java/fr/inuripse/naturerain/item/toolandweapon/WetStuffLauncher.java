package fr.inuripse.naturerain.item.toolandweapon;


import fr.inuripse.naturerain.entity.projectile.wetprojectile.WetProjectile;
import fr.inuripse.naturerain.item.ModItems;
import fr.inuripse.naturerain.item.custom.WetItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class WetStuffLauncher extends ProjectileWeaponItem {

    //Items that are allowed to be shot.
    public static final Predicate<ItemStack> WET_STUFF = (stack) -> {
        return stack.getItem().equals(ModItems.SOFTENED_HONEYCOMB.get()) || stack.getItem().equals(ModItems.FLOWING_GLOW_INK.get())
                || stack.getItem().equals(ModItems.WET_LEAF.get()) || stack.getItem().equals(ModItems.SOFTENED_SLIMEBALL.get());
    };

    public WetStuffLauncher(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player) {
            Player pPlayer = (Player)pEntityLiving;
            ItemStack itemstack = pPlayer.getProjectile(pStack);
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if(i>20) {
                if (!itemstack.isEmpty() && !pLevel.isClientSide() && !itemstack.is(Items.ARROW)) {
                    WetProjectile wetStuffToShoot = ((WetItem)itemstack.getItem()).getStuffToShoot(pLevel, pPlayer);
                    wetStuffToShoot.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F,  2.0F, 1.0F);
                    pLevel.addFreshEntity(wetStuffToShoot);
                    pLevel.playSound(null,pPlayer, SoundEvents.SLIME_JUMP, SoundSource.PLAYERS, 1.0f,2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);
                    itemstack.shrink(1);
                    pStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pStack.getEquipmentSlot()));
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        ItemStack itemstack1 = pPlayer.getProjectile(itemstack);
        boolean flag = !itemstack1.isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, pLevel, pPlayer, pHand, flag);
        if (ret != null) return ret;

        if (!pPlayer.getAbilities().instabuild && !flag && itemstack1.is(Items.ARROW)) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return WET_STUFF;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack pStack) { return 72000; }

    @Override
    public int getDefaultProjectileRange() { return 15; }
}
