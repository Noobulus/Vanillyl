package mod.noobulus.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$FuelSlot", priority = 800)
public class BrewingStandScreenHandlerMixin {

    @Redirect(method = "matches(Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean lavaCanBeInserted(ItemStack itemStack, Item item) {
        return itemStack.isOf(Items.LAVA_BUCKET);
    }

    @Inject(method = "getMaxItemCount()I", at = @At("HEAD"), cancellable = true)
    void oneItemOnlyPlease(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(1);
        cir.cancel();
    }
}
