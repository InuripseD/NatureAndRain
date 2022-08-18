package fr.inuripse.naturerain.entity.projectile.wetprojectile.model;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.SoftenedSlimeballEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class SoftenedSlimeballModel extends WetProjectileModel<SoftenedSlimeballEntity>{

    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NatureRain.MOD_ID, "softened_slimeball.png"), "main");

    public SoftenedSlimeballModel(ModelPart root) {
        super(root);
    }
}
