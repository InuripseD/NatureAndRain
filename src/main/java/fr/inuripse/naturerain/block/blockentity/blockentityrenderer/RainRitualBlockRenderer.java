package fr.inuripse.naturerain.block.blockentity.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fr.inuripse.naturerain.block.blockentity.MainPillarBlockEntity;
import fr.inuripse.naturerain.block.custom.RainRitualBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class RainRitualBlockRenderer implements BlockEntityRenderer<MainPillarBlockEntity> {

    public RainRitualBlockRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    public void render(MainPillarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5,0.6249,0.5);
        pPoseStack.scale(0.375f,0.375f,0.375f);
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(90));

        switch (pBlockEntity.getBlockState().getValue(RainRitualBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
            case EAST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(90));
            case SOUTH -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
            case WEST -> pPoseStack.mulPose(Vector3f.ZP.rotationDegrees(270));
        }

        itemRenderer.renderStatic(pBlockEntity.getItem(), ItemTransforms.TransformType.FIXED, pBlockEntity.getLightLevel(), pPackedOverlay, pPoseStack, pBufferSource, 0);
        pPoseStack.popPose();
    }

}
