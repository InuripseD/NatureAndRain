package fr.inuripse.naturerain.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SimplePillarBlockEntity extends MainPillarBlockEntity{

    public SimplePillarBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.SIMPLE_PILLAR_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, SimplePillarBlockEntity pBlockEntity) {

    }

}
