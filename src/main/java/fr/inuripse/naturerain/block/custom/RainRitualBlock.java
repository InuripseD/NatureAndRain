package fr.inuripse.naturerain.block.custom;

import fr.inuripse.naturerain.block.blockentity.ModBlockEntities;
import fr.inuripse.naturerain.block.blockentity.RainRitualBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class RainRitualBlock extends SimplePillarBlock {

    public static final BooleanProperty UNDER_RAIN = BooleanProperty.create("under_rain");

    public RainRitualBlock(Properties p_49224_) {
        super(p_49224_);
        this.registerDefaultState(this.stateDefinition.any().setValue(UNDER_RAIN, Boolean.valueOf(false)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(UNDER_RAIN);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        /*if (pLevel.isClientSide) {
            return pState.getValue(LIT) ? createTickerHelper(pBlockEntityType, ModBlockEntities.RAIN_RITUAL_BLOCK_ENTITY.get(), RainRitualBlockEntity::particleTick) : null;
        } else {
            return pState.getValue(LIT) ? createTickerHelper(pBlockEntityType, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cookTick) : createTickerHelper(pBlockEntityType, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cooldownTick);
        }*/
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlockEntities.RAIN_RITUAL_BLOCK_ENTITY.get(), RainRitualBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RainRitualBlockEntity(pPos, pState);
    }
}
