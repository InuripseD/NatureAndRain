package fr.inuripse.naturerain.item.armor.model;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.item.armor.SnailShellChestplate;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SnailShellChestplateModel extends AnimatedGeoModel<SnailShellChestplate> {

	@Override
	public ResourceLocation getModelLocation(SnailShellChestplate object) {
		return new ResourceLocation(NatureRain.MOD_ID, "geo/snail_shell_chestplate.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SnailShellChestplate object) {
		return new ResourceLocation(NatureRain.MOD_ID, "textures/models/armor/snail_shell_chestplate.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SnailShellChestplate animatable) {
		return new ResourceLocation(NatureRain.MOD_ID, "animations/armor_animation.json");
	}
}