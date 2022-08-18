package fr.inuripse.naturerain.entity.projectile.wetprojectile;

import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SoftenedHoneycombEntity extends WetProjectile {

    public SoftenedHoneycombEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public SoftenedHoneycombEntity(Level pLevel, Player pPlayer) {
        this(ModEntityTypes.SOFTENED_HONEYCOMB.get(), pLevel);
        super.setOwner(pPlayer);
        this.setPos(pPlayer.getX(), pPlayer.getEyeY() - (double)0.1F, pPlayer.getZ());
    }

    @Override
    public BlockState getBlockForScratch() {
        return ModBlocks.WET_HONEY_PUDDLE.get().defaultBlockState();
    }

}
