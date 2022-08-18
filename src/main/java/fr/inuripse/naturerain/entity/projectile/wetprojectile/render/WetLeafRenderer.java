package fr.inuripse.naturerain.entity.projectile.wetprojectile.render;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.WetLeafEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.model.WetLeafModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class WetLeafRenderer extends WetProjectileRenderer<WetLeafEntity> {

    private static final ResourceLocation WET_PROJECTILE_TEXTURE_LOCATION =
            new ResourceLocation(NatureRain.MOD_ID,"textures/entity/wet_leaf.png");

    public WetLeafRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new WetLeafModel(context.bakeLayer(WetLeafModel.MODEL_LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(WetLeafEntity pEntity) {
        return WET_PROJECTILE_TEXTURE_LOCATION;
    }
}
