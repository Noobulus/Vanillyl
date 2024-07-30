package mod.noobulus.mixin;

import net.minecraft.potion.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = Potions.class, priority = 800)
public class PotionsMixin {

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

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 900)) // also poison!
    private static int modifyRegenDuration(int constant) {
        return 2400;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 450))
    private static int modifyStrongRegenDuration(int constant) {
        return 1800; // coincidentally also the strong potion duration
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 432))
    private static int modifyStrongPoisonDuration(int constant) {
        return 1728; // keeping the wierd multiple of 12 thing
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 4800))
    private static int modifyLongSlownessDuration(int constant) {
        return 9600;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 400)) // slowness too
    private static int modifyTurtleMasterPotionDuration(int constant) {
        return 1800;
    }

    @ModifyConstant(method = "<clinit>", constant = @Constant(intValue = 800))
    private static int modifyLongTurtleMasterPotionDuration(int constant) {
        return 3600;
    }
}
