package fr.inuripse.naturerain.item.armor.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fr.inuripse.naturerain.NatureRain;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SnailShellChestplateModel<T extends LivingEntity> extends HumanoidModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NatureRain.MOD_ID, "snail_shell_chestplate"), "main");
	private final ModelPart VoxelShapes;

	public SnailShellChestplateModel(ModelPart root) {
		super(root);
		this.VoxelShapes = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition VoxelShapes = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -23.0F, 2.0F, 8.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(28, 2).addBox(-4.5F, -22.0F, 3.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(3.5F, -22.0F, 3.0F, 1.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 22).addBox(-3.0F, -22.0F, 8.0F, 6.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -24.0F, 3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -24.0F, 3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(18, 16).addBox(2.0F, -24.0F, -3.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(-6.0F, -24.0F, -3.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-6.0F, -24.0F, -3.0F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(18, 16).addBox(2.0F, -12.0F, -3.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(16, 0).addBox(-6.0F, -12.0F, -3.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.0F, -13.0F, 3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(2.0F, -13.0F, 3.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		VoxelShapes.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}