package fr.inuripse.naturerain.block.screen;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, NatureRain.MOD_ID);

    public static final RegistryObject<MenuType<RaindropCatcherMenu>> RAINDROP_CATCHER_MENU =
            MENUS.register("raindrop_catcher_menu", () -> IForgeMenuType.create(RaindropCatcherMenu::new));

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }

}
