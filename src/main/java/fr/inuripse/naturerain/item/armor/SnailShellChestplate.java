package fr.inuripse.naturerain.item.armor;

import fr.inuripse.naturerain.NatureRain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SnailShellChestplate extends ModArmorItem{
    public SnailShellChestplate(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return new ResourceLocation(NatureRain.MOD_ID,"textures/models/armor/snail_shell_chestplate.png").toString();
        //return null;
    }

}
