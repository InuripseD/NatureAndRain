package fr.inuripse.naturerain.block;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.item.ModItems;
import fr.inuripse.naturerain.item.grouptab.ModGroupTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    //Register Blocks.
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NatureRain.MOD_ID);


    /*---------- Simple Block --------------*/

    public static final RegistryObject<Block> ZIRMS_BLOCK = registerBlock("zirms_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)), ModGroupTab.NATURERAIN_TAB);

    public static final RegistryObject<Block> ZIRMS_ORE = registerBlock("zirms_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)), ModGroupTab.NATURERAIN_TAB);

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
        return BLOCKS.register(name, block);
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
