package fr.inuripse.naturerain.block.custom;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class WetHoneyPuddle extends MultifaceBlock {

    private static final VoxelShape UP_AABB = Block.box(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.5D, 16.0D);
    private static final VoxelShape WEST_AABB = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
    private static final VoxelShape EAST_AABB = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    private static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
    private static final Map<Direction, VoxelShape> SHAPE_BY_DIRECTIONS = Util.make(Maps.newEnumMap(Direction.class), (p_153923_) -> {
        p_153923_.put(Direction.NORTH, NORTH_AABB);
        p_153923_.put(Direction.EAST, EAST_AABB);
        p_153923_.put(Direction.SOUTH, SOUTH_AABB);
        p_153923_.put(Direction.WEST, WEST_AABB);
        p_153923_.put(Direction.UP, UP_AABB);
        p_153923_.put(Direction.DOWN, DOWN_AABB);
    });

    public WetHoneyPuddle(Properties p_153822_) {
        super(p_153822_);
        this.shapesCache = this.getShapeForEachState(WetHoneyPuddle::calculateMultifaceShape);
    }

    /*---------- HoneyBlock LIKE ----------*/
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return getShape(pState,pLevel,pPos,pContext);
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
            System.out.println("x: "+xBonus+" z: "+zBonus);
            return (d0 >= 0.425 - xBonus) && (d0 <= 0.575 + xBonus) && (d1>= 0.425 - zBonus) && (d1<= 0.575 + zBonus) ;
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

    /*---------- GlowLichen LIKE ----------*/
    private static VoxelShape calculateMultifaceShape(BlockState p_153959_) {
        VoxelShape voxelshape = Shapes.empty();

        for(Direction direction : DIRECTIONS) {
            if (hasFace(p_153959_, direction)) {
                voxelshape = Shapes.or(voxelshape, SHAPE_BY_DIRECTIONS.get(direction));
            }
        }

        return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
    }

    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        return !pUseContext.getItemInHand().is(Items.GLOW_LICHEN) || super.canBeReplaced(pState, pUseContext);
    }

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.getFluidState().isEmpty();
    }
    /*-------------------------------------*/

}
