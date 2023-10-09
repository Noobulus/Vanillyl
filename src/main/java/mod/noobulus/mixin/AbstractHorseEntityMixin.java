package mod.noobulus.mixin;

import mod.noobulus.VanillylTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AbstractHorseEntity.class, priority = 800)
public abstract class AbstractHorseEntityMixin extends AnimalEntity {

    protected AbstractHorseEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyConstant(method = "getChildHealthBonus(Ljava/util/function/IntUnaryOperator;)F", constant = @Constant(floatValue = 15.0F))
    private static float buffHorseBaseHealth(float constant) {
        return 23.0F;
    }

    @ModifyConstant(method = "getChildHealthBonus(Ljava/util/function/IntUnaryOperator;)F", constant = @Constant(intValue = 8))
    private static int tweakHorseHealthStatScalingPartOne(int constant) {
        return 3;
    }

    @ModifyConstant(method = "getChildHealthBonus(Ljava/util/function/IntUnaryOperator;)F", constant = @Constant(intValue = 9))
    private static int tweakHorseHealthStatScalingPartTwo(int constant) {
        return 4;
    }

    @ModifyConstant(method = "getChildJumpStrengthBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.4000000059604645))
    private static double buffHorseBaseJumpHeight(double constant) {
        return 0.8;
    }

    @ModifyConstant(method = "getChildJumpStrengthBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.2))
    private static double tweakHorseJumpHeightStatScaling(double constant) {
        return 0.1;
    }

    @ModifyConstant(method = "getChildMovementSpeedBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.44999998807907104))
    private static double buffHorseBaseSpeed(double constant) {
        return 0.9;
    }

    @ModifyConstant(method = "getChildMovementSpeedBonus(Ljava/util/function/DoubleSupplier;)D", constant = @Constant(doubleValue = 0.3))
    private static double tweakHorseSpeedStatScaling(double constant) {
        return 0.15;
    }

    /*@Redirect(method = "receiveFood(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    public boolean skipVanillaFoodLogic(ItemStack instance, Item item) {
        return false;
    }

    @Inject(method = "receiveFood(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AbstractHorseEntity;getHealth()F", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    public void rejectYourLogicAndSubtituteMyOwn(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cir, boolean bl, float f, int i, int j) {
        if (item.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier("vanillyl", "horse_treats_small")))) {
            f = 2.0F;
            i = 60;
            j = 3;
        } else if (item.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier("vanillyl", "horse_treats")))) {
            f = 6.0F;
            i = 120;
            j = 5;
        } else if (item.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier("vanillyl", "horse_treats_large")))) {
            f = 10.0F;
            i = 240;
            j = 10;
        } else if (item.isIn(TagKey.of(RegistryKeys.ITEM, new Identifier("vanillyl", "horse_breeding_items")))) {
            f = 10.0F;
            i = 240;
            j = 10;
            if (!((AbstractHorseEntity) (Object) this).getWorld().isClient && ((AbstractHorseEntity) (Object) this).isTame() && ((AbstractHorseEntity) (Object) this).getBreedingAge() == 0 && !((AbstractHorseEntity) (Object) this).isInLove()) {
                bl = true;
                ((AbstractHorseEntity) (Object) this).lovePlayer(player);
            }
        }
    }*/

    @Inject(method = "receiveFood(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void theBadWay(PlayerEntity player, ItemStack item, CallbackInfoReturnable<Boolean> cir) {
        boolean bl = false;
        float f = 0.0F;
        int i = 0;
        int j = 0;
        if (item.isIn(VanillylTags.HORSE_BREEDING_ITEMS)) {
            f = 10.0F;
            i = 240;
            j = 10;
            if (!((AbstractHorseEntity) (Object) this).getWorld().isClient && ((AbstractHorseEntity) (Object) this).isTame() && ((AbstractHorseEntity) (Object) this).getBreedingAge() == 0 && !((AbstractHorseEntity) (Object) this).isInLove()) {
                bl = true;
                ((AbstractHorseEntity) (Object) this).lovePlayer(player);
            }
        } else if (item.isIn(VanillylTags.HORSE_TREATS_LARGE)) {
            f = 10.0F;
            i = 240;
            j = 10;
        } else if (item.isIn(VanillylTags.HORSE_TREATS)) {
            f = 6.0F;
            i = 120;
            j = 5;
        } else if (item.isIn(VanillylTags.HORSE_TREATS_SMALL)) {
            f = 2.0F;
            i = 60;
            j = 3;
        }

        if (((AbstractHorseEntity) (Object) this).getHealth() < ((AbstractHorseEntity) (Object) this).getMaxHealth() && f > 0.0F) {
            ((AbstractHorseEntity) (Object) this).heal(f);
            bl = true;
        }

        if (((AbstractHorseEntity) (Object) this).isBaby() && i > 0) {
            ((AbstractHorseEntity) (Object) this).getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, ((AbstractHorseEntity) (Object) this).getParticleX(1.0), ((AbstractHorseEntity) (Object) this).getRandomBodyY() + 0.5, ((AbstractHorseEntity) (Object) this).getParticleZ(1.0), 0.0, 0.0, 0.0);
            if (!((AbstractHorseEntity) (Object) this).getWorld().isClient) {
                ((AbstractHorseEntity) (Object) this).growUp(i);
            }

            bl = true;
        }

        if (j > 0 && (bl || !((AbstractHorseEntity) (Object) this).isTame()) && ((AbstractHorseEntity) (Object) this).getTemper() < ((AbstractHorseEntity) (Object) this).getMaxTemper()) {
            bl = true;
            if (!((AbstractHorseEntity) (Object) this).getWorld().isClient) {
                ((AbstractHorseEntity) (Object) this).addTemper(j);
            }
        }

        if (bl) {
            ((AbstractHorseEntity) (Object) this).playEatingAnimation();
            ((AbstractHorseEntity) (Object) this).emitGameEvent(GameEvent.EAT);
        }

        cir.setReturnValue(bl);
        cir.cancel();
    }
}
