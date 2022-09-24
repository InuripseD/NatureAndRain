package fr.inuripse.naturerain.item.grouptab;

import fr.inuripse.naturerain.enchantment.ModEnchantments;
import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.jetbrains.annotations.NotNull;

public class ModGroupTab {

    public static final CreativeModeTab NATURERAIN_TAB = new CreativeModeTab("naturerain_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.WET_LEAF.get());
        }

        @Override
        public void fillItemList(NonNullList<ItemStack> pItems) {
            super.fillItemList(pItems);
            pItems.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ModEnchantments.DEFOLIATE.get(), 1)));
        }
    };

}
