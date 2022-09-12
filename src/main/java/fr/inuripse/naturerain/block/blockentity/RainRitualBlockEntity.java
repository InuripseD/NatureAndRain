package fr.inuripse.naturerain.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RainRitualBlockEntity extends MainPillarBlockEntity{

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 120;

    public RainRitualBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.RAIN_RITUAL_BLOCK_ENTITY.get(), pWorldPosition, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0: return RainRitualBlockEntity.this.progress;
                    case 1: return RainRitualBlockEntity.this.maxProgress;
                    default: return 0;
                }
            }

            public void set(int index, int value) {
                switch(index) {
                    case 0: RainRitualBlockEntity.this.progress = value; break;
                    case 1: RainRitualBlockEntity.this.maxProgress = value; break;
                }
            }

            public int getCount() {
                return 1;
            }
        };
    }


    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("rain_ritual_block.progress", progress);
        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        progress = pTag.getInt("rain_ritual_block.progress");
    }


    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, RainRitualBlockEntity pBlockEntity) {

    }
}
