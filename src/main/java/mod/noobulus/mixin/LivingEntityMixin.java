package mod.noobulus.mixin;

import mod.noobulus.VanillylTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = LivingEntity.class, priority = 800)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyConstant(method = "getJumpBoostVelocityModifier()F", constant = @Constant(floatValue = 1.0F, ordinal = 0))
    private float buffJumpBoostBaseHeight(float constant) {
        return 0.0F; // this feels dumb but somehow it's right?
    }

    @Redirect(method = "getJumpBoostVelocityModifier()F", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectInstance;getAmplifier()I"))
    private int buffJumpBoostScalingHeight(StatusEffectInstance instance) {
        return (instance.getAmplifier() + 1) * 2; // scale twice as fast as usual
    }

    @ModifyVariable(method = "modifyAppliedDamage", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getProtectionAmount(Ljava/lang/Iterable;Lnet/minecraft/entity/damage/DamageSource;)I"), argsOnly = true)
    private float armorSpecialDamageResists(float value, DamageSource source) {
        Iterable<ItemStack> equipment = ((LivingEntity) (Object) this).getArmorItems();
        float percentDamageReduction = 100;
        if (source.getSource() != null) {
            return value;
        }
        for (ItemStack itemStack : equipment) {
            if (itemStack.isIn(VanillylTags.LEATHER_ARMOR)) {
                if (source.isIn(DamageTypeTags.IS_FALL)) {
                    percentDamageReduction += (12.5f); // 50% resistance with full set
                } else if (source.isIn(DamageTypeTags.IS_FIRE)) {
                    percentDamageReduction += (7.5f); // 30% resistance with full set
                } else if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                    percentDamageReduction += (25f); // 100% resistance with full set
                }
            } else if (itemStack.isIn(VanillylTags.NETHERITE_ARMOR)) {
                if (source.isIn(DamageTypeTags.IS_FIRE)) {
                    percentDamageReduction += (20f); // 80% resistance with full set
                }
            }
        }
        return value * (1 - (percentDamageReduction / 100));
    }
}
