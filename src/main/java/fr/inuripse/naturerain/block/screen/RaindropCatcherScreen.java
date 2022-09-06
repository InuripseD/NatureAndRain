package fr.inuripse.naturerain.block.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.inuripse.naturerain.NatureRain;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static fr.inuripse.naturerain.block.custom.RaindropCatcher.UNDER_RAIN;

public class RaindropCatcherScreen extends AbstractContainerScreen<RaindropCatcherMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(NatureRain.MOD_ID,"textures/gui/raindrop_catcher_gui.png");

    public RaindropCatcherScreen(RaindropCatcherMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

        if(menu.blockEntity.getBlockState().getValue(UNDER_RAIN)){
            this.blit(pPoseStack, x+82, y+18, 176, 0, 13, 17);
        }

        if(menu.isCrafting()){
            this.blit(pPoseStack, x + 4, y + 20,176, 77,76, menu.getScaledProgress());
            this.blit(pPoseStack, x + 97, y + 20,176,17,76, menu.getScaledProgress());
        }

    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
