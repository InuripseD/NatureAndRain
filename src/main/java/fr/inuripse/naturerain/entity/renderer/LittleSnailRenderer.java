package fr.inuripse.naturerain.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.LittleSnailEntity;
import fr.inuripse.naturerain.entity.model.LittleSnailModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LittleSnailRenderer extends GeoEntityRenderer<LittleSnailEntity> {

    public LittleSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new LittleSnailModel());
        this.shadowRadius = 0.1f;
    }

    @Override
    public ResourceLocation getTextureLocation(LittleSnailEntity instance) {
        return new ResourceLocation(NatureRain.MOD_ID, "textures/entity/little_snail.png");
    }

    @Override
    public RenderType getRenderType(LittleSnailEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        //stack.scale();
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
