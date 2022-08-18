package fr.inuripse.naturerain.entity.projectile.wetprojectile.render;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.FlowingGlowInkEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.model.FlowingGlowInkModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class FlowingGlowInkRenderer extends WetProjectileRenderer<FlowingGlowInkEntity> {

    private static final ResourceLocation WET_PROJECTILE_TEXTURE_LOCATION =
            new ResourceLocation(NatureRain.MOD_ID,"textures/entity/flowing_glow_ink.png");

    public FlowingGlowInkRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new FlowingGlowInkModel(context.bakeLayer(FlowingGlowInkModel.MODEL_LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(FlowingGlowInkEntity pEntity) {
        return WET_PROJECTILE_TEXTURE_LOCATION;
    }

}
