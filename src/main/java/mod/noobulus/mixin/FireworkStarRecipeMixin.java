package mod.noobulus.mixin;

import com.google.common.collect.Maps;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.FireworkStarRecipe;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.HashMap;

@Mixin(value = FireworkStarRecipe.class, priority = 800)
public class FireworkStarRecipeMixin {

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;", ordinal = 0))
    private static Ingredient tweakTypeModifiers(ItemConvertible[] items) {
        return Ingredient.ofItems(new ItemConvertible[]{Items.FIRE_CHARGE, Items.FLINT, Items.GOLD_NUGGET, Items.CARVED_PUMPKIN});
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;", ordinal = 1))
    private static Ingredient tweakTrailModifier(ItemConvertible[] items) {
        return Ingredient.ofItems(new ItemConvertible[]{Items.REDSTONE});
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Ingredient;ofItems([Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/recipe/Ingredient;", ordinal = 2))
    private static Ingredient tweakFlickerModifiers(ItemConvertible[] items) {
        return Ingredient.ofItems(new ItemConvertible[]{Items.AMETHYST_SHARD});
    }

    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;newHashMap()Ljava/util/HashMap;", ordinal = 0))
    private static HashMap<Item, FireworkRocketItem.Type> tweakModiferMap() {
        HashMap<Item, FireworkRocketItem.Type> typeModifiers = Maps.newHashMap();
        typeModifiers.put(Items.FIRE_CHARGE, FireworkRocketItem.Type.LARGE_BALL);
        typeModifiers.put(Items.FLINT, FireworkRocketItem.Type.BURST);
        typeModifiers.put(Items.GOLD_NUGGET, FireworkRocketItem.Type.STAR);
        typeModifiers.put(Items.CARVED_PUMPKIN, FireworkRocketItem.Type.CREEPER);
        return typeModifiers;
    }
}
