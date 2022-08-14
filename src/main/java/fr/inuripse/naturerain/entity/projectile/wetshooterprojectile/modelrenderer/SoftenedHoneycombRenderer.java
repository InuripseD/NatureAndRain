package fr.inuripse.naturerain.entity.projectile.wetshooterprojectile.modelrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetshooterprojectile.SoftenedHoneycombEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class SoftenedHoneycombRenderer extends EntityRenderer<SoftenedHoneycombEntity> {

        private static final ResourceLocation SOFTENED_HONEYCOMB_LOCATION =
                new ResourceLocation(NatureRain.MOD_ID,"textures/entity/softened_honeycomb.png");
        private final SoftenedHoneycombModel<SoftenedHoneycombEntity> model;

        public SoftenedHoneycombRenderer(EntityRendererProvider.Context context) {
                super(context);
                this.shadowRadius = 0.25F;
                this.model = new SoftenedHoneycombModel<>(context.bakeLayer(SoftenedHoneycombModel.SOFTENED_HONEYCOMB));
        }

        @Override
        public void render(SoftenedHoneycombEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
                pMatrixStack.pushPose();
                pMatrixStack.translate(0.0D, (double)0.15F, 0.0D);
                pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
                pMatrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot())));
                this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
                VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(SOFTENED_HONEYCOMB_LOCATION));
                this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
                pMatrixStack.popPose();
                super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        }

        @Override
        public ResourceLocation getTextureLocation(SoftenedHoneycombEntity pEntity) {
                return SOFTENED_HONEYCOMB_LOCATION;
        }
}
