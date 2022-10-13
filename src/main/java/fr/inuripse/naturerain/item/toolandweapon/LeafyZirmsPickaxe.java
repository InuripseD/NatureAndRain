package fr.inuripse.naturerain.item.toolandweapon;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import static fr.inuripse.naturerain.config.NatureRainCommonConfigs.ZirmsPickaxePowerInNether;

public class LeafyZirmsPickaxe extends PickaxeItem {

    public LeafyZirmsPickaxe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        BlockHitResult blockhitresult = getPlayerPOVHitResult(pLevel, pPlayer, ClipContext.Fluid.ANY);
        BlockPlaceContext blockPlaceContext = new BlockPlaceContext(new UseOnContext(pPlayer, pUsedHand, blockhitresult));
        return new InteractionResultHolder<>(tryPlace(blockPlaceContext), pPlayer.getItemInHand(pUsedHand));
    }

    /*When right click, this method is call to check if
    * the pos the player clicked is a lava source or lava flow
    * and then replace it by obsi or stone if it's modifiable*/
    private InteractionResult tryPlace(BlockPlaceContext pContext) {

        /*Can we replace the target block ?*/
        if (!pContext.canPlace()) {
            return InteractionResult.FAIL;
        } else {

            /*Are we really targeting a block ?*/
            BlockState blockstate = Blocks.OBSIDIAN.defaultBlockState();
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

            /*Are we targeting a fluid that is lava ?*/
            if(fluidstate.getType() != Fluids.LAVA && fluidstate.getType() != Fluids.FLOWING_LAVA) {
                return InteractionResult.FAIL;
            } else if(pContext.getLevel().dimensionType().ultraWarm() && !ZirmsPickaxePowerInNether.get()){
                return InteractionResult.FAIL;
            } else {
                /*Is the block placed ?*/
                if (!this.placeBlock(pContext, blockstate, fluidstate)) {
                    return InteractionResult.FAIL;
                } else {
                    BlockPos blockpos = pContext.getClickedPos();
                    Level level = pContext.getLevel();
                    Player player = pContext.getPlayer();
                    ItemStack itemstack = pContext.getItemInHand();
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    Block block = blockstate1.getBlock();
                    if (blockstate1.is(blockstate.getBlock())) {
                        block.onBlockStateChange(level,blockpos,fluidstate.createLegacyBlock(),blockstate);
                        blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
                        }
                    }

                    /*Play sound.*/
                    level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
                    SoundType soundtype = blockstate.getSoundType();
                    level.playSound(player, blockpos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

                    /*Decrease durability and return.*/
                    if (!level.isClientSide) {
                        if(player!=null) {
                            if (pContext.getLevel().dimensionType().ultraWarm()) {
                                itemstack.hurtAndBreak(7, player, (player1) -> player1.broadcastBreakEvent(pContext.getHand()));
                                player.getCooldowns().addCooldown(this, 30);
                            } else {
                                itemstack.hurtAndBreak(5, player, (player1) -> player1.broadcastBreakEvent(pContext.getHand()));
                                player.getCooldowns().addCooldown(this, 10);
                            }
                        }
                        /*Particles*/
                        ((ServerLevel) level).sendParticles(ParticleTypes.LARGE_SMOKE, blockpos.getX()+0.5, blockpos.getY()+0.8, blockpos.getZ()+0.5, 8, 0.35D, 0.15D, 0.35D, 0.0D);
                    }
                    return InteractionResult.sidedSuccess(level.isClientSide);
                }
            }
        }
    }

    private boolean placeBlock(BlockPlaceContext pContext, BlockState blockstate, FluidState fluid) {
        if(fluid.getType()==Fluids.LAVA){
            blockstate = Blocks.OBSIDIAN.defaultBlockState();
        } else if (fluid.getType()==Fluids.FLOWING_LAVA) {
            blockstate = Blocks.COBBLESTONE.defaultBlockState();
        }
        return pContext.getLevel().setBlock(pContext.getClickedPos(), blockstate, 11);
    }

}
