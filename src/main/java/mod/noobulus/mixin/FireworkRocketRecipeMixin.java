package mod.noobulus.mixin;

import net.minecraft.recipe.FireworkRocketRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = FireworkRocketRecipe.class, priority = 800)
public class FireworkRocketRecipeMixin {

    @ModifyConstant(method = "craft(Lnet/minecraft/inventory/RecipeInputInventory;Lnet/minecraft/registry/DynamicRegistryManager;)Lnet/minecraft/item/ItemStack;", constant = @Constant(intValue = 3, ordinal = 0))
    public int rocketsCraftEight(int constant) {
        return 8;
    }
}
