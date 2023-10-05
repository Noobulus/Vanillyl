package mod.noobulus.mixin;

import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolMaterials.class)
public class MixinToolMaterials {
    @Inject(method = "getMiningSpeedMultiplier()F", at = @At("RETURN"), cancellable = true)
    private void modifyMiningSpeed(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(cir.getReturnValue() + 5.0f); // blanket eff2
    }
}
