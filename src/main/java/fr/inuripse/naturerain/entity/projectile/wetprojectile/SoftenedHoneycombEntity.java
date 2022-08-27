package fr.inuripse.naturerain.entity.projectile.wetprojectile;

import fr.inuripse.naturerain.block.ModBlocks;
import fr.inuripse.naturerain.entity.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import static net.minecraft.world.level.block.MultifaceBlock.getFaceProperty;

public class SoftenedHoneycombEntity extends WetProjectile {

    public static final float SOFTENED_HONEYCOMB_DAMAGE = 14.0F;

    public SoftenedHoneycombEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public SoftenedHoneycombEntity(Level pLevel, Player pPlayer) {
        this(ModEntityTypes.SOFTENED_HONEYCOMB.get(), pLevel);
        super.setOwner(pPlayer);
        this.setPos(pPlayer.getX(), pPlayer.getEyeY() - (double)0.2F, pPlayer.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        if(level.getFluidState(blockPosition()).getType()==Fluids.LAVA){
            HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
            this.onHitBlock(new BlockHitResult(hitresult.getLocation(), Direction.UP, blockPosition(), false));
        }
    }

    @Override
    protected BlockState blockForPlace(BlockPos blockPos) {
        BlockState superState = super.blockForPlace(blockPos);
        if(superState!=null){
            if(level.getBlockState(blockPos)==Blocks.AIR.defaultBlockState() && level.getBlockState(blockPos.below())==Blocks.LAVA.defaultBlockState()){
                if(random.nextInt(100)<60){
                    superState = superState.setValue(getFaceProperty(Direction.DOWN), Boolean.valueOf(true));
                }
            }
        }else {
            if (level.getBlockState(blockPos) == Blocks.AIR.defaultBlockState() && level.getBlockState(blockPos.below()) == Blocks.LAVA.defaultBlockState()) {
                if (random.nextInt(100) < 60) {
                    superState = this.getBlockForScratch().setValue(getFaceProperty(Direction.DOWN), Boolean.valueOf(true));
                }
            }
        }
        return superState;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        if(!level.isClientSide() && pResult.getEntity() instanceof LivingEntity){
            ((LivingEntity)pResult.getEntity()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,1));
        }
    }

    @Override
    public BlockState getBlockForScratch() {
        return ModBlocks.WET_HONEY_PUDDLE.get().defaultBlockState();
    }

    @Override
    public float getDamageToDeal() {
        return SOFTENED_HONEYCOMB_DAMAGE;
    }

}
