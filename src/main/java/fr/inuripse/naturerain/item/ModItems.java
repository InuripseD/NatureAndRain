package fr.inuripse.naturerain.item;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.item.grouptab.ModGroupTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    //Register items.
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, NatureRain.MOD_ID);

    /*------------------- Simple Items ----------------*/
    public static final RegistryObject<Item> ZIRMS = ITEMS.register("zirms",
            () -> new Item(new Item.Properties().tab(ModGroupTab.NATURERAIN_TAB)));
    
    public static final RegistryObject<Item> WET_BRANCH = ITEMS.register("wet_branch",
            () -> new Item(new Item.Properties().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS = ITEMS.register("leafy_zirms",
            () -> new Item(new Item.Properties().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAF = ITEMS.register("leaf",
            () -> new Item(new Item.Properties().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> WET_LEAF = ITEMS.register("wet_leaf",
            () -> new Item(new Item.Properties().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> SOFTENED_HONEYCOMB = ITEMS.register("softened_honeycomb",
            () -> new Item(new Item.Properties().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> SOFTENED_SLIMEBALL = ITEMS.register("softened_slimeball",
            () -> new Item(new Item.Properties().tab(ModGroupTab.NATURERAIN_TAB)));

    //Register items.
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
