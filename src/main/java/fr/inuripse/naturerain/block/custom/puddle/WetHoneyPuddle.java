package fr.inuripse.naturerain.block.custom.puddle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class WetHoneyPuddle extends WetMultifaceBlock {

    public static final BooleanProperty ABOVE_LAVA = BooleanProperty.create("above_lava");

    public WetHoneyPuddle(Properties p_153822_) {
        super(p_153822_);
        this.registerDefaultState(this.defaultBlockState().setValue(ABOVE_LAVA, Boolean.valueOf(false)));
    }

    /*------------ Things to do above lava ----------*/
    @Override
    public boolean isRandomlyTicking(BlockState pState) {
        return hasFace(pState, Direction.DOWN);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRand) {
        if (lavaBelow(pPos, pLevel)) {
            if (pRand.nextInt(30) == 20) {
                if(Math.random()>0.5){
                    pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.2F + pRand.nextFloat(), pRand.nextFloat() * 0.7F + 0.6F, false);

                }else{
                    pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.2F + pRand.nextFloat(), pRand.nextFloat() * 0.7F + 0.6F, false);

                }
                for(int l = 0; l < 8; ++l) {
                    pLevel.addParticle(ParticleTypes.LARGE_SMOKE, (double)pPos.getX()+ Math.random(), (double)pPos.getY() + Math.random(), (double)pPos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if(lavaBelow(pPos, pLevel)){
            if(pRandom.nextInt(2)==1 && pState.getValue(ABOVE_LAVA)){
                pLevel.destroyBlock(pPos, false);
            }else{
                pLevel.setBlock(pPos, pState.setValue(ABOVE_LAVA, Boolean.valueOf(lavaBelow(pPos, pLevel))), 2);
            }
        }
    }

    public static boolean lavaBelow(BlockPos pPos, Level pLevel){
        return pLevel.getFluidState(pPos.below()).getType()==Fluids.LAVA || pLevel.getFluidState(pPos.below()).getType()==Fluids.FLOWING_LAVA;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(ABOVE_LAVA);
    }
    /*-------------------------------------*/


    /*---------- HoneyBlock LIKE ----------*/
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return hasFace(pState, Direction.DOWN) ? DOWN_AABB : Shapes.empty();
    }

    public void fallOn(Level pLevel, BlockState pState, BlockPos pPos, Entity pEntity, float p_153376_) {
        pEntity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
        if (!pLevel.isClientSide) {
            pLevel.broadcastEntityEvent(pEntity, (byte)54);
        }
        if (pEntity.causeFallDamage(p_153376_, 0.2F, DamageSource.FALL)) {
            pEntity.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
        }
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (this.isSlidingDown(pPos, pEntity)) {
            this.doSlideMovement(pEntity);
            this.maybeDoSlideEffects(pLevel, pEntity);
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    private boolean isSlidingDown(BlockPos pPos, Entity pEntity) {
        if (pEntity.isOnGround()) {
            return false;
        }else if (pEntity.getDeltaMovement().y >= -0.08D) {
            return false;
        }else{
            double d0 = Math.abs((double)pPos.getX() - pEntity.getX());
            double d1 = Math.abs((double)pPos.getZ() - pEntity.getZ());
            BlockState blockState = pEntity.level.getBlockState(pPos);
            double xBonus = (((hasFace(blockState, Direction.SOUTH)) || (hasFace(blockState, Direction.NORTH))) ? 0.85 : 0);
            double zBonus = (((hasFace(blockState, Direction.EAST)) || (hasFace(blockState, Direction.WEST))) ? 0.85 : 0);
            return (d0 >= 0.300 - xBonus) && (d0 <= 0.700 + xBonus) && (d1>= 0.300 - zBonus) && (d1<= 0.700 + zBonus) ;
            //return (d0 >= 0.425 - xBonus) && (d0 <= 0.575 + xBonus) && (d1>= 0.425 - zBonus) && (d1<= 0.575 + zBonus) ;
        }
    }

    private void doSlideMovement(Entity pEntity) {
        Vec3 vec3 = pEntity.getDeltaMovement();
        if (vec3.y < -0.13D) {
            double d0 = -0.05D / vec3.y;
            pEntity.setDeltaMovement(new Vec3(vec3.x * d0, -0.05D, vec3.z * d0));
        } else {
            pEntity.setDeltaMovement(new Vec3(vec3.x, -0.05D, vec3.z));
        }
        pEntity.resetFallDistance();
    }

    private void maybeDoSlideEffects(Level pLevel, Entity pEntity) {
        if (doesEntityDoHoneyBlockSlideEffects(pEntity)) {
            if (pLevel.random.nextInt(5) == 0) {
                pEntity.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
            }
            if (!pLevel.isClientSide && pLevel.random.nextInt(5) == 0) {
                pLevel.broadcastEntityEvent(pEntity, (byte)53);
            }
        }
    }

    private static boolean doesEntityDoHoneyBlockSlideEffects(Entity pEntity) {
        return pEntity instanceof LivingEntity || pEntity instanceof AbstractMinecart || pEntity instanceof PrimedTnt || pEntity instanceof Boat;
    }
    /*-------------------------------------*/

}
