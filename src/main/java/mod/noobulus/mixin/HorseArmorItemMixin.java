package mod.noobulus.mixin;

import mod.noobulus.util.HorseArmorToughness;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = HorseArmorItem.class, priority = 800)
public class HorseArmorItemMixin implements HorseArmorToughness {
    @Unique
    private int toughness;

    public HorseArmorItemMixin(int toughness) {
        this.toughness = toughness;
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void addToughness(int bonus, String name, Item.Settings settings, CallbackInfo ci) {
        switch (name) {
            case "leather":
                this.toughness = 2;
                break;
            case "iron":
                this.toughness = 4;
                break;
            case "gold":
                this.toughness = 4;
                break;
            case "diamond":
                this.toughness = 8;
                break;
            default:
                this.toughness = 0;
        }
    }

    // this probably sucks but i couldn't quite work out how to do it anywhere else
    @Inject(method = "getBonus", at = @At("TAIL"), cancellable = true)
    private void modifyArmorValues(CallbackInfoReturnable<Integer> cir) {
        switch (cir.getReturnValue()) {
            case 3: // leather
                cir.setReturnValue(7);
                break;
            case 5: // iron
                cir.setReturnValue(15);
                break;
            case 7: // gold
                cir.setReturnValue(11);
                break;
            case 11: // diamond
                cir.setReturnValue(20);
                break;
            default:
                cir.setReturnValue(cir.getReturnValue());
                break;
        }
    }

    @Unique
    public int vanillyl$getToughness() {
        return this.toughness;
    }
}