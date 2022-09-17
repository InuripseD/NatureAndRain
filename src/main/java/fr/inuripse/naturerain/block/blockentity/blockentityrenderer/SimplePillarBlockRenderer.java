package fr.inuripse.naturerain.block.blockentity.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fr.inuripse.naturerain.block.blockentity.SimplePillarBlockEntity;
import fr.inuripse.naturerain.block.custom.SimplePillarBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;

public class SimplePillarBlockRenderer implements BlockEntityRenderer<SimplePillarBlockEntity> {

    public SimplePillarBlockRenderer(BlockEntityRendererProvider.Context pContext) {
    }

    public void render(SimplePillarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5,0.875,0.5);
        pPoseStack.scale(0.375f,0.375f,0.375f);

        switch (pBlockEntity.getBlockState().getValue(SimplePillarBlock.FACING)) {
            case NORTH -> pPoseStack.mulPose(Vector3f.YN.rotationDegrees(0));
            case EAST -> pPoseStack.mulPose(Vector3f.YN.rotationDegrees(90));
            case SOUTH -> pPoseStack.mulPose(Vector3f.YN.rotationDegrees(180));
            case WEST -> pPoseStack.mulPose(Vector3f.YN.rotationDegrees(270));
        }

        itemRenderer.renderStatic(pBlockEntity.getItem(), ItemTransforms.TransformType.FIXED, pBlockEntity.getLightLevel(), pPackedOverlay, pPoseStack, pBufferSource, 0);
        pPoseStack.popPose();
    }

}
