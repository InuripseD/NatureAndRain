package fr.inuripse.naturerain.entity.projectile.wetprojectile;

import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.config.NatureRainCommonConfigs;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FlowingGlowInkEntity extends WetProjectile{

    public FlowingGlowInkEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public FlowingGlowInkEntity(Level pLevel, Player pPlayer) {
        this(ModEntityTypes.FLOWING_GLOW_INK.get(), pLevel);
        super.setOwner(pPlayer);
        this.setPos(pPlayer.getX(), pPlayer.getEyeY() - (double)0.2F, pPlayer.getZ());
    }

    @Override
    public BlockState getBlockForScratch() {
        return ModBlocks.FLOWING_GLOW_INK_PUDDLE.get().defaultBlockState();
    }

    @Override
    public float getDamageToDeal() {
        return NatureRainCommonConfigs.FlowingGlowInkDamageWithLauncher.get();
    }
}
