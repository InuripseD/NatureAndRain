package fr.inuripse.naturerain.block.recipe.integration;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.recipe.RainRitualRecipe;
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

import javax.annotation.Nonnull;

public class RainRitualRecipeCategory implements IRecipeCategory<RainRitualRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(NatureRain.MOD_ID, "rain_ritual");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(NatureRain.MOD_ID, "textures/gui/raindrop_catcher_gui_jei.png");

    private final IDrawable background;
    private final IDrawable icon;

    public RainRitualRecipeCategory(IGuiHelper helper) {
        this.background = helper.drawableBuilder(TEXTURE, 0, 0, 1920, 1001).setTextureSize(1920,1001).build();
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(ModBlocks.RAIN_RITUAL_BLOCK.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends RainRitualRecipe> getRecipeClass() {
        return RainRitualRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TextComponent("Rain Ritual");
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
    public void setRecipe(@Nonnull IRecipeLayoutBuilder builder, @Nonnull RainRitualRecipe recipe, @Nonnull IFocusGroup focusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 860, 30).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 300, 300).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 1450, 300).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 860, 800).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 860, 400).addItemStack(recipe.getResultItem());
    }
}
