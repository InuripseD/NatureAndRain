package fr.inuripse.naturerain.entity.projectile.wetprojectile;

import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SoftenedSlimeballEntity extends WetProjectile{

    public SoftenedSlimeballEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public SoftenedSlimeballEntity(Level level, Player pPlayer) {
        this(ModEntityTypes.SOFTENED_SLIMEBALL.get(), level);
        super.setOwner(pPlayer);
        this.setPos(pPlayer.getX(), pPlayer.getEyeY() - (double)0.1F, pPlayer.getZ());
    }

    @Override
    public BlockState getBlockForScratch() {
        return ModBlocks.WET_SLIMEBALL_PUDDLE.get().defaultBlockState();
    }
}
