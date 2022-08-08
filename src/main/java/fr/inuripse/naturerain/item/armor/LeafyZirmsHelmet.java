package fr.inuripse.naturerain.item.armor;

import fr.inuripse.naturerain.item.tiers.ModArmorMaterials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class LeafyZirmsHelmet extends ArmorItem {

    public LeafyZirmsHelmet(ArmorMaterial pMaterial, EquipmentSlot pSlot, Properties pProperties) {
        super(pMaterial, pSlot, pProperties);
    }

    public static boolean hasFullLeafyZirmsArmor(Player player){
        return !player.getInventory().getArmor(0).isEmpty() && !player.getInventory().getArmor(1).isEmpty() &&
                !player.getInventory().getArmor(2).isEmpty() && !player.getInventory().getArmor(3).isEmpty() &&
                ((ArmorItem)player.getInventory().getArmor(0).getItem()).getMaterial()== ModArmorMaterials.LEAFY_ZIRMS &&
                ((ArmorItem)player.getInventory().getArmor(1).getItem()).getMaterial()==ModArmorMaterials.LEAFY_ZIRMS &&
                ((ArmorItem)player.getInventory().getArmor(2).getItem()).getMaterial()==ModArmorMaterials.LEAFY_ZIRMS &&
                ((ArmorItem)player.getInventory().getArmor(3).getItem()).getMaterial()==ModArmorMaterials.LEAFY_ZIRMS;
    }

}
