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
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class WetStuffLauncher extends ProjectileWeaponItem {

    /*-- Items that are allowed to be shot --*/
    private static final Predicate<ItemStack> WET_STUFF = (stack) -> {
        return stack.getItem().equals(ModItems.SOFTENED_HONEYCOMB.get()) || stack.getItem().equals(ModItems.FLOWING_GLOW_INK.get())
                || stack.getItem().equals(ModItems.WET_LEAF.get()) || stack.getItem().equals(ModItems.SOFTENED_SLIMEBALL.get());
    };

    public WetStuffLauncher(Properties properties) {
        super(properties);
    }

    /*----- Do stuff like a bow -----*/
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack launcherItemStack = pPlayer.getItemInHand(pHand);
        ItemStack projectile = pPlayer.getProjectile(launcherItemStack);
        boolean flag = !projectile.isEmpty() && !projectile.is(Items.ARROW);
        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(launcherItemStack, pLevel, pPlayer, pHand, flag);
        if (ret != null) return ret;
        if (flag) {
                pPlayer.startUsingItem(pHand);
                return InteractionResultHolder.consume(launcherItemStack);
        }
        return InteractionResultHolder.fail(launcherItemStack);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player pPlayer) {
            ItemStack itemstack = pPlayer.getProjectile(pStack);
            int i = this.getUseDuration(pStack) - pTimeLeft;
            if(i>5) {
                if (!itemstack.isEmpty() && !pLevel.isClientSide() && !itemstack.is(Items.ARROW)) {
                    WetProjectile wetStuffToShoot = ((WetItem) itemstack.getItem()).getStuffToShoot(pLevel, pPlayer);
                    wetStuffToShoot.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, getProjectileVelocity(i), 1.0F);
                    pLevel.addFreshEntity(wetStuffToShoot);
                    pStack.hurtAndBreak(1, pPlayer, (player) -> {
                        player.broadcastBreakEvent(pPlayer.getUsedItemHand());
                    });
                }
                pLevel.playSound(pPlayer,pPlayer, SoundEvents.SLIME_JUMP, SoundSource.PLAYERS, 1.0f,2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);
                if (!pPlayer.isCreative()) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        pPlayer.getInventory().removeItem(itemstack);
                    }
                }
            }
        }
    }

    private float getProjectileVelocity(int timeUse){
        return (timeUse > 30) ? 2.0F : 0.5F + (float)timeUse * 0.05F;
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return WET_STUFF;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
    /*------------------------------------*/
}
