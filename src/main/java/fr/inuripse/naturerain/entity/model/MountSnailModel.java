package fr.inuripse.naturerain.entity.model;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.MountSnailEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MountSnailModel extends AnimatedGeoModel<MountSnailEntity> {

    @Override
    public ResourceLocation getModelLocation(MountSnailEntity object) {
        return new ResourceLocation(NatureRain.MOD_ID, "geo/mount_snail.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MountSnailEntity object) {
        return new ResourceLocation(NatureRain.MOD_ID, "textures/entity/mount_snail.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MountSnailEntity animatable) {
        return new ResourceLocation(NatureRain.MOD_ID, "animations/mount_snail.animation.json");
    }
}
