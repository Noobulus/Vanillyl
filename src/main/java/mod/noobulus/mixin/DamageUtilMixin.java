package mod.noobulus.mixin;

import net.minecraft.entity.DamageUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DamageUtil.class, priority = 800)
public class DamageUtilMixin {

    @Unique
    private static final float ARMOR_PERCENT_REDUCTION = 0.025f;
    @Unique
    private static final float TOUGHNESS_FLAT_REDUCTION = 0.5f;

    // i reject your damage calculation and substitute my own
    @Inject(method = "getDamageLeft(FFF)F", at = @At(value = "HEAD"), cancellable = true)
    private static void replaceDamageCalculation(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> cir) {
        float flatReduction = armorToughness * TOUGHNESS_FLAT_REDUCTION;
        float percentReductionCoefficient = 1 - (armor * ARMOR_PERCENT_REDUCTION);
        cir.setReturnValue(Math.max((damage - flatReduction) * percentReductionCoefficient, 0));
        cir.cancel();
    }
}
