package mod.noobulus.mixin;

import mod.noobulus.VanillylTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RabbitEntity.class, priority = 800)
public abstract class RabbitEntityMixin extends AnimalEntity {

    public RabbitEntityMixin(EntityType<? extends RabbitEntity> entityType, World world) {
        super(entityType, world);
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
    private double fastAsFuck(double constant) {
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
        ((RabbitEntity) (Object) this).goalSelector.add(3, new TemptGoal(this, 1.2, Ingredient.ofItems(new ItemConvertible[]{Items.CARROT_ON_A_STICK}), false));
    }

    @Inject(method = "isBreedingItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
    public void rabbitsBredWithTag(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.isIn(VanillylTags.RABBIT_BREEDING_ITEMS));
    }

    @ModifyArg(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 4), index = 1)
    private Goal rabbitsTemptedByTag(Goal goal) {
        return new TemptGoal(this, 1.25, Ingredient.fromTag(VanillylTags.GENERIC_BREEDING_ITEMS), false);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void buffStepHeight(EntityType entityType, World world, CallbackInfo ci) {
        ((RabbitEntity) (Object) this).setStepHeight(1.0F);
    }
}
