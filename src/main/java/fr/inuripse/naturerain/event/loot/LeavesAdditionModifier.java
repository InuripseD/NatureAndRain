package fr.inuripse.naturerain.event.loot;


import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LeavesAdditionModifier extends LootModifier {

    private final double prob12;
    private final double prob34;
    private final Item addition1;
    private final Item addition2;
    private final Item addition3;
    private final Item addition4;

    protected LeavesAdditionModifier(LootItemCondition[] conditionsIn, Item addition1, Item addition2, Item addition3, Item addition4, double prob1, double prob2) {
        super(conditionsIn);
        this.addition1 = addition1;
        this.addition2 = addition2;
        this.addition3 = addition3;
        this.addition4 = addition4;
        this.prob12 = prob1;
        this.prob34 = prob2;
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if(context.getRandom().nextFloat() < this.prob12){
            generatedLoot.add(new ItemStack(addition1, context.getRandom().nextInt(1) + 1));
        }
        if(context.getRandom().nextFloat() < this.prob34){
            generatedLoot.add(new ItemStack(addition3,1));
        }

        ServerLevel serverlevel = context.getLevel();
        Vec3 vec3 = context.getParamOrNull(LootContextParams.ORIGIN);
        Biome biome = serverlevel.getBiome(new BlockPos(vec3.x(),vec3.y(),vec3.z())).value();
        boolean goodBiome = biome.getPrecipitation() == Biome.Precipitation.RAIN;

        if(context.getLevel().isRaining() && goodBiome){
            if(context.getRandom().nextFloat() < this.prob12){
                generatedLoot.add(new ItemStack(addition2,context.getRandom().nextInt(1) + 1));
            }

            if(context.getRandom().nextFloat() < this.prob34){
                generatedLoot.add(new ItemStack(addition4,1));
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
            Item addition4 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object,"addition4")));
            double prob12 = GsonHelper.getAsDouble(object,"proba12");
            double prob34 = GsonHelper.getAsDouble(object,"proba34");
            return new LeavesAdditionModifier(ailootcondition, addition1, addition2, addition3, addition4, prob12, prob34);
        }

        @Override
        public JsonObject write(LeavesAdditionModifier instance) {
            JsonObject json = makeConditions(instance.conditions);
            json.addProperty("addition1", ForgeRegistries.ITEMS.getKey(instance.addition1).toString());
            json.addProperty("addition2", ForgeRegistries.ITEMS.getKey(instance.addition2).toString());
            json.addProperty("addition3", ForgeRegistries.ITEMS.getKey(instance.addition3).toString());
            json.addProperty("addition4", ForgeRegistries.ITEMS.getKey(instance.addition4).toString());
            json.addProperty("proba12", instance.prob12);
            json.addProperty("proba34", instance.prob34);
            return json;
        }
    }

}