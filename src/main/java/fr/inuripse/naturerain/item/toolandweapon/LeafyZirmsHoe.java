package fr.inuripse.naturerain.item.toolandweapon;

import com.mojang.datafixers.util.Pair;
import fr.inuripse.naturerain.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.minecraft.world.level.block.FarmBlock.MOISTURE;

public class LeafyZirmsHoe extends HoeItem {

    public LeafyZirmsHoe(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    /*    It's the useOn method from HoeItem    */
    /* with a new method to get farmlands etc...*/
    /*      with a new sound and particles      */
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(pContext);
        if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        Level level = pContext.getLevel();
        BlockPos blockpos = pContext.getClickedPos();
        BlockState toolModifiedState = getCorrespondingBlockWhenUseHoeOn(level.getBlockState(blockpos), blockpos, level, pContext);
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = toolModifiedState == null ? null : Pair.of(ctx -> true, changeIntoState(toolModifiedState));
        if (pair == null) {
            return InteractionResult.PASS;
        } else {
            Predicate<UseOnContext> predicate = pair.getFirst();
            Consumer<UseOnContext> consumer = pair.getSecond();
            if (predicate.test(pContext)) {
                Player player = pContext.getPlayer();
                level.playSound(player, blockpos, SoundEvents.WET_GRASS_PLACE, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
                if (!level.isClientSide) {
                    this.getParticulesWhenHoeUsed(level, blockpos);
                    consumer.accept(pContext);
                    if (player != null) {
                        pContext.getItemInHand().hurtAndBreak(1, player, (p_150845_) -> {
                            p_150845_.broadcastBreakEvent(pContext.getHand());
                        });
                    }
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private void getParticulesWhenHoeUsed(Level level, BlockPos blockpos) {
        double i = blockpos.getX();
        double j = blockpos.getY();
        double k = blockpos.getZ();
        ((ServerLevel) level).sendParticles(ParticleTypes.RAIN, i+0.5, j+0.8, k+0.5, 8, 0.25D, 0.25D, 0.25D, 1);
    }

    /*Methode that return the blockstate for the right clicked block.*/
    private BlockState getCorrespondingBlockWhenUseHoeOn(BlockState blockState, BlockPos blockpos, Level level, UseOnContext pContext) {
        if(blockState==Blocks.GRASS_BLOCK.defaultBlockState()||blockState==Blocks.DIRT.defaultBlockState()||blockState==Blocks.DIRT_PATH.defaultBlockState()) {
            if(level.getBlockState(blockpos.above())==Blocks.AIR.defaultBlockState()) {
                return ModBlocks.WET_FARMLAND.get().defaultBlockState().setValue(MOISTURE, Integer.valueOf(7));
            }
            return null;
        } else {
            return level.getBlockState(blockpos).getToolModifiedState(pContext, net.minecraftforge.common.ToolActions.HOE_TILL, false);
        }
    }
}
