package fr.inuripse.naturerain.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class ModArmorItem extends ArmorItem {

    public ModArmorItem(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    public static boolean hasFullArmorSet(Player player, ArmorMaterial armorMaterial){
        for(ItemStack armorSlot : player.getArmorSlots()){
            if(!armorSlot.isEmpty() && (armorSlot.getItem() instanceof ArmorItem) && ((ArmorItem)armorSlot.getItem()).getMaterial() == armorMaterial){

            }else{
                return false;
            }
        }
        return true;
    }

}
