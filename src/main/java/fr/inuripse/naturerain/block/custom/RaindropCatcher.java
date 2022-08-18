package fr.inuripse.naturerain.block.custom;

import fr.inuripse.naturerain.block.blockentity.ModBlockEntities;
import fr.inuripse.naturerain.block.blockentity.RaindropCatcherEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.stream.Stream;

public class RaindropCatcher extends BaseEntityBlock {

    //Update under water section
    public static final BooleanProperty UNDER_RAIN = BooleanProperty.create("under_rain");

    //Rotation Section
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public RaindropCatcher(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH)
                .setValue(UNDER_RAIN, Boolean.valueOf(false)));
    }

    /*---------- Shape Section ----------*/
    private static final VoxelShape SHAPE = Stream.of(
            Block.box(1, 0, 1, 15, 9, 15),
            Block.box(5, 9, 5, 11, 10, 11),
            Block.box(4, 10, 4, 12, 11, 12)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
    /*--------------------------------------*/



    /*---------- Rotation Section ----------*/
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    /*--------------------------------------*/



    /*--------- Update under water ---------*/
    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return true;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        pLevel.setBlock(pPos, pState.setValue(UNDER_RAIN, Boolean.valueOf(pLevel.isRainingAt(pPos.above()))), 2);
    }
    /*--------------------------------------*/


    //For block properties/blockstates
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, UNDER_RAIN);
    }


    /*--------- Block Entity Properties ---------*/
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof RaindropCatcherEntity) {
                if (pLevel instanceof ServerLevel) {
                    ((RaindropCatcherEntity)blockentity).drops();
                }
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RaindropCatcherEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.RAINDROP_CATCHER_ENTITY.get(), RaindropCatcherEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof RaindropCatcherEntity) {
                NetworkHooks.openGui(((ServerPlayer)pPlayer), (RaindropCatcherEntity)blockentity, pPos);
            }
            return InteractionResult.CONSUME;
        }
    }
    /*-------------------------------------------*/

}
