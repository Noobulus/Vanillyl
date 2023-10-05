package mod.noobulus.mixin;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ArmorMaterials.class)
public class MixinArmorMaterials {
    @Inject(method = "getProtection(Lnet/minecraft/item/ArmorItem$Type;)I", at = @At("RETURN"), cancellable = true)
    private void modifyProtectionAmount(CallbackInfoReturnable<Integer> cir) {
        if ((ArmorMaterial) this == ArmorMaterials.TURTLE) {
            cir.setReturnValue(cir.getReturnValue() + 1);
        }
    }

    @Inject(method = "getToughness()F", at = @At("RETURN"), cancellable = true)
    private void modifyToughness(CallbackInfoReturnable<Float> cir) {
        ArmorMaterial material = (ArmorMaterial) this;
        if (material == ArmorMaterials.LEATHER || material == ArmorMaterials.CHAIN || material == ArmorMaterials.GOLD) {
            cir.setReturnValue(cir.getReturnValue() + 1);
        } else if (material == ArmorMaterials.IRON || material == ArmorMaterials.TURTLE) {
            cir.setReturnValue(cir.getReturnValue() + 2);
        }
    }

    @Inject(method = "getKnockbackResistance()F", at = @At("RETURN"), cancellable = true)
    private void modifyKnockbackResistance(CallbackInfoReturnable<Float> cir) {
        if ((ArmorMaterial) this == ArmorMaterials.GOLD) {
            cir.setReturnValue(cir.getReturnValue() + 0.15f);
        }
    }
}
