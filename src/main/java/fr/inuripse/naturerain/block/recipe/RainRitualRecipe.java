package fr.inuripse.naturerain.block.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.inuripse.naturerain.NatureRain;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RainRitualRecipe implements Recipe<SimpleContainer> {

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;

    public RainRitualRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems){
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        System.out.println("In " + pContainer.getItem(0) + " " + pContainer.getItem(1) + " " + pContainer.getItem(2) + " " + pContainer.getItem(3) + " " + pContainer.getItem(4));
        System.out.println("Needed " + Arrays.toString(recipeItems.get(0).getItems()) + " " + recipeItems.get(1) + " " + recipeItems.get(2) + " " + recipeItems.get(3) + " " + recipeItems.get(4));
        return recipeItems.get(0).test(pContainer.getItem(0)) && checkIngredientsPresence(pContainer);
    }

    /*
     * We check if all items required in the recipe are present in the container.
     */
    private boolean checkIngredientsPresence(SimpleContainer pContainer) {
        boolean eachItemIsPresent = true;

        List<Boolean> itemUse = new ArrayList<>();
        for(int i = 1; i < pContainer.getContainerSize(); i++){
            itemUse.add(false);
        }

        for(int i = 1; i < recipeItems.size(); i++){
            boolean itemInContainer = false;
            int j = 1;
            while(j<pContainer.getContainerSize() && !itemInContainer){
                if(recipeItems.get(i).test(pContainer.getItem(j)) && !itemUse.get(j-1)){
                    itemInContainer = true;
                    itemUse.set(j-1, true);
                }
                j++;
            }
            eachItemIsPresent = eachItemIsPresent && itemInContainer;
        }
        
        return eachItemIsPresent;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<RainRitualRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "rain_ritual";
    }

    public static class Serializer implements RecipeSerializer<RainRitualRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(NatureRain.MOD_ID,"rain_ritual");

        @Override
        public RainRitualRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(5, Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new RainRitualRecipe(id, output, inputs);
        }

        @Override
        public RainRitualRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();
            return new RainRitualRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, RainRitualRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(), false);
        }

        @Override
        public RecipeSerializer<?> setRegistryName(ResourceLocation name) {
            return INSTANCE;
        }

        @Override
        public ResourceLocation getRegistryName() {
            return ID;
        }

        @Override
        public Class<RecipeSerializer<?>> getRegistryType() {
            return Serializer.castClass(RecipeSerializer.class);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }

}
