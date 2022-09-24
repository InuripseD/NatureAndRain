package fr.inuripse.naturerain.item.toolandweapon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class LeafyZirmsAxe extends AxeItem {

    public LeafyZirmsAxe(Tier pTier, float pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    /*-----------------------------------------------------------------------*/
    /*Basically it's the BoneMealItem class but I removed the stack.shrink(1)*/
    /*                 to replace by damageItem(5) instead                   */
    /*                 I also Added sounds and particles                     */
    /*-----------------------------------------------------------------------*/
    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        InteractionResult superRes = super.useOn(pContext);
        if(superRes!=InteractionResult.PASS){
            return superRes;
        }
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(pContext.getClickedFace());
        ItemStack holdItem = pContext.getItemInHand();
        Player pPlayer = pContext.getPlayer();
        if (applyBonemeal(holdItem, level, blockpos, pContext.getPlayer())) {
            level.playSound(pPlayer, blockpos1, SoundEvents.WET_GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
            if(pPlayer!=null) {
                pPlayer.getCooldowns().addCooldown(this, 20);
                if (!level.isClientSide) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.RAIN, blockpos.getX(), blockpos.getY(), blockpos.getZ(), 30, 1.5D, 1.0D, 1.5D, 1);
                    level.levelEvent(1505, blockpos, 0);
                    pContext.getItemInHand().hurtAndBreak(5, pPlayer, (player) -> {
                        player.broadcastBreakEvent(pContext.getHand());
                    });
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            BlockState blockstate = level.getBlockState(blockpos);
            boolean flag = blockstate.isFaceSturdy(level, blockpos, pContext.getClickedFace());
            if (flag && growWaterPlant(level, blockpos1, pContext.getClickedFace())) {
                level.playSound(pPlayer, blockpos1, SoundEvents.WET_GRASS_PLACE, SoundSource.BLOCKS, 1.0F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
                if(pPlayer!=null) {
                    pPlayer.getCooldowns().addCooldown(this, 20);
                    if (!level.isClientSide) {
                        level.levelEvent(1505, blockpos, 0);
                        pContext.getItemInHand().hurtAndBreak(5, pPlayer, (player) -> {
                            player.broadcastBreakEvent(pContext.getHand());
                        });                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private static boolean applyBonemeal(ItemStack pStack, Level pLevel, BlockPos pPos, net.minecraft.world.entity.player.Player player) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        int hook = net.minecraftforge.event.ForgeEventFactory.onApplyBonemeal(player, pLevel, pPos, blockstate, pStack);
        if (hook != 0) return hook > 0;
        if (blockstate.getBlock() instanceof BonemealableBlock bonemealableblock) {
            if (bonemealableblock.isValidBonemealTarget(pLevel, pPos, blockstate, pLevel.isClientSide)) {
                if (pLevel instanceof ServerLevel) {
                    if (bonemealableblock.isBonemealSuccess(pLevel, pLevel.random, pPos, blockstate)) {
                        bonemealableblock.performBonemeal((ServerLevel)pLevel, pLevel.random, pPos, blockstate);
                    }
                }

                return true;
            }
        }
        return false;
    }

    private static boolean growWaterPlant(Level pLevel, BlockPos pPos, @Nullable Direction pClickedSide) {
        if (pLevel.getBlockState(pPos).is(Blocks.WATER) && pLevel.getFluidState(pPos).getAmount() == 8) {
            if (!(pLevel instanceof ServerLevel)) {
                return true;
            }
            Random random = pLevel.getRandom();

            label78:
            for(int i = 0; i < 128; ++i) {
                BlockPos blockpos = pPos;
                BlockState blockstate = Blocks.SEAGRASS.defaultBlockState();

                for(int j = 0; j < i / 16; ++j) {
                    blockpos = blockpos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                    if (pLevel.getBlockState(blockpos).isCollisionShapeFullBlock(pLevel, blockpos)) {
                        continue label78;
                    }
                }

                Holder<Biome> holder = pLevel.getBiome(blockpos);
                if (holder.is(Biomes.WARM_OCEAN)) {
                    if (i == 0 && pClickedSide != null && pClickedSide.getAxis().isHorizontal()) {
                        blockstate = Registry.BLOCK.getTag(BlockTags.WALL_CORALS).flatMap((p_204098_) -> {
                            return p_204098_.getRandomElement(pLevel.random);
                        }).map((p_204100_) -> {
                            return p_204100_.value().defaultBlockState();
                        }).orElse(blockstate);
                        if (blockstate.hasProperty(BaseCoralWallFanBlock.FACING)) {
                            blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, pClickedSide);
                        }
                    } else if (random.nextInt(4) == 0) {
                        blockstate = Registry.BLOCK.getTag(BlockTags.UNDERWATER_BONEMEALS).flatMap((p_204091_) -> {
                            return p_204091_.getRandomElement(pLevel.random);
                        }).map((p_204095_) -> {
                            return p_204095_.value().defaultBlockState();
                        }).orElse(blockstate);
                    }
                }

                if (blockstate.is(BlockTags.WALL_CORALS, (p_204093_) -> {
                    return p_204093_.hasProperty(BaseCoralWallFanBlock.FACING);
                })) {
                    for(int k = 0; !blockstate.canSurvive(pLevel, blockpos) && k < 4; ++k) {
                        blockstate = blockstate.setValue(BaseCoralWallFanBlock.FACING, Direction.Plane.HORIZONTAL.getRandomDirection(random));
                    }
                }

                if (blockstate.canSurvive(pLevel, blockpos)) {
                    BlockState blockstate1 = pLevel.getBlockState(blockpos);
                    if (blockstate1.is(Blocks.WATER) && pLevel.getFluidState(blockpos).getAmount() == 8) {
                        pLevel.setBlock(blockpos, blockstate, 3);
                    } else if (blockstate1.is(Blocks.SEAGRASS) && random.nextInt(10) == 0) {
                        ((BonemealableBlock)Blocks.SEAGRASS).performBonemeal((ServerLevel)pLevel, random, blockpos, blockstate1);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

}
