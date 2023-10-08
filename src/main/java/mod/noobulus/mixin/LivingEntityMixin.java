package mod.noobulus.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = LivingEntity.class, priority = 800)
public class LivingEntityMixin {

    @ModifyConstant(method = "getJumpBoostVelocityModifier()F", constant = @Constant(floatValue = 1.0F, ordinal = 0))
    private float buffJumpBoostBaseHeight(float constant) {
        return 0.0F; // this feels dumb but somehow it's right?
    }

    @Redirect(method = "getJumpBoostVelocityModifier()F", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;getAmplifier()I"))
    private int buffJumpBoostScalingHeight(StatusEffectInstance instance) {
        return (instance.getAmplifier() + 1) * 2; // scale twice as fast as usual
    }
}
