package mod.noobulus.mixin;

import net.minecraft.entity.passive.AbstractHorseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = AbstractHorseEntity.class, priority = 800)
public class AbstractHorseEntityMixin {

    @ModifyConstant(method = "getChildHealthBonus(Ljava/util/function/IntUnaryOperator;)F", constant = @Constant(floatValue = 15.0F))
    private static float buffHorseBaseHealth(float constant) {
        return 23.0F;
    }

    @ModifyConstant(method = "getChildHealthBonus(Ljava/util/function/IntUnaryOperator;)F", constant = @Constant(intValue = 8))
    private static int tweakHorseHealthStatScalingPartOne(int constant) {
        return 3;
    }

    @ModifyConstant(method = "getChildHealthBonus(Ljava/util/function/IntUnaryOperator;)F", constant = @Constant(intValue = 9))
    private static int tweakHorseHealthStatScalingPartTwo(int constant) {
        return 4;
    }

    @ModifyConstant(method = "getChildJumpStrengthBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.4000000059604645))
    private static double buffHorseBaseJumpHeight(double constant) {
        return 0.8;
    }

    @ModifyConstant(method = "getChildJumpStrengthBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.2))
    private static double tweakHorseJumpHeightStatScaling(double constant) {
        return 0.1;
    }

    @ModifyConstant(method = "getChildMovementSpeedBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.44999998807907104))
    private static double buffHorseBaseSpeed(double constant) {
        return 0.9;
    }

    @ModifyConstant(method = "getChildMovementSpeedBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.3))
    private static double tweakHorseSpeedStatScaling(double constant) {
        return 0.15;
    }
}
