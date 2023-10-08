package mod.noobulus.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PigEntity.class, priority = 800)
public abstract class PigEntityMixin extends AnimalEntity {

    protected PigEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isBreedingItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
    public void pigsBredWithTag(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier("vanillyl", "pig_breeding_items"))));
    }

    @ModifyArg(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 4), index = 1)
    private Goal pigsTemptedByTag(Goal goal) {
        return new TemptGoal(this, 1.25, Ingredient.fromTag(TagKey.of(RegistryKeys.ITEM, new Identifier("vanillyl", "pig_breeding_items"))), false);
    }
}
