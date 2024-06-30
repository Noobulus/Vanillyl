package mod.noobulus.mixin;

import mod.noobulus.util.HorseArmorToughness;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(value = HorseEntity.class, priority = 800)
public abstract class HorseEntityMixin extends AbstractHorseEntity {
    private static final UUID HORSE_ARMOR_TOUGHNESS_ID = UUID.fromString("A5F0AF05-54B5-4F9A-8753-8FE9E8179186");

    protected HorseEntityMixin(EntityType<? extends AbstractHorseEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setArmorTypeFromStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/HorseEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;", ordinal = 0))
    private void removeToughnessOnEquip(ItemStack stack, CallbackInfo ci) {
        ((HorseEntity) (Object) this).getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).removeModifier(HORSE_ARMOR_TOUGHNESS_ID);
    }

    @Inject(method = "setArmorTypeFromStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/HorseEntity;getAttributeInstance(Lnet/minecraft/entity/attribute/EntityAttribute;)Lnet/minecraft/entity/attribute/EntityAttributeInstance;", ordinal = 1))
    private void addToughnessOnEquip(ItemStack stack, CallbackInfo ci) {
        int i = ((HorseArmorToughness) stack.getItem()).vanillyl$getToughness();
        ((HorseEntity) (Object) this).getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).addTemporaryModifier(new EntityAttributeModifier(HORSE_ARMOR_TOUGHNESS_ID, "Horse armor toughness", (double)i, EntityAttributeModifier.Operation.ADDITION));
    }
}
