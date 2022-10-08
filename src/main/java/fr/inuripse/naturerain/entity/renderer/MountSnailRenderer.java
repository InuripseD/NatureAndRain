package fr.inuripse.naturerain.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.MountSnailEntity;
import fr.inuripse.naturerain.entity.model.MountSnailModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MountSnailRenderer extends GeoEntityRenderer<MountSnailEntity> {

    public MountSnailRenderer(EntityRendererProvider.Context context) {
        super(context, new MountSnailModel());
        this.shadowRadius = 1.0f;
    }

    @Override
    public ResourceLocation getTextureLocation(MountSnailEntity instance) {
        return new ResourceLocation(NatureRain.MOD_ID, "textures/entity/mount_snail.png");
    }

    @Override
    public RenderType getRenderType(MountSnailEntity animatable, float partialTicks, PoseStack stack, @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        //stack.scale();
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }
}
