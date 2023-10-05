package mod.noobulus.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RabbitEntity.class)
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
        return super.computeFallDamage(fallDistance, damageMultiplier) - 5;
    }

    @Inject(method = "Lnet/minecraft/entity/passive/RabbitEntity;computeFallDamage(FF)I", at = @At("RETURN"), cancellable = true)
    private void injectFallResist(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() - 5);
    }

    @Inject(method = "initGoals()V", at = @At("TAIL"))
    private void fleeFromFoxesAndCats(CallbackInfo ci) {
        ((RabbitEntity) (Object) this).goalSelector.add(4, new RabbitEntity.FleeGoal<>(((RabbitEntity) (Object) this), FoxEntity.class, 10.0F, 3.3, 3.3));
        ((RabbitEntity) (Object) this).goalSelector.add(4, new RabbitEntity.FleeGoal<>(((RabbitEntity) (Object) this), CatEntity.class, 10.0F, 3.3, 3.3));
    }
}
