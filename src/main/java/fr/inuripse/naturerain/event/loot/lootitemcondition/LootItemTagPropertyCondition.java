package fr.inuripse.naturerain.event.loot.lootitemcondition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.Set;

public class LootItemTagPropertyCondition implements LootItemCondition {

    final TagKey<Block> tag;


    public LootItemTagPropertyCondition(TagKey<Block> pTag) {
        this.tag = pTag;
    }
    
    @Override
    public LootItemConditionType getType() {
        return ModLootItemConditions.TAG_PROPERTY.get();

    }

    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.BLOCK_STATE);
    }

    @Override
    public boolean test(LootContext lootContext) {
        BlockState blockstate = lootContext.getParamOrNull(LootContextParams.BLOCK_STATE);
        //System.out.println("It's leave :" + blockstate.is(this.tag));
        return blockstate != null && blockstate.is(this.tag);
    }

    public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemTagPropertyCondition> {
        /**
         * Serialize the value by putting its data into the JsonObject.
         */
        public void serialize(JsonObject pJson, LootItemTagPropertyCondition condition, JsonSerializationContext pSerializationContext) {
            pJson.addProperty("tag", condition.tag.location().toString());
        }

        /**
         * Deserialize a value by reading it from the JsonObject.
         */
        public LootItemTagPropertyCondition deserialize(JsonObject pJson, JsonDeserializationContext pSerializationContext) {
            ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(pJson, "tag"));
            TagKey<Block> blockTag = TagKey.create(Registry.BLOCK_REGISTRY, resourcelocation);
            return new LootItemTagPropertyCondition(blockTag);
        }
    }

}
