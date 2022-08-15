package fr.inuripse.naturerain.item.tiers;

import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier LEAFY_ZIRMS =
            new ForgeTier(4,2031,9.0F,4.0F,16,
                    BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ModItems.LEAFY_ZIRMS.get()));
}
