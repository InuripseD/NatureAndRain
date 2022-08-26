package fr.inuripse.naturerain.block.recipe.integration;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.recipe.RaindropCatcherRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class RainDropCatcherRecipeCategory implements IRecipeCategory<RaindropCatcherRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(NatureRain.MOD_ID, "hydration");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(NatureRain.MOD_ID, "textures/gui/raindrop_catcher_gui_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public RainDropCatcherRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 44, 64).setTextureSize(44,64).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.RAINDROP_CATCHER.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends RaindropCatcherRecipe> getRecipeClass() {
        return RaindropCatcherRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Hydration");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull RaindropCatcherRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 14, 23).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 14, 45).addItemStack(recipe.getResultItem());
    }
}
