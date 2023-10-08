package mod.noobulus.mixin;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AnimalEntity.class, priority = 800)
public class AnimalEntityMixin {

    @Inject(method = "isBreedingItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
    public void animalsBredWithTag(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier("vanillyl", "generic_breeding_items"))));
    }
}
