package fr.inuripse.naturerain.entity.projectile.wetprojectile.render;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.SoftenedSlimeballEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.model.SoftenedSlimeballModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SoftenedSlimeballRenderer extends WetProjectileRenderer<SoftenedSlimeballEntity> {

    private static final ResourceLocation WET_PROJECTILE_TEXTURE_LOCATION =
            new ResourceLocation(NatureRain.MOD_ID,"textures/entity/softened_slimeball.png");

    public SoftenedSlimeballRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new SoftenedSlimeballModel(context.bakeLayer(SoftenedSlimeballModel.MODEL_LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(SoftenedSlimeballEntity pEntity) {
        return WET_PROJECTILE_TEXTURE_LOCATION;
    }
}
