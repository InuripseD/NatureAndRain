package fr.inuripse.naturerain.block.custom;

import fr.inuripse.naturerain.block.blockentity.ModBlockEntities;
import fr.inuripse.naturerain.block.blockentity.RainRitualBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.stream.Stream;

public class RainRitualBlock extends SimplePillarBlock {

    public static final BooleanProperty UNDER_RAIN = BooleanProperty.create("under_rain");
    public static final BooleanProperty PROCESSING = BooleanProperty.create("processing");

    public RainRitualBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.stateDefinition.any().setValue(UNDER_RAIN, Boolean.valueOf(false)).setValue(PROCESSING, Boolean.valueOf(false)));
    }

    /*-------------------------SHAPE AND ROTATE----------------------*/
    private static final VoxelShape SHAPES = Stream.of(
            Block.box(3, 0, 3, 13, 8, 13),
            Block.box(2, 8, 2, 14, 10, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES;
    }
    /*----------------------------------------------------------------------*/

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(UNDER_RAIN, PROCESSING);
    }

    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        pLevel.setBlock(pPos, pState.setValue(UNDER_RAIN, Boolean.valueOf(pLevel.isRainingAt(pPos.above()))), 2);
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        if (pState.getValue(PROCESSING)) {
            if (pRand.nextInt(10) == 0) {
                pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.BOAT_PADDLE_WATER, SoundSource.BLOCKS, 0.6F + pRand.nextFloat(), pRand.nextFloat() * 0.7F + 0.6F, false);
            }

            if (pRand.nextInt(2) == 0) {
                for (int i = 0; i < pRand.nextInt(1) + 1; ++i) {
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.3D + (pRand.nextFloat() * 0.4), (double) pPos.getY() + 0.6D, (double) pPos.getZ() + 0.3D + (pRand.nextFloat() * 0.4), (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                }
            }
            if (pRand.nextInt(2) == 0) {
                for (int i = 0; i < pRand.nextInt(2) + 1; ++i) {
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D + pRand.nextFloat(), (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 1.5D + pRand.nextFloat(), (double) pPos.getY() + 0.15D, (double) pPos.getZ() + 0.5D, (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 2.5D + pRand.nextFloat(), (double) pPos.getY() - 0.25D, (double) pPos.getZ() + 0.5D, (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                }
            }
            if (pRand.nextInt(2) == 0) {
                for (int i = 0; i < pRand.nextInt(2) + 1; ++i) {
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D + pRand.nextFloat(), (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.15D, (double) pPos.getZ() + 1.5D + pRand.nextFloat(), (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() - 0.25D, (double) pPos.getZ() + 2.5D + pRand.nextFloat(), (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                }
            }
            if (pRand.nextInt(2) == 0) {
                for (int i = 0; i < pRand.nextInt(2) + 1; ++i) {
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D - pRand.nextFloat(), (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() - 0.5D - pRand.nextFloat(), (double) pPos.getY() + 0.15D, (double) pPos.getZ() + 0.5D, (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() - 1.5D - pRand.nextFloat(), (double) pPos.getY() - 0.25D, (double) pPos.getZ() + 0.5D, (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                }
            }

            if (pRand.nextInt(2) == 0) {
                for (int i = 0; i < pRand.nextInt(2) + 1; ++i) {
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D,  (double) pPos.getZ() + 0.5D - pRand.nextFloat(), (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.15D, (double) pPos.getZ() - 0.5D - pRand.nextFloat(), (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                    pLevel.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) pPos.getX() + 0.5D, (double) pPos.getY() - 0.25D, (double) pPos.getZ() - 1.5D - pRand.nextFloat(), (double) (pRand.nextFloat() * 10.0F), 2.0F, (double) (pRand.nextFloat() * 10.0F));
                }
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (!pLevel.isClientSide) {
            return createTickerHelper(pBlockEntityType, ModBlockEntities.RAIN_RITUAL_BLOCK_ENTITY.get(), RainRitualBlockEntity::tick);
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RainRitualBlockEntity(pPos, pState);
    }
}
