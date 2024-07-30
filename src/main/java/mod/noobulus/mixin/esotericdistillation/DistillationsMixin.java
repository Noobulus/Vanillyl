package mod.noobulus.mixin.esotericdistillation;

import com.esodist.distillations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = distillations.class, priority = 800)
public class DistillationsMixin {

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 3600))
    private static int modifyPotionDuration(int constant) {
        return 9600;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 9600))
    private static int modifyLongPotionDuration(int constant) {
        return 14400;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 1800)) // also collides with long regen duration
    private static int modifyStrongPotionDuration(int constant) {
        return 7200;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 900))
    private static int modifyBlindnessDuration(int constant) {
        return 2400;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 4800)) // also hunger
    private static int modifyLongGlowingDuration(int constant) {
        return 9600;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 400))
    private static int modifyStrongWitherPotionDuration(int constant) {
        return 1800;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 800))
    private static int modifyLongWitherPotionDuration(int constant) {
        return 3600;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 500))
    private static int modifyLevitationPotionDuration(int constant) {
        return 1800;
    }
}
