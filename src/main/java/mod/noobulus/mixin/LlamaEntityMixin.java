package mod.noobulus.mixin;

import net.minecraft.entity.passive.LlamaEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LlamaEntity.class, priority = 800)
public class LlamaEntityMixin {

    @ModifyVariable(method = "setStrength(I)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int llamasAlwaysHaveMaxInvSize(int strength) {
        return 5;
    }
}
