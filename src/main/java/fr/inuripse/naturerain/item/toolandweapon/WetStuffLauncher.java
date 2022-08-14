package fr.inuripse.naturerain.item.toolandweapon;


import fr.inuripse.naturerain.entity.projectile.wetshooterprojectile.SoftenedHoneycombEntity;
import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class WetStuffLauncher extends ProjectileWeaponItem {

    public static final Predicate<ItemStack> WET_STUFFS = (stack) -> {
        return stack.getItem().equals(ModItems.SOFTENED_HONEYCOMB.get());
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
            if(i>30) {
                if (!itemstack.isEmpty() && !pLevel.isClientSide()) {
                    SoftenedHoneycombEntity honeycomb = new SoftenedHoneycombEntity(pLevel, pPlayer);
                    honeycomb.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.4f * 3.0F, 1.0F);
                    pLevel.addFreshEntity(honeycomb);
                    itemstack.shrink(1);
                    pStack.hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(pStack.getEquipmentSlot()));
                }
            }
        }

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, pLevel, pPlayer, pHand, flag);
        if (ret != null) return ret;

        if (!pPlayer.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return WET_STUFFS;
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
