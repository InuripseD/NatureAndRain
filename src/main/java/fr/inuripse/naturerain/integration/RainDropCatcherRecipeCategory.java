package fr.inuripse.naturerain.integration;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.recipe.RaindropCatcherRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;

public class RainDropCatcherRecipeCategory implements IRecipeCategory<RaindropCatcherRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(NatureRain.MOD_ID, "hydration");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(NatureRain.MOD_ID, "textures/gui/raindrop_catcher_gui.png");

    private final IDrawable background;
    private final IDrawable icon;

    public RainDropCatcherRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
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
        return new TextComponent("Raindrop Catcher");
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
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 31).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 53).addItemStack(recipe.getResultItem());
    }
}
