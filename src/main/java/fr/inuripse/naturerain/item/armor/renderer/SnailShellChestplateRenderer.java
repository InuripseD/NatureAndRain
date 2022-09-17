package fr.inuripse.naturerain.item.armor.renderer;

import fr.inuripse.naturerain.item.armor.SnailShellChestplate;
import fr.inuripse.naturerain.item.armor.model.SnailShellChestplateModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class SnailShellChestplateRenderer extends GeoArmorRenderer<SnailShellChestplate> {
    public SnailShellChestplateRenderer() {
        super(new SnailShellChestplateModel());

        this.bodyBone = "armorBody";
    }
}
