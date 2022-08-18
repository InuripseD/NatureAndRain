package fr.inuripse.naturerain.item.toolandweapon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class LeafyZirmsShovel extends ShovelItem {

    private final Fluid content = Fluids.WATER;

    public LeafyZirmsShovel(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    /*----------------------------------------------------------------------*/
    /*            Basically it's the BucketItem class but adapted           */
    /*----------------------------------------------------------------------*/
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        return this.use(pContext.getLevel(),pContext.getPlayer(),pContext.getHand()).getResult();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
        if(pPlayer!=null) {

            /*if player sneak, we try to activate the power! */
            if (pPlayer.isCrouching()) {
                if (this.activePower(pPlayer, pLevel, pUsedHand, blockhitresult)) {
                    if (!pLevel.isClientSide()) {
                        if (pPlayer != null) {
                            itemstack.hurtAndBreak(5, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                        }
                    }
                    return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
                }
            } else {

                /*If we already used the showel power (AKA make a path) we pass.*
                 *              Else we try to activate the power!               */
                InteractionResult superInteractionResult = super.useOn(new UseOnContext(pPlayer,pUsedHand, getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY)));
                if (superInteractionResult == InteractionResult.PASS) {
                    if (this.activePower(pPlayer, pLevel, pUsedHand, blockhitresult)) {
                        if (!pLevel.isClientSide()) {
                            if (pPlayer != null) {
                                itemstack.hurtAndBreak(5, pPlayer, (player) -> player.broadcastBreakEvent(pUsedHand));
                            }
                        }
                        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
                    }
                }
                return new InteractionResultHolder<>(superInteractionResult,itemstack);
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    /*Basically the power is: remove water source if there is one, or try to place a water source.*/
    private boolean activePower(Player pPlayer, Level pLevel, InteractionHand pUsedHand, BlockHitResult blockhitresult) {
        if(blockhitresult.getType() == HitResult.Type.BLOCK){
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
            if (pLevel.mayInteract(pPlayer, blockpos) && pPlayer.mayUseItemAt(blockpos1, direction, itemstack)) {
                FluidState fluidstate = pLevel.getFluidState(blockpos);
                /*If aim place is a block and this block is a water source then try to pick it up.*/
                if(fluidstate.getType()==this.content){
                    BlockState blockstate = pLevel.getBlockState(blockpos);
                    if (blockstate.getBlock() instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup)blockstate.getBlock();
                        ItemStack itemstack1 = bucketpickup.pickupBlock(pLevel, blockpos, blockstate);
                        if (!itemstack1.isEmpty()) {
                            bucketpickup.getPickupSound(blockstate).ifPresent((p_150709_) -> {
                                pPlayer.playSound(p_150709_, 1.0F, 1.0F);
                            });
                            return true;
                        }
                    }
                }else{
                    /*Else try to identify a place to place a water source*/
                    BlockHitResult blockhitresult1 = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.SOURCE_ONLY);
                    BlockPos bBlockpos = blockhitresult1.getBlockPos();
                    Direction dDirection = blockhitresult1.getDirection();
                    BlockPos bBlockpos1 = bBlockpos.relative(dDirection);
                    BlockState blockstate = pLevel.getBlockState(blockpos);
                    BlockPos blockpos2 = canBlockContainFluid(pLevel, bBlockpos, blockstate) ? bBlockpos : bBlockpos1;
                    return this.emptyContents(pPlayer, pLevel, blockpos2, blockhitresult1);
                }
            }
        }
        return false;
    }

    /*It's the emptyContents method from bucketItem, not fully optimised but it's ok it works.*/
    private boolean emptyContents(@Nullable Player pPlayer, Level pLevel, BlockPos pPos, @Nullable BlockHitResult pResult) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        Block block = blockstate.getBlock();
        Material material = blockstate.getMaterial();
        boolean flag = blockstate.canBeReplaced(this.content);
        boolean flag1 = blockstate.isAir() || flag || block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(pLevel, pPos, blockstate, this.content);
        if (!flag1) {
            return pResult != null && this.emptyContents(pPlayer, pLevel, pResult.getBlockPos().relative(pResult.getDirection()), (BlockHitResult)null);
        } else if (pLevel.dimensionType().ultraWarm()) {
            int i = pPos.getX();
            int j = pPos.getY();
            int k = pPos.getZ();
            pLevel.playSound(pPlayer, pPos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (pLevel.random.nextFloat() - pLevel.random.nextFloat()) * 0.8F);

            for(int l = 0; l < 8; ++l) {
                pLevel.addParticle(ParticleTypes.LARGE_SMOKE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
            }

            return true;
        } else if (block instanceof LiquidBlockContainer && ((LiquidBlockContainer)block).canPlaceLiquid(pLevel,pPos,blockstate,content)) {
            ((LiquidBlockContainer)block).placeLiquid(pLevel, pPos, blockstate, ((FlowingFluid)this.content).getSource(false));
            pLevel.playSound(pPlayer, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            return true;
        } else {
            if (!pLevel.isClientSide && flag && !material.isLiquid()) {
                pLevel.destroyBlock(pPos, true);
            }

            if (!pLevel.setBlock(pPos, this.content.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                return false;
            } else {
                pLevel.playSound(pPlayer, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                return true;
            }
        }
    }

    /*Methode from BucketItem class*/
    private boolean canBlockContainFluid(Level worldIn, BlockPos posIn, BlockState blockstate)
    {
        return blockstate.getBlock() instanceof LiquidBlockContainer && ((LiquidBlockContainer)blockstate.getBlock()).canPlaceLiquid(worldIn, posIn, blockstate, Fluids.WATER);
    }

}
