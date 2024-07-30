package mod.noobulus.mixin;

import mod.noobulus.VanillylTags;
import mod.noobulus.util.RabbitEntityTrust;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(value = RabbitEntity.class, priority = 800)
public abstract class RabbitEntityMixin extends AnimalEntity implements RabbitEntityTrust {

    @Unique
    private static TrackedData<Boolean> TRUSTING;
    @Nullable @Unique
    private FleeEntityGoal fleeGoal;
    @Unique
    private static Predicate<Entity> NOTICEABLE_PLAYER_FILTER;

    protected RabbitEntityMixin(EntityType<? extends RabbitEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    protected void updateFleeing() {
        if (this.fleeGoal == null) {
            // this.fleeGoal = new RabbitEntity.FleeGoal((RabbitEntity) (Object) this, PlayerEntity.class, 8.0F, 2.2, 2.2);
            // fox-style flee goal so that rabbits won't run from sneaking players
            this.fleeGoal = new FleeEntityGoal((RabbitEntity) (Object) this, PlayerEntity.class, 8.0F, 2.2, 2.2, (entity) -> {
                return NOTICEABLE_PLAYER_FILTER.test((Entity) entity) && !this.isTrusting();
            });
        }

        ((RabbitEntity) (Object) this).goalSelector.remove(this.fleeGoal);
        if (!this.isTrusting()) {
            ((RabbitEntity) (Object) this).goalSelector.add(4, this.fleeGoal);
        }
    }

    @Unique
    public boolean isTrusting() {
        return (Boolean) ((RabbitEntity) (Object) this).getDataTracker().get(TRUSTING);
    }

    @Unique
    public boolean vanillyl$isTrusting() {
        return isTrusting();
    }

    @Unique
    public final void setTrusting(boolean trusting) {
        ((RabbitEntity) (Object) this).getDataTracker().set(TRUSTING, trusting);
        this.updateFleeing();
    }

    @Unique
    public void vanillyl$setTrusting(boolean trusting) {
        setTrusting(trusting);
    }

    @Inject(method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void addTrustingToNBT(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("Trusting", this.isTrusting());
    }

    @Inject(method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    private void readTrustingFromNBT(NbtCompound nbt, CallbackInfo ci) {
        this.setTrusting(nbt.getBoolean("Trusting"));
    }

    @Inject(method = "initDataTracker()V", at = @At("TAIL"))
    private void addTrustingToDataTracker(CallbackInfo ci) {
        ((RabbitEntity) (Object) this).getDataTracker().startTracking(TRUSTING, false);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void updateFleeingInConstructor(EntityType entityType, World world, CallbackInfo ci) {
        this.updateFleeing();
    }

    /*@Inject(method = "initGoals()V", at = @At("TAIL"))
    private void removeDefaultPlayerFleeGoal(CallbackInfo ci) {
        ((RabbitEntity) (Object) this).goalSelector.remove(new RabbitEntity.FleeGoal(((RabbitEntity) (Object) this), PlayerEntity.class, 8.0F, 2.2, 2.2));
    }*/

    /*@ModifyArg(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 5), index = 1)
    private Goal replaceDefaultPlayerFleeGoal(Goal goal) {
        return fleeGoal;
    }*/

    @Redirect(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 5))
    private void sendDefaultPlayerFleeGoalToGBJ(GoalSelector instance, int priority, Goal goal) {
        // kid named noop
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void registerTrustingTracking(CallbackInfo ci) {
        TRUSTING = DataTracker.registerData(RabbitEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    @Unique
    private void showEmoteParticle(boolean positive) {
        ParticleEffect particleEffect = ParticleTypes.HEART;
        if (!positive) {
            particleEffect = ParticleTypes.SMOKE;
        }

        for(int i = 0; i < 7; ++i) {
            double d = this.random.nextGaussian() * 0.02;
            double e = this.random.nextGaussian() * 0.02;
            double f = this.random.nextGaussian() * 0.02;
            this.getWorld().addParticle(particleEffect, this.getParticleX(1.0), this.getRandomBodyY() + 0.5, this.getParticleZ(1.0), d, e, f);
        }
    }

    @Intrinsic @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        return super.interactMob(player, hand);
    }

    @Inject(method = "Lnet/minecraft/entity/passive/RabbitEntity;interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At("HEAD"), cancellable = true)
    private void injectTrustOnFeeding(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (((RabbitEntity) (Object) this).isBreedingItem(itemStack) && !this.isTrusting()) {
            ((RabbitEntity) (Object) this).eat(player, hand, itemStack);
            if (!((RabbitEntity) (Object) this).getWorld().isClient) {
                this.setTrusting(true);
                this.showEmoteParticle(true);
                ((RabbitEntity) (Object) this).getWorld().sendEntityStatus(this, (byte)41);
            }

            cir.setReturnValue(ActionResult.success(((RabbitEntity) (Object) this).getWorld().isClient));
        }
    }

    @Inject(method = "handleStatus", at = @At("HEAD"))
    private void handleTrustStatus(byte status, CallbackInfo ci) {
        if (status == 41) {
            this.showEmoteParticle(true);
        }
    }

    @Inject(method = "createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/RabbitEntity;", at = @At(value = "TAIL"), cancellable = true)
    void babyRabbitsTrustPlayers(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<RabbitEntity> cir) {
        RabbitEntity baby = EntityType.RABBIT.create(serverWorld);
        if (baby != null && ((RabbitEntityTrust) passiveEntity).vanillyl$isTrusting() && ((RabbitEntityTrust) this).vanillyl$isTrusting()) { // baby is real, both parents trust player
            ((RabbitEntityTrust) baby).vanillyl$setTrusting(true);
        }
        cir.setReturnValue(baby);
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectSneakPredicate(CallbackInfo ci) {
        NOTICEABLE_PLAYER_FILTER = (entity) -> !entity.isSneaky() && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(entity);
    }

    @ModifyConstant(method = "createRabbitAttributes()Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", constant = @Constant(doubleValue = 3.0, ordinal = 0))
    private static double buffRabbitHealth(double health) {
        return 6.0;
    }

    /*@ModifyConstant(method = "initGoals()V", constant = @Constant(floatValue = 8.0F, ordinal = 0))
    private float braverRabbits(float playerFleeDistance) {
        return 4.0F;
    }*/

    @ModifyConstant(method = "initGoals()V", constant = @Constant(doubleValue = 2.2, ordinal = 0))
    private double rabbitsEscapeDangerFaster(double constant) {
        return 3.3;
    }

    @Intrinsic @Override
    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        return super.computeFallDamage(fallDistance, damageMultiplier);
    }

    @Inject(method = "Lnet/minecraft/entity/passive/RabbitEntity;computeFallDamage(FF)I", at = @At("RETURN"), cancellable = true)
    private void injectFallResist(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() - 5);
    }

    @Inject(method = "initGoals()V", at = @At("TAIL"))
    private void fleeFromFoxesAndCatsAndTemptWithCarrotOnAStick(CallbackInfo ci) {
        ((RabbitEntity) (Object) this).goalSelector.add(4, new RabbitEntity.FleeGoal<>(((RabbitEntity) (Object) this), FoxEntity.class, 10.0F, 3.3, 3.3));
        ((RabbitEntity) (Object) this).goalSelector.add(4, new RabbitEntity.FleeGoal<>(((RabbitEntity) (Object) this), CatEntity.class, 10.0F, 3.3, 3.3));
    }

    @Inject(method = "isBreedingItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
    public void rabbitsBredWithTag(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.isIn(VanillylTags.RABBIT_BREEDING_ITEMS));
    }

    @ModifyArg(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 4), index = 1)
    private Goal rabbitsTemptedByTag(Goal goal) {
        return new TemptGoal(this, 1.0, Ingredient.fromTag(VanillylTags.RABBIT_BREEDING_ITEMS), false);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void buffStepHeight(EntityType entityType, World world, CallbackInfo ci) {
        ((RabbitEntity) (Object) this).setStepHeight(1.0F);
    }

    /*@Mixin(targets = "net.minecraft.entity.passive.RabbitEntity$FleeGoal", priority = 800)
    public abstract static class RabbitEntityFleeGoalMixin extends FleeEntityGoal {

        @Shadow @Final public RabbitEntity rabbit;

        protected RabbitEntityFleeGoalMixin(PathAwareEntity mob, Class fleeFromType, float distance, double slowSpeed, double fastSpeed) {
            super(mob, fleeFromType, distance, slowSpeed, fastSpeed);
        }

        @Inject(method = "canStart()Z", at = @At("RETURN"), cancellable = true)
        private void rabbitsDontFleeWhenTrusting(CallbackInfoReturnable<Boolean> cir) {
            if(cir.getReturnValue()) {
                RabbitEntity rabbitEntity = ((RabbitEntity.FleeGoal) (Object) this).rabbit;
                cir.setReturnValue(!((RabbitEntityTrust) rabbitEntity).vanillyl$isTrusting());
            }
        }

        @Intrinsic @Override
        public boolean shouldContinue() {
            return super.shouldContinue();
        }

        @Inject(method = "shouldContinue()Z", at = @At("RETURN"), cancellable = true)
        private void rabbitsDontContinueFleeingWhenTrusting(CallbackInfoReturnable<Boolean> cir) {
            if(cir.getReturnValue()) {
                RabbitEntity rabbitEntity = ((RabbitEntity.FleeGoal) (Object) this).rabbit;
                cir.setReturnValue(!((RabbitEntityTrust) rabbitEntity).vanillyl$isTrusting());
            }
        }
    }*/
}
