package fr.inuripse.naturerain.entity.model;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.LittleSnailEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LittleSnailModel extends AnimatedGeoModel<LittleSnailEntity> {

    @Override
    public ResourceLocation getModelLocation(LittleSnailEntity object) {
        return new ResourceLocation(NatureRain.MOD_ID, "geo/little_snail.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(LittleSnailEntity object) {
        return new ResourceLocation(NatureRain.MOD_ID, "textures/entity/little_snail.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(LittleSnailEntity animatable) {
        return new ResourceLocation(NatureRain.MOD_ID, "animations/little_snail.animation.json");
    }
}
