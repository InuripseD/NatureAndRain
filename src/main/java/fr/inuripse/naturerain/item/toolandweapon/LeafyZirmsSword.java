package fr.inuripse.naturerain.item.toolandweapon;

import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class LeafyZirmsSword extends SwordItem {

    private final Random random = new Random();
    private static final float POWER_DAMAGE = 8.0F;
    private static final Predicate<ItemStack> WET_STUFF = (stack) -> stack.getItem().equals(ModItems.WET_LEAF.get());

    public LeafyZirmsSword(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack holdItem = pPlayer.getItemInHand(pUsedHand);
        ItemStack ammunition = pPlayer.isCreative() ? new ItemStack(ModItems.WET_LEAF.get()) : findAmmo(holdItem,pPlayer);
        if(ammunition.isEmpty()) {
            return InteractionResultHolder.pass(holdItem);
        }else{
            if(!pLevel.isClientSide()){
                if(this.activePower(pLevel, pPlayer)) {
                    if(!pPlayer.isCreative()) {
                        holdItem.hurtAndBreak(5, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        pPlayer.getCooldowns().addCooldown(this, 200);
                        if(!ammunition.isEmpty()) {
                            ammunition.shrink(1);
                        }
                    }
                    if(ammunition.isEmpty()){
                        pPlayer.getInventory().removeItem(ammunition);
                    }
                    return InteractionResultHolder.consume(holdItem);
                }
            }
        }
        return InteractionResultHolder.pass(holdItem);
    }

    /*Method used when the item is right clicked, called in LeafyZirmsSword::use method
    * it will deal damage to nearby livingEntity and spawn particles and sound*/
    private boolean activePower(Level pLevel, Player pPlayer) {
        boolean atLeastOneLivingEntityAround = false;
        List<Entity> entitiesAround = pLevel.getEntities(pPlayer,pPlayer.getBoundingBox().inflate(3.0,0.5,3.0));
        if(!entitiesAround.isEmpty()) {
            for (Entity entity : entitiesAround) {
                if (entity instanceof LivingEntity) {
                    entity.hurt(DamageSource.playerAttack(pPlayer), POWER_DAMAGE);
                    addPowerParticles(pLevel, entity);
                    atLeastOneLivingEntityAround = true;
                }
            }
            /*If at least one entity has been hit then play sound and spawn particles*/
            if(atLeastOneLivingEntityAround){
                double i = pPlayer.getX();
                double j = pPlayer.getY();
                double k = pPlayer.getZ();
                pLevel.playSound(null,pPlayer,SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.PLAYERS, 1.0f,2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);
                ((ServerLevel)pLevel).sendParticles((ServerPlayer) pPlayer,ParticleTypes.DRIPPING_WATER, true, i,j,k, 60, 0.1d,0.1d,0.1d,3);
                addPowerParticles(pLevel,pPlayer);
            }
            return atLeastOneLivingEntityAround;
        }else{
            return false;
        }
    }

    /*A methode to spawn Particles around hitted entities*/
    private void addPowerParticles(Level pLevel, Entity entity) {
        for(int i = 0; i < 20; ++i) {
            double d0 = random.nextGaussian() * 0.02D;
            double d1 = random.nextGaussian() * 0.02D;
            double d2 = random.nextGaussian() * 0.02D;
            //((ServerLevel)pLevel).sendParticles(entity)
            pLevel.addParticle(ParticleTypes.RAIN, entity.getRandomX(1.0D), entity.getRandomY() + 1.0D, entity.getRandomZ(1.0D), d0, d1, d2);
            pLevel.addParticle(ParticleTypes.DRIPPING_WATER, entity.getRandomX(1.0D), entity.getRandomY() + 1.0D, entity.getRandomZ(1.0D), d0, d1, d2);
        }
    }

    /*Use to scan the player inventory and hands to use
    * the item ability if the player own some WET_STUFF*/
    private ItemStack findAmmo(ItemStack holdItem, Player pPlayer) {
        if(holdItem.getItem() instanceof LeafyZirmsSword){
            if(WET_STUFF.test(pPlayer.getItemInHand(InteractionHand.MAIN_HAND))){
                return pPlayer.getItemInHand(InteractionHand.MAIN_HAND);
            }else if(WET_STUFF.test(pPlayer.getItemInHand(InteractionHand.OFF_HAND))){
                return pPlayer.getItemInHand(InteractionHand.OFF_HAND);
            }else{
                for(int i = 0; i < pPlayer.getInventory().getContainerSize(); ++i) {
                    ItemStack itemstack1 = pPlayer.getInventory().getItem(i);
                    if (WET_STUFF.test(itemstack1)) {
                        return itemstack1;
                    }
                }
                return ItemStack.EMPTY;
            }
        }else{
            return ItemStack.EMPTY;
        }
    }
}
