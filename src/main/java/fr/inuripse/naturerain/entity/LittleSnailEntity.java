package fr.inuripse.naturerain.entity;

import fr.inuripse.naturerain.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Random;

public class LittleSnailEntity extends Animal implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public LittleSnailEntity(EntityType<? extends LittleSnailEntity> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.03D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D).build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));
    }

    /*-------- To Climb ----------*/
    public Vec3 handleRelativeFrictionAndCalculateMovement(Vec3 p_21075_, float p_21076_) {
        this.moveRelative(this.getFrictionInfluencedSpeed(p_21076_), p_21075_);
        this.setDeltaMovement(this.handleOnClimbable(this.getDeltaMovement()));
        this.move(MoverType.SELF, this.getDeltaMovement());
        Vec3 vec3 = this.getDeltaMovement();
        if ((this.horizontalCollision || this.jumping) && (this.onClimbable() || this.getFeetBlockState().is(Blocks.POWDER_SNOW) && PowderSnowBlock.canEntityWalkOnPowderSnow(this))) {
            vec3 = new Vec3(vec3.x, 0.1D, vec3.z);
        }

        return vec3;
    }

    private Vec3 handleOnClimbable(Vec3 p_21298_) {
        if (this.onClimbable()) {
            this.resetFallDistance();
            float f = 0.15F;
            double d0 = Mth.clamp(p_21298_.x, (double)-0.15F, (double)0.15F);
            double d1 = Mth.clamp(p_21298_.z, (double)-0.15F, (double)0.15F);
            double d2 = Math.max(p_21298_.y, (double)-0.15F);
            /*if (d2 < 0.0D && !this.getFeetBlockState().isScaffolding(this) && this.isSuppressingSlidingDownLadder() && this instanceof Player) {
                d2 = 0.0D;
            }*/

            p_21298_ = new Vec3(d0, d2, d1);
        }

        return p_21298_;
    }

    private float getFrictionInfluencedSpeed(float p_21331_) {
        return this.onGround ? this.getSpeed() * (0.21600002F / (p_21331_ * p_21331_ * p_21331_)) : this.flyingSpeed;
    }
    /*----------------------------*/

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if(!(pSource.getEntity() instanceof Player)){
            return super.hurt(pSource, pAmount);
        }
        ItemStack inHand = ((Player)pSource.getEntity()).getMainHandItem();
        return inHand == ItemStack.EMPTY ? this.isGatherByPlayer((Player)pSource.getEntity(), inHand) : super.hurt(pSource, pAmount);
    }

    private boolean isGatherByPlayer(Player player, ItemStack inHand) {
        if(inHand == ItemStack.EMPTY){
            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.SNAIL_IN_SHELL.get()));
            this.discard();
            this.level.playSound(null, player, SoundEvents.CHICKEN_EGG, SoundSource.PLAYERS, 1.0f,2.6F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.8F);
            return true;
        }
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean isAffectedByFluids() {
        return false;
    }

    @Override
    protected int getExperienceReward(Player pPlayer) {
        return 2;
    }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    @Override
    protected float getJumpPower() {
        return 0.0F;
    }

    @Override
    protected void jumpFromGround() {
    }

    @Override
    protected float getWaterSlowDown() {
        return 0.0f;
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    public float getVoicePitch() {
        return super.getVoicePitch() * 0.1F;
    }

    @Nullable
    public SoundEvent getAmbientSound() {
        return this.random.nextInt(50) != 0 ? null : SoundEvents.BONE_MEAL_USE;
    }

    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.BONE_MEAL_USE;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.BONE_MEAL_USE;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
        this.playSound(SoundEvents.SLIME_BLOCK_PLACE, 0.05f, 1.0f);
    }

    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity pEntity) {
    }

    protected void pushEntities() {
    }

    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    protected float getStandingEyeHeight(Pose pPose, EntityDimensions pSize) {
        return pSize.height / 2.0F;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        /*if(random.nextInt(3)==1){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.move", true));
            return PlayState.CONTINUE;
        }*/
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.model.shake", true));
        return PlayState.CONTINUE;
    }

    public static boolean checkSnailSpawnRules(EntityType<LittleSnailEntity> pSnail, LevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        if (pPos.getY() >= 62 && pPos.getY() <= 120) {
            if(pLevel.getBlockState(pPos.below()) == Blocks.GRASS_BLOCK.defaultBlockState()) {
                //System.out.println("snail spawn at : " + pPos);
                return pLevel.getLevelData().isRaining() && checkMobSpawnRules(pSnail, pLevel, pReason, pPos, pRandom);
            }
        }
        return false;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",0,this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
