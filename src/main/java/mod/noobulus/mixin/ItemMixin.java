package mod.noobulus.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Item.class, priority = 800)
public class ItemMixin {

    @Inject(method = "getRecipeRemainder()Lnet/minecraft/item/Item;", at = @At("TAIL"), cancellable = true)
    private void injectBottleRemainder(CallbackInfoReturnable<Item> cir) {
        if (((Item) (Object) this) == Items.POTION) {
            cir.setReturnValue(Items.GLASS_BOTTLE);
        }
    }

    @Inject(method = "hasRecipeRemainder()Z", at = @At("TAIL"), cancellable = true)
    private void injectHasRemainder(CallbackInfoReturnable<Boolean> cir) {
        if (((Item) (Object) this) == Items.POTION) {
            cir.setReturnValue(true);
        }
    }
}
