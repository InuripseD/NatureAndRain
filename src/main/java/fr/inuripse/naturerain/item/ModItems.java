package fr.inuripse.naturerain.item;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.item.grouptab.ModGroupTab;
import fr.inuripse.naturerain.item.tiers.ModArmorMaterials;
import fr.inuripse.naturerain.item.tiers.ModTiers;
import fr.inuripse.naturerain.item.toolandweapon.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
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


    /*------------------- Tools and Weapons -----------------*/
    public static final RegistryObject<Item> LEAFY_ZIRMS_SWORD = ITEMS.register("leafy_zirms_sword",
            () -> new LeafyZirmsSword(ModTiers.LEAFY_ZIRMS, 2, -2.4F,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS_PICKAXE = ITEMS.register("leafy_zirms_pickaxe",
            () -> new LeafyZirmsPickaxe(ModTiers.LEAFY_ZIRMS, 0, -2.8f,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS_SHOVEL = ITEMS.register("leafy_zirms_shovel",
            () -> new LeafyZirmsShovel(ModTiers.LEAFY_ZIRMS, 1.0f, -3.0f,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS_AXE = ITEMS.register("leafy_zirms_axe",
            () -> new LeafyZirmsAxe(ModTiers.LEAFY_ZIRMS, 4, -3.0f,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS_HOE = ITEMS.register("leafy_zirms_hoe",
            () -> new LeafyZirmsHoe(ModTiers.LEAFY_ZIRMS, -4, 0.0f,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> WET_STUFF_LAUNCHER = ITEMS.register("wet_stuff_launcher",
            () -> new WetStuffLauncher(
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB).defaultDurability(834)));

    /*------------------- Armor and Wearable -----------------*/
    public static final RegistryObject<Item> LEAFY_ZIRMS_BOOTS = ITEMS.register("leafy_zirms_boots",
            () -> new ArmorItem(ModArmorMaterials.LEAFY_ZIRMS, EquipmentSlot.FEET,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS_LEGGINGS = ITEMS.register("leafy_zirms_leggings",
            () -> new ArmorItem(ModArmorMaterials.LEAFY_ZIRMS, EquipmentSlot.LEGS,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS_CHESTPLATE = ITEMS.register("leafy_zirms_chestplate",
            () -> new ArmorItem(ModArmorMaterials.LEAFY_ZIRMS, EquipmentSlot.CHEST,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));

    public static final RegistryObject<Item> LEAFY_ZIRMS_HELMET = ITEMS.register("leafy_zirms_helmet",
            () -> new ArmorItem(ModArmorMaterials.LEAFY_ZIRMS, EquipmentSlot.HEAD,
                    new Item.Properties().fireResistant().tab(ModGroupTab.NATURERAIN_TAB)));


    //Register items.
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
