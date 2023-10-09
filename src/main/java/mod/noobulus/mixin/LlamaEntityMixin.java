package mod.noobulus.mixin;

import mod.noobulus.VanillylTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LlamaEntity.class, priority = 800)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity {

    protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "setStrength(I)V", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int llamasAlwaysHaveMaxInvSize(int strength) {
        return 5;
    }

    @Inject(method = "isBreedingItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"), cancellable = true)
    public void llamaBredWithTag(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(stack.isIn(VanillylTags.LLAMA_BREEDING_ITEMS));
    }

    @ModifyArg(method = "initGoals()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 6), index = 1)
    private Goal llamasTemptedByTag(Goal goal) {
        return new TemptGoal(this, 1.25, Ingredient.fromTag(VanillylTags.LLAMA_BREEDING_ITEMS), false);
    }

    @Inject(method = "receiveFood(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void theBadWayPartTwo(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cir) {
        int i = 0;
        int j = 0;
        float f = 0.0F;
        boolean bl = false;
        if (item.isIn(VanillylTags.LLAMA_BREEDING_ITEMS)) {
            i = 120;
            j = 6;
            f = 10.0F;
            if (((LlamaEntity) (Object) this).isTame() && ((LlamaEntity) (Object) this).getBreedingAge() == 0 && ((LlamaEntity) (Object) this).canEat()) {
                bl = true;
                ((LlamaEntity) (Object) this).lovePlayer(player);
            }
        } else if (item.isIn(VanillylTags.LLAMA_TREATS)) {
            i = 30;
            j = 3;
            f = 2.0F;
        }

        if (((LlamaEntity) (Object) this).getHealth() < ((LlamaEntity) (Object) this).getMaxHealth() && f > 0.0F) {
            ((LlamaEntity) (Object) this).heal(f);
            bl = true;
        }

        if (((LlamaEntity) (Object) this).isBaby() && i > 0) {
            ((LlamaEntity) (Object) this).getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, ((LlamaEntity) (Object) this).getParticleX(1.0), ((LlamaEntity) (Object) this).getRandomBodyY() + 0.5, ((LlamaEntity) (Object) this).getParticleZ(1.0), 0.0, 0.0, 0.0);
            if (!((LlamaEntity) (Object) this).getWorld().isClient) {
                ((LlamaEntity) (Object) this).growUp(i);
            }

            bl = true;
        }

        if (j > 0 && (bl || !((LlamaEntity) (Object) this).isTame()) && ((LlamaEntity) (Object) this).getTemper() < ((LlamaEntity) (Object) this).getMaxTemper()) {
            bl = true;
            if (!((LlamaEntity) (Object) this).getWorld().isClient) {
                ((LlamaEntity) (Object) this).addTemper(j);
            }
        }

        if (bl && !((LlamaEntity) (Object) this).isSilent()) {
            SoundEvent soundEvent = ((LlamaEntity) (Object) this).getEatSound();
            if (soundEvent != null) {
                ((LlamaEntity) (Object) this).getWorld().playSound((PlayerEntity)null, ((LlamaEntity) (Object) this).getX(), ((LlamaEntity) (Object) this).getY(), ((LlamaEntity) (Object) this).getZ(), ((LlamaEntity) (Object) this).getEatSound(), ((LlamaEntity) (Object) this).getSoundCategory(), 1.0F, 1.0F + (((LlamaEntity) (Object) this).random.nextFloat() - ((LlamaEntity) (Object) this).random.nextFloat()) * 0.2F);
            }
        }

        cir.setReturnValue(bl);
        cir.cancel();
    }
}
