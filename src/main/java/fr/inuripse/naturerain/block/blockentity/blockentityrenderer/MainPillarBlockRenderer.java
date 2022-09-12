package fr.inuripse.naturerain.block.blockentity.blockentityrenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.inuripse.naturerain.block.blockentity.MainPillarBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class MainPillarBlockRenderer implements BlockEntityRenderer<MainPillarBlockEntity> {

    public MainPillarBlockRenderer(BlockEntityRendererProvider.Context pContext) {

    }

    public void render(MainPillarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5,0.875,0.5);
        pPoseStack.scale(0.375f,0.375f,0.375f);
        itemRenderer.renderStatic(pBlockEntity.getItem(), ItemTransforms.TransformType.FIXED, getLightLevel(pBlockEntity.getLevel(),
                pBlockEntity.getBlockPos()), pPackedOverlay, pPoseStack, pBufferSource, 0);
        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
