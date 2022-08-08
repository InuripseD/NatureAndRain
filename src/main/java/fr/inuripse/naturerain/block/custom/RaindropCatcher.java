package fr.inuripse.naturerain.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;
import java.util.stream.Stream;

public class RaindropCatcher extends Block {

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
        Block.box(1, 0, 1, 15, 10, 15),
        Block.box(3, 10, 3, 5, 11, 13),
        Block.box(11, 10, 3, 13, 11, 13),
        Block.box(5, 10, 3, 11, 11, 5),
        Block.box(5, 10, 11, 11, 11, 13),
        Block.box(5, 10, 5, 11, 10.1, 11)
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
        //super.tick(pState, pLevel, pPos, pRandom);
    }
    /*--------------------------------------*/


    //For block properties/blockstates
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, UNDER_RAIN);
    }


}
