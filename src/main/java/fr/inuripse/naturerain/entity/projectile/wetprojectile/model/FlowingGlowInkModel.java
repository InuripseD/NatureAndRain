package fr.inuripse.naturerain.entity.projectile.wetprojectile.model;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.FlowingGlowInkEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class FlowingGlowInkModel extends WetProjectileModel<FlowingGlowInkEntity>{

    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NatureRain.MOD_ID, "flowing_glow_ink.png"), "main");

    public FlowingGlowInkModel(ModelPart root) {
        super(root);
    }

}
