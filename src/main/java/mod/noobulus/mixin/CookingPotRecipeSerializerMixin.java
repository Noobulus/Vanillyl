package mod.noobulus.mixin;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipe;
import com.nhoryzon.mc.farmersdelight.recipe.CookingPotRecipeSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = CookingPotRecipeSerializer.class, priority = 800)
public class CookingPotRecipeSerializerMixin {

    @Inject(method = "read(Lnet/minecraft/util/Identifier;Lcom/google/gson/JsonObject;)Lcom/nhoryzon/mc/farmersdelight/recipe/CookingPotRecipe;", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void cookingRecipesReadData(Identifier id, JsonObject json, CallbackInfoReturnable<CookingPotRecipe> cir, String groupIn, DefaultedList<Ingredient> inputItemsIn, JsonObject jsonResult, ItemStack outputIn, ItemStack container, float experienceIn, int cookTimeIn) {
        if (JsonHelper.hasElement(jsonResult, "data")) {
            JsonObject data = JsonHelper.getObject(jsonResult, "data");
            NbtCompound nbt = (NbtCompound) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, data);
            outputIn.setNbt(nbt);
        }
    }
}
