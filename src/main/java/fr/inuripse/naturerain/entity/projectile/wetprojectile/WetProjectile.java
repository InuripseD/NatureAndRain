package fr.inuripse.naturerain.entity.projectile.wetprojectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import static fr.inuripse.naturerain.block.custom.puddle.FlowingGlowInkPuddle.WATERLOGGED;
import static net.minecraft.world.level.block.MultifaceBlock.getFaceProperty;

public abstract class WetProjectile extends Projectile {

    public WetProjectile(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 10.0D;
        if (Double.isNaN(d0)) {
            d0 = 1.0D;
        }
        d0 *= 64.0D * getViewScale();
        return pDistance < d0 * d0;
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        boolean flag = false;
        if (hitresult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = ((BlockHitResult) hitresult).getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
                flag = true;
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity) blockentity);
                }

                flag = true;
            }
        }

        if (hitresult.getType() != HitResult.Type.MISS && !flag && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.onHit(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d2 = this.getX() + vec3.x;
        double d0 = this.getY() + vec3.y;
        double d1 = this.getZ() + vec3.z;
        this.updateRotation();
        float f;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.BUBBLE, d2 - vec3.x * 0.25D, d0 - vec3.y * 0.25D, d1 - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }

            f = 0.8F;
        } else {
            f = 0.99F;
        }

        this.setDeltaMovement(vec3.scale((double) f));
        if (!this.isNoGravity()) {
            Vec3 vec31 = this.getDeltaMovement();
            this.setDeltaMovement(vec31.x, vec31.y - (double) this.getGravity(), vec31.z);
        }

        this.setPos(d2, d0, d1);

        for (int j = 0; j < 3; ++j) {
            this.level.addParticle(ParticleTypes.RAIN, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), vec3.x, vec3.y, vec3.z);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), getDamageToDeal());
        this.playSound(SoundEvents.HONEY_BLOCK_FALL, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if(!level.isClientSide()) {
            this.spreadAround(result);
        }
        if(this instanceof FlowingGlowInkEntity){
            this.playSound(SoundEvents.AMETHYST_CLUSTER_BREAK, 0.7F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        }else {
            this.playSound(SoundEvents.HONEY_BLOCK_FALL, 0.7F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        }
        this.discard();
    }

    protected void spreadAround(BlockHitResult result) {
        BlockPos blockPos = result.getBlockPos();
        for(BlockPos pPos : getPosesToPlace(blockPos, result.getDirection())){
            BlockState blockState = level.getBlockState(pPos);
            FluidState fluidState = level.getFluidState(pPos);
            System.out.println(blockState.getMaterial() + " " +pPos.getX() + " " + pPos.getY() + " " +pPos.getZ());
            if((blockState.getMaterial().isReplaceable() || blockState.getMaterial() == Material.AIR) && fluidState.getType()!=Fluids.LAVA && (fluidState.getType()!=Fluids.WATER || this instanceof FlowingGlowInkEntity)) {
                int x = Math.abs(blockPos.getX());
                int y = Math.abs(blockPos.getY());
                int z = Math.abs(blockPos.getZ());
                int d1 = Math.abs(Math.abs(pPos.getX()) - x);
                int d2 = Math.abs(Math.abs(pPos.getY()) - y);
                int d3 = Math.abs(Math.abs(pPos.getZ()) - z);
                int sum = d1 + d2 + d3;
                if (sum < 3){
                    tryToPlaceRelativeBlock(pPos);
                } else if (sum < 5) {
                    if(random.nextInt(100)<60){
                        tryToPlaceRelativeBlock(pPos);
                    }
                } else {
                    if(random.nextInt(100)<20){
                        tryToPlaceRelativeBlock(pPos);
                    }
                }
            }
        }
    }

    private void tryToPlaceRelativeBlock(BlockPos blockPos){
        BlockState blockState = blockForPlace(blockPos);
        if(blockState!=null){
            level.setBlock(blockPos, blockState,11);
        }
    }

    private Iterable<BlockPos> getPosesToPlace(BlockPos blockPos, Direction direction){
        BlockPos bP1 = blockPos.relative(direction).relative(direction);
        BlockPos bP2 = blockPos.relative(direction.getOpposite());
        if(direction==Direction.DOWN || direction==Direction.UP){
            bP1 = bP1.relative(Direction.SOUTH).relative(Direction.SOUTH).relative(Direction.SOUTH);
            bP2 = bP2.relative(Direction.NORTH).relative(Direction.NORTH).relative(Direction.NORTH);
            Direction dir1 = Direction.SOUTH.getClockWise();
            bP1 = bP1.relative(dir1).relative(dir1).relative(dir1);
            bP2 = bP2.relative(dir1.getOpposite()).relative(dir1.getOpposite()).relative(dir1.getOpposite());
        }else{
            Direction dir1 = direction.getClockWise();
            bP1 = bP1.relative(dir1).relative(dir1).relative(dir1);
            bP2 = bP2.relative(dir1.getOpposite()).relative(dir1.getOpposite()).relative(dir1.getOpposite());
            bP1 = bP1.relative(Direction.UP).relative(Direction.UP).relative(Direction.UP);
            bP2 = bP2.relative(Direction.DOWN).relative(Direction.DOWN).relative(Direction.DOWN);
        }
        return BlockPos.betweenClosed(bP1,bP2);
    }

    protected BlockState blockForPlace(BlockPos blockPos){
        BlockState blockState = getBlockForScratch();
        boolean atLeastOneFace = false;
        for(Direction direction : Direction.values()){
            BlockPos blockPosTest = blockPos.relative(direction);
            BlockState blockStateTest = level.getBlockState(blockPosTest);
            boolean fullFace = Block.isFaceFull(blockStateTest.getCollisionShape(level, blockPosTest), direction);
            if(fullFace && !(blockStateTest.getBlock() instanceof MultifaceBlock)){
                if(random.nextInt(100)<60){
                    blockState = blockState.setValue(getFaceProperty(direction),Boolean.valueOf(true));
                    atLeastOneFace = true;
                    if(level.getFluidState(blockPos).getType()==Fluids.WATER && this instanceof FlowingGlowInkEntity){
                        blockState = blockState.setValue(WATERLOGGED, Boolean.valueOf(true));
                    }
                }
            }
        }
        return atLeastOneFace ? blockState : null;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected float getGravity() {
        return 0.0185F;
    }

    public abstract BlockState getBlockForScratch();

    public abstract float getDamageToDeal();

}
