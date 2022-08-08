package fr.inuripse.naturerain.enchantment;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, NatureRain.MOD_ID);

    public static final RegistryObject<Enchantment> DEFOLIATE = ENCHANTMENTS.register("defoliate",
            () -> new DefoliateEnchantment(Enchantment.Rarity.UNCOMMON,
                    EnchantmentCategory.create("hoe_type", item -> item instanceof HoeItem),
                    EquipmentSlot.MAINHAND));

    public static void register(IEventBus eventBus){
        ENCHANTMENTS.register(eventBus);
    }

}
