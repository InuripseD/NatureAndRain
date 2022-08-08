package fr.inuripse.naturerain.block;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.custom.RaindropCatcher;
import fr.inuripse.naturerain.item.ModItems;
import fr.inuripse.naturerain.item.grouptab.ModGroupTab;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;
import java.util.function.Supplier;

public class ModBlocks {

    //Register Blocks.
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NatureRain.MOD_ID);


    /*---------- Simple Block --------------*/

    public static final RegistryObject<Block> ZIRMS_BLOCK = registerBlock("zirms_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)), ModGroupTab.NATURERAIN_TAB);

    /*------------ Ore Blocks ------------*/

    public static final RegistryObject<Block> ZIRMS_ORE = registerBlock("zirms_ore",
            () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE), UniformInt.of(3,7)), ModGroupTab.NATURERAIN_TAB);

    public static final RegistryObject<Block> DEEPSLATE_ZIRMS_ORE = registerBlock("deepslate_zirms_ore",
            () -> new OreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE), UniformInt.of(5,10)), ModGroupTab.NATURERAIN_TAB);

    /*----------- Custom Blocks -----------*/


    public static final RegistryObject<Block> RAINDROP_CATCHER = registerBlock("raindrop_catcher",
            () -> new RaindropCatcher(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()), ModGroupTab.NATURERAIN_TAB);

    public static final RegistryObject<Block> WET_FARMLAND = registerBlockWithoutItem("wet_farmland",
            () -> new FarmBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND)){
                @Override
                public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
                    return (plantable.getPlantType(world, pos) != null);
                }

                @Override
                public boolean isFertile(BlockState state, BlockGetter level, BlockPos pos) {
                    return true;
                }

                @Override
                public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
                    pLevel.setBlock(pPos,pState.setValue(MOISTURE, Integer.valueOf(7)),2);
                }
            });


    /*---------Register Block and linked Item---------*/
    private static <T extends Block> RegistryObject<T> registerBlock(
            String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> blockToReturn = BLOCKS.register(name,block);
        registerBlockItem(name,blockToReturn,tab);
        return blockToReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutItem(
            String name, Supplier<T> block){
        RegistryObject<T> blockToReturn = BLOCKS.register(name,block);
        return blockToReturn;
    }

    private static <T extends Block> void registerBlockItem(
            String name, RegistryObject<T> block, CreativeModeTab tab){
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
    /*-------------------------------------------------*/

}
