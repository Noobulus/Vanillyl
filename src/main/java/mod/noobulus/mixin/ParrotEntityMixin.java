package mod.noobulus.mixin;

import mod.noobulus.VanillylTags;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;

@Mixin(value = ParrotEntity.class, priority = 800)
public class ParrotEntityMixin {

    @Redirect(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", ordinal = 0))
    public boolean parrotsTamedWithTag(Set instance, Object o) {
        return ((Item) o).getDefaultStack().isIn(VanillylTags.PARROT_BREEDING_ITEMS);
    }

    @Redirect(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean parrotsDontEatCookies(ItemStack instance, Item item) {
        return false; // dealing Float.MAX_VALUE damage to a random bird when you feed it a cookie is hysterical, but why even have that?
    }
}
