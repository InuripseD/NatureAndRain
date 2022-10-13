package fr.inuripse.naturerain.block.recipe.integration;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.block.recipe.RainRitualRecipe;
import fr.inuripse.naturerain.block.recipe.RaindropCatcherRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEINatureRainPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(NatureRain.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new RainDropCatcherRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RainRitualRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<RaindropCatcherRecipe> raindropCatcherRecipes = rm.getAllRecipesFor(RaindropCatcherRecipe.Type.INSTANCE);
        List<RainRitualRecipe> rainRitualRecipes = rm.getAllRecipesFor(RainRitualRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(RainDropCatcherRecipeCategory.UID, RaindropCatcherRecipe.class), raindropCatcherRecipes);
        registration.addRecipes(new RecipeType<>(RainRitualRecipeCategory.UID, RainRitualRecipe.class), rainRitualRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.RAINDROP_CATCHER.get()),
                RainDropCatcherRecipeCategory.UID);
    }
}
