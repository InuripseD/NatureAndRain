package fr.inuripse.naturerain.item.grouptab;

import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModGroupTab {

    public static final CreativeModeTab NATURERAIN_TAB = new CreativeModeTab("naturerain_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.ZIRMS.get());
        }
    };

}
