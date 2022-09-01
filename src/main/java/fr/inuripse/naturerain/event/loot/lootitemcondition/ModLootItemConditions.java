package fr.inuripse.naturerain.event.loot.lootitemcondition;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModLootItemConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_ITEM_CONDITIONS =
            DeferredRegister.create(Registry.LOOT_CONDITION_TYPE.key(), NatureRain.MOD_ID);

    public static final RegistryObject<LootItemConditionType> TAG_PROPERTY = LOOT_ITEM_CONDITIONS.register("tag",
            ()-> new LootItemConditionType(new LootItemTagPropertyCondition.Serializer()));

    public static void register(IEventBus eventBus){
        LOOT_ITEM_CONDITIONS.register(eventBus);
    }

}
