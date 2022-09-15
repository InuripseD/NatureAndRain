package fr.inuripse.naturerain.item.grouptab;

import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModGroupTab {

    public static final CreativeModeTab NATURERAIN_TAB = new CreativeModeTab("naturerain_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.WET_LEAF.get());
        }
    };

}
