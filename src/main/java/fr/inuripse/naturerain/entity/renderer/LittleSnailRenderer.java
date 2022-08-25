package fr.inuripse.naturerain.entity.renderer;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.LittleSnailEntity;
import fr.inuripse.naturerain.entity.model.LittleSnailModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class LittleSnailRenderer<T extends LittleSnailEntity> extends MobRenderer<T, LittleSnailModel<T>> {

    private static final ResourceLocation SNAIL_TEXTURE = new ResourceLocation(NatureRain.MOD_ID, "textures/entity/little_snail.png");

    public LittleSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new LittleSnailModel<>(context.bakeLayer(LittleSnailModel.LAYER_LOCATION)), 0.05F);
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return SNAIL_TEXTURE;
    }
}
