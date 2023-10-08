package mod.noobulus.mixin;

import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = ToolMaterials.class, priority = 800)
public class MixinToolMaterials {
    /* <init> params
    0 ??? string
    1 ??? i
    2 int miningLevel
    3 int itemDurability
    4 float miningSpeed
    5 float attackDamage
    6 int enchantability
    7 Lazy<Ingredient> repairIngredient
     */

    /* <clinit> ordinals
    0 WOOD
    1 STONE
    2 IRON
    3 DIAMOND
    4 GOLD
    5 NETHERITE
     */

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ToolMaterials.<init> (Ljava/lang/String;IIIFFILjava/util/function/Supplier;)V", ordinal = 0))
    private static void buffWood(Args args) {
        args.set(4, 3.0F); // mining speed
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ToolMaterials.<init> (Ljava/lang/String;IIIFFILjava/util/function/Supplier;)V", ordinal = 1))
    private static void buffStone(Args args) {
        args.set(4, 5.0F);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ToolMaterials.<init> (Ljava/lang/String;IIIFFILjava/util/function/Supplier;)V", ordinal = 2))
    private static void buffIron(Args args) {
        args.set(4, 12.0F);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ToolMaterials.<init> (Ljava/lang/String;IIIFFILjava/util/function/Supplier;)V", ordinal = 3))
    private static void buffDiamond(Args args) {
        args.set(2, 4); // netherite mining level
        args.set(4, 18.0F); // 18 mining speed
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ToolMaterials.<init> (Ljava/lang/String;IIIFFILjava/util/function/Supplier;)V", ordinal = 4))
    private static void buffGold(Args args) {
        args.set(2, 2); // iron mining level
        args.set(4, 24.0F);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ToolMaterials.<init> (Ljava/lang/String;IIIFFILjava/util/function/Supplier;)V", ordinal = 5))
    private static void nerfNetherite(Args args) {
        args.set(2, 2); // iron mining level
        args.set(4, 8.0F);
    }
}
