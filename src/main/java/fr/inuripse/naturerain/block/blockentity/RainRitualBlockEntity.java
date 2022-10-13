package fr.inuripse.naturerain.block.blockentity;

import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.custom.RainRitualBlock;
import fr.inuripse.naturerain.block.recipe.RainRitualRecipe;
import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fr.inuripse.naturerain.block.custom.RaindropCatcher.UNDER_RAIN;
import static fr.inuripse.naturerain.config.NatureRainCommonConfigs.AllowRainRitual;

public class RainRitualBlockEntity extends MainPillarBlockEntity{

    protected final ContainerData data;

    private int progress = 0;
    private int maxProgress = 600;

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

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, RainRitualBlockEntity pBlockEntity) {
        List<SimplePillarBlockEntity> pillars = pillarAround(pBlockEntity);
        if(pState.getValue(UNDER_RAIN) && pillars.size()==4 && hasRecipe(pBlockEntity, pillars)){
            pState = pBlockEntity.getBlockState().setValue(RainRitualBlock.PROCESSING, Boolean.valueOf(true));
            pBlockEntity.progress++;
            if (pBlockEntity.progress > pBlockEntity.maxProgress) {
                bonusItemsConditionsGestation(pBlockEntity, pLevel, pPos);
                craftItem(pBlockEntity, pillars);
                setChanged(pLevel, pPos, pState);
            }
        }else if(AllowRainRitual.get() && !pState.getValue(UNDER_RAIN) && pillars.size() == 4 && hasRainRecipe(pillars) && pBlockEntity.getItem().is(ModItems.ZIRMS.get())){
            pState = pBlockEntity.getBlockState().setValue(RainRitualBlock.PROCESSING, Boolean.valueOf(true));
            pBlockEntity.progress++;
            if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                pBlockEntity.consumeItem();
                pillars.forEach(MainPillarBlockEntity::consumeItem);
                pBlockEntity.resetProgress();
                setChanged(pLevel, pPos, pState);
                if(!pLevel.isClientSide()){
                    ServerLevel serverLevel = (ServerLevel) pLevel;
                    serverLevel.setWeatherParameters(0, 12000, true, false);
                }
            }
        }else {
            pBlockEntity.resetProgress();
            pState = pBlockEntity.getBlockState().setValue(RainRitualBlock.PROCESSING, Boolean.valueOf(false));
            setChanged(pLevel, pPos, pState);
        }
        pLevel.setBlock(pPos, pState, 3);
    }

    /*----------------------------------------------------------*/


    /*---------------------Methods for crafting-----------------*/
    private static boolean hasRecipe(RainRitualBlockEntity entity, List<SimplePillarBlockEntity> pillars) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(5);
        inventory.setItem(0, entity.getItem());

        for (int i = 1; i < pillars.size() + 1; i++) {
            inventory.setItem(i, pillars.get(i - 1).getItem());
        }

        Optional<RainRitualRecipe> match = level.getRecipeManager()
                .getRecipeFor(RainRitualRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }

    private static void craftItem(RainRitualBlockEntity entity, List<SimplePillarBlockEntity> pillars) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(5);
        inventory.setItem(0, entity.getItem());

        for (int i = 1; i < pillars.size() + 1; i++){
            inventory.setItem(i, pillars.get(i-1).getItem());
        }

        Optional<RainRitualRecipe> match = level.getRecipeManager()
                .getRecipeFor(RainRitualRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            entity.consumeItem();
            entity.placeItem(new ItemStack(match.get().getResultItem().getItem()));
            pillars.forEach(MainPillarBlockEntity::consumeItem);
            entity.resetProgress();
        }
    }

    private static void bonusItemsConditionsGestation(RainRitualBlockEntity pBlockEntity, Level pLevel, BlockPos pPos) {
        if(pBlockEntity.getItem().is(ModItems.LITTLE_SNAIL_IN_SHELL.get())){
            for(int i = 0; i < pLevel.random.nextInt(1,4); i++) {
                Containers.dropItemStack(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.75, pPos.getZ() + 0.5, ModItems.SNAIL_SHELL_FRAGMENT.get().getDefaultInstance());
            }
        }
    }

    private static boolean hasRainRecipe(List<SimplePillarBlockEntity> pillars){
        return pillars.stream().allMatch(p -> p.getItem().is(Items.WATER_BUCKET));
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
                if(blockentity instanceof SimplePillarBlockEntity pillarBlockEntity) {
                    pillarBlockEntities.add(pillarBlockEntity);
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
