package fr.inuripse.naturerain.item.custom;

import fr.inuripse.naturerain.entity.projectile.wetprojectile.WetProjectile;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public abstract class WetItem extends Item {

    public WetItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract WetProjectile getStuffToShoot(Level pLevel, Player pShooter);

}
