package mod.noobulus.mixin;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.EnumMap;

@Mixin(value = ArmorItem.class, priority = 800)
public class ArmorItemMixin {

    @Mutable @Final @Shadow
    protected final ArmorItem.Type type;
    @Mutable @Final @Shadow
    protected final float knockbackResistance;

    public ArmorItemMixin(ArmorItem.Type type, float knockbackResistance) {
        this.type = type;
        this.knockbackResistance = knockbackResistance;
    }
    @Unique
    private static final EnumMap<ArmorItem.Type, Float> STAT_COEFFICIENTS = (EnumMap) Util.make(new EnumMap(ArmorItem.Type.class), (map) -> {
        map.put(ArmorItem.Type.BOOTS, 0.6F);
        map.put(ArmorItem.Type.LEGGINGS, 1.2F);
        map.put(ArmorItem.Type.CHESTPLATE, 1.6F);
        map.put(ArmorItem.Type.HELMET, 0.6F);
    });

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorMaterial;getToughness()F"))
    private float modifyToughness(ArmorMaterial instance) {
        return instance.getToughness() * STAT_COEFFICIENTS.get(type);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ArmorMaterial;getKnockbackResistance()F"))
    private float modifyKnockbackResistance(ArmorMaterial instance) {
        return instance.getKnockbackResistance() * STAT_COEFFICIENTS.get(type);
    }
}
