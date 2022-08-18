package fr.inuripse.naturerain.entity.projectile.wetprojectile.render;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.SoftenedHoneycombEntity;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.model.SoftenedHoneycombModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SoftenedHoneycombRenderer extends WetProjectileRenderer<SoftenedHoneycombEntity> {

   private static final ResourceLocation WET_PROJECTILE_TEXTURE_LOCATION =
           new ResourceLocation(NatureRain.MOD_ID,"textures/entity/softened_honeycomb.png");

   public SoftenedHoneycombRenderer(EntityRendererProvider.Context context) {
           super(context);
           this.model = new SoftenedHoneycombModel(context.bakeLayer(SoftenedHoneycombModel.MODEL_LAYER_LOCATION));
   }

   @Override
   public ResourceLocation getTextureLocation(SoftenedHoneycombEntity pEntity) {
           return WET_PROJECTILE_TEXTURE_LOCATION;
   }
}
