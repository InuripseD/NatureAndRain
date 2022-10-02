package fr.inuripse.naturerain.item.grouptab;

import fr.inuripse.naturerain.enchantment.ModEnchantments;
import fr.inuripse.naturerain.item.ModItems;
import fr.inuripse.naturerain.potion.ModPotions;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
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
            ItemStack itemStack = new ItemStack(Items.POTION.asItem());
            itemStack.setTag(new CompoundTag());
            PotionUtils.setPotion(itemStack, ModPotions.LAVA_VISION.get());
            pItems.add(itemStack);
            pItems.add(EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ModEnchantments.DEFOLIATE.get(), 1)));
        }
    };

}
