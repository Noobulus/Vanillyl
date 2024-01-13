package mod.noobulus.mixin;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.EnumMap;

@Mixin(value = ArmorMaterials.class, priority = 800)
public class ArmorMaterialsMixin {
    /* <init> params
    0 ??? string
    1 ??? i
    2 string name
    3 int durabilityMultiplier
    4 EnumMap protectionAmounts
    5 int enchantability
    6 SoundEvent equipSound
    7 float toughness
    8 float knockbackResistance
    9 Supplier repairIngredientSupplier
     */

    /* <clinit> ordinals
    0 LEATHER
    1 CHAIN
    2 IRON
    3 GOLD
    4 DIAMOND
    5 TURTLE
    6 NETHERITE
     */

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ArmorMaterials.<init> (Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sound/SoundEvent;FFLjava/util/function/Supplier;)V", ordinal = 0))
    private static void buffLeather(Args args) {
        args.set(7, 1.0F); // add toughness
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ArmorMaterials.<init> (Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sound/SoundEvent;FFLjava/util/function/Supplier;)V", ordinal = 1))
    private static void buffChain(Args args) {
        args.set(7, 1.0F);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ArmorMaterials.<init> (Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sound/SoundEvent;FFLjava/util/function/Supplier;)V", ordinal = 2))
    private static void buffIron(Args args) {
        args.set(7, 2.0F);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ArmorMaterials.<init> (Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sound/SoundEvent;FFLjava/util/function/Supplier;)V", ordinal = 3))
    private static void buffGold(Args args) {
        args.set(7, 1.0F);
        args.set(8, 0.1F); // kb resist
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ArmorMaterials.<init> (Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sound/SoundEvent;FFLjava/util/function/Supplier;)V", ordinal = 4))
    private static void buffDiamond(Args args) {
        args.set(7, 3.0F); // same as vanilla netherite
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ArmorMaterials.<init> (Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sound/SoundEvent;FFLjava/util/function/Supplier;)V", ordinal = 5))
    private static void buffTurtle(Args args) {
        args.set(7, 2.0F);
        args.set(4, (EnumMap) Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, 3);
            map.put(ArmorItem.Type.LEGGINGS, 6);
            map.put(ArmorItem.Type.CHESTPLATE, 8);
            map.put(ArmorItem.Type.HELMET, 3);
        })); // diamond protection values
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = "net/minecraft/item/ArmorMaterials.<init> (Ljava/lang/String;ILjava/lang/String;ILjava/util/EnumMap;ILnet/minecraft/sound/SoundEvent;FFLjava/util/function/Supplier;)V", ordinal = 6))
    private static void nerfNetherite(Args args) {
        args.set(7, 2.0F);
        args.set(8, 0.0F);
        args.set(4, (EnumMap) Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
            map.put(ArmorItem.Type.BOOTS, 2);
            map.put(ArmorItem.Type.LEGGINGS, 5);
            map.put(ArmorItem.Type.CHESTPLATE, 6);
            map.put(ArmorItem.Type.HELMET, 2);
        })); // iron protection values
    }
}
