package fr.inuripse.naturerain.entity.projectile.wetprojectile.model;

import fr.inuripse.naturerain.NatureRain;
import fr.inuripse.naturerain.entity.projectile.wetprojectile.WetLeafEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

public class WetLeafModel extends WetProjectileModel<WetLeafEntity>{

    public static final ModelLayerLocation MODEL_LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(NatureRain.MOD_ID, "wet_leaf.png"), "main");

    public WetLeafModel(ModelPart root) {
        super(root);
    }
}
