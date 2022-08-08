package fr.inuripse.naturerain.event.loot;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class LeavesAdditionModifier extends LootModifier {

    private Random rand = new Random();

    private static final int ADD1_CHANCE_FOR_1000 = 100;
    private static final int ADD2_CHANCE_FOR_1000 = 50;
    private static final int ADD3_CHANCE_FOR_1000 = 1;
    private Item addition1;
    private Item addition2;
    private Item addition3;

    protected LeavesAdditionModifier(LootItemCondition[] conditionsIn, Item addition1, Item addition2, Item addition3) {
        super(conditionsIn);
        this.addition1 = addition1;
        this.addition2 = addition2;
        this.addition3 = addition3;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if(context.getLevel().isRaining()){
            if(rand.nextInt(1000)<=ADD3_CHANCE_FOR_1000){
                generatedLoot.add(new ItemStack(addition3,1));
            }
            int test = rand.nextInt(1000);
            if (test<ADD2_CHANCE_FOR_1000) {
                generatedLoot.add(new ItemStack(addition2,1));
            }else if(test<ADD1_CHANCE_FOR_1000){
                generatedLoot.add(new ItemStack(addition1,1));
            }
        }else{
            if(rand.nextInt(1000)<ADD1_CHANCE_FOR_1000){
                generatedLoot.add(new ItemStack(addition1,1));
            }
        }
        return generatedLoot;
    }

    public static class ModGlobalLootModifierSerilizer extends GlobalLootModifierSerializer<LeavesAdditionModifier> {
        @Override
        public LeavesAdditionModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] ailootcondition) {
            Item addition1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object,"addition1")));
            Item addition2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object,"addition2")));
            Item addition3 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object,"addition3")));
            return new LeavesAdditionModifier(ailootcondition, addition1, addition2, addition3);
        }

        @Override
        public JsonObject write(LeavesAdditionModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition1", ForgeRegistries.ITEMS.getKey(instance.addition1).toString());
            json.addProperty("addition2", ForgeRegistries.ITEMS.getKey(instance.addition2).toString());
            json.addProperty("addition3", ForgeRegistries.ITEMS.getKey(instance.addition3).toString());
            return json;
        }

        /*@Override
        public ModTreeLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
            String additionS1 = JSONUtils.getString(object,"addition1");
            Item addition1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(additionS1));
            String additionS2 = JSONUtils.getString(object,"addition2");
            Item addition2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(additionS2));
            String additionS3 = JSONUtils.getString(object,"addition3");
            Item addition3 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(additionS3));
            return new ModTreeLootModifier(ailootcondition, addition1, addition2, addition3);
        }

        @Override
        public JsonObject write(ModTreeLootModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition1", ForgeRegistries.ITEMS.getKey(instance.addition1).toString());
            json.addProperty("addition2", ForgeRegistries.ITEMS.getKey(instance.addition2).toString());
            json.addProperty("addition3", ForgeRegistries.ITEMS.getKey(instance.addition3).toString());
            return json;
        }*/
    }

}
