package mod.noobulus.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = OcelotEntity.class, priority = 800)
public abstract class OcelotEntityMixin extends AnimalEntity {

    protected OcelotEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/OcelotEntity;", at = @At(value = "TAIL"), cancellable = true)
    void babyOcelotsTrustPlayers(ServerWorld serverWorld, PassiveEntity passiveEntity, CallbackInfoReturnable<OcelotEntity> cir) {
        OcelotEntity baby = EntityType.OCELOT.create(serverWorld);
        if (baby != null && ((OcelotEntity) passiveEntity).isTrusting() && ((OcelotEntity) (Object) this).isTrusting()) { // baby is real, both parents trust player
            baby.setTrusting(true);
        }
        cir.setReturnValue(baby);
    }

    @Inject(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    void creativeFedOcelotsTrustPlayers(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (player.isCreative() && !((OcelotEntity) (Object) this).isTrusting() && this.isBreedingItem(itemStack)) {
            this.eat(player, hand, itemStack);
            if (!this.getWorld().isClient) {
                ((OcelotEntity) (Object) this).setTrusting(true);
                ((OcelotEntity) (Object) this).showEmoteParticle(true);
                this.getWorld().sendEntityStatus(this, (byte) 41);
            }

            cir.setReturnValue(ActionResult.success(this.getWorld().isClient));
        }
    }
}
