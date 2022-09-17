package fr.inuripse.naturerain.block.blockentity;

import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

import static fr.inuripse.naturerain.block.custom.RaindropCatcher.UNDER_RAIN;

public class RainRitualBlockEntity extends MainPillarBlockEntity{

    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 500;

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

    /*----------------Data Management--------------*/
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
    /*-------------------------------------------------*/


    /*--------------------------Tick Methods-------------------------*/
    public static void animationTick(Level pLevel, BlockPos pPos, BlockState pState, RainRitualBlockEntity pBlockEntity) {
        //if (pBlockEntity.progress > 0){
            int x = pPos.getX();
            int y = pPos.getY();
            int z = pPos.getZ();
            //if ((pBlockEntity.progress % 10) == 1) {
            for (int i = 0; i < 1; i++) {
                pBlockEntity.level.addParticle(ParticleTypes.HAPPY_VILLAGER, (double) x + Math.random(), (double) y + Math.random(), (double) z + Math.random(), 5.0D, -0.5D, 5.0D);
            }
            //}
        //}
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, RainRitualBlockEntity pBlockEntity) {
        if(pState.getValue(UNDER_RAIN)) {
            if(pBlockEntity.getItem().getItem() == Items.BOW) {
                List<SimplePillarBlockEntity> pillars = pillarAround(pBlockEntity);
                boolean recipe = pillars.size() == 4 && hasBowRecipe(pillars);
                if(recipe) {
                    pBlockEntity.progress++;
                    if(pBlockEntity.progress>pBlockEntity.maxProgress) {
                        pBlockEntity.consumeItem();
                        pBlockEntity.placeItem(ModItems.WET_STUFF_LAUNCHER.get().getDefaultInstance());
                        setChanged(pLevel, pPos, pState);
                        pillars.forEach(MainPillarBlockEntity::consumeItem);
                        pBlockEntity.resetProgress();
                    }
                }else{
                    pBlockEntity.resetProgress();
                }
            }else{
                pBlockEntity.resetProgress();
            }
        }else{
            pBlockEntity.resetProgress();
        }

    }
    /*----------------------------------------------------------*/


    /*---------------------Methods for crafting-----------------*/
    private static boolean hasBowRecipe(List<SimplePillarBlockEntity> pillars) {
        return pillars.stream().allMatch(p -> p.getItem().getItem()==ModItems.LEAFY_ZIRMS.get());
    }

    private static List<SimplePillarBlockEntity> pillarAround(RainRitualBlockEntity pBlockEntity){
        Level level = pBlockEntity.level;
        BlockPos start = pBlockEntity.worldPosition.relative(Direction.DOWN);
        List<BlockPos> pillarTest = new ArrayList<>();
        List<SimplePillarBlockEntity> pillarBlockEntities = new ArrayList<>();
        pillarTest.add(start.relative(Direction.NORTH, 3));
        pillarTest.add(start.relative(Direction.EAST, 3));
        pillarTest.add(start.relative(Direction.SOUTH, 3));
        pillarTest.add(start.relative(Direction.WEST, 3));
        for(BlockPos pillarPos : pillarTest){
            if(level.getBlockState(pillarPos).getBlock() == ModBlocks.SIMPLE_PILLAR.get()){
                BlockEntity blockentity = level.getBlockEntity(pillarPos);
                if(blockentity instanceof SimplePillarBlockEntity) {
                    pillarBlockEntities.add(((SimplePillarBlockEntity)blockentity));
                }
            }
        }
        return pillarBlockEntities;
    }

    private void resetProgress() {
        this.progress = 0;
    }
    /*----------------------------------------------------------*/
}
