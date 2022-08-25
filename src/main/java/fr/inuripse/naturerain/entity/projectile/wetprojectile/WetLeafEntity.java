package fr.inuripse.naturerain.entity.projectile.wetprojectile;

import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WetLeafEntity extends WetProjectile{

    public static final float WET_LEAF_DAMAGE = 8.0F;

    public WetLeafEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public WetLeafEntity(Level level, Player pPlayer) {
        this(ModEntityTypes.WET_LEAF.get(), level);
        super.setOwner(pPlayer);
        this.setPos(pPlayer.getX(), pPlayer.getEyeY() - (double)0.1F, pPlayer.getZ());
    }

    @Override
    public BlockState getBlockForScratch() {
        return ModBlocks.WET_LEAVES_CARPET.get().defaultBlockState();
    }

    @Override
    public float getDamageToDeal() {
        return WET_LEAF_DAMAGE;
    }
}
