package fr.inuripse.naturerain.entity.projectile.wetprojectile.model;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.SoftenedHoneycombEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class SoftenedHoneycombModel extends WetProjectileModel<SoftenedHoneycombEntity> {

    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NatureRain.MOD_ID, "softened_honeycomb.png"), "main");

    public SoftenedHoneycombModel(ModelPart root) {
        super(root);
    }
}