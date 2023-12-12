package mod.noobulus.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = net.minecraft.block.entity.BrewingStandBlockEntity.class, priority = 800)
public class BrewingStandBlockEntityMixin {

    @Redirect(method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BrewingStandBlockEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean useLavaAsFuel(ItemStack itemStack, Item item) {
        return itemStack.isOf(Items.LAVA_BUCKET);
    }

    @Inject(method = "tick(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/block/entity/BrewingStandBlockEntity;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V", ordinal = 0))
    private static void leaveBucketBehind(World world, BlockPos pos, BlockState state, net.minecraft.block.entity.BrewingStandBlockEntity blockEntity, CallbackInfo ci) {
        ItemStack itemStack = (ItemStack) blockEntity.inventory.get(4);
        Item item = itemStack.getItem().getRecipeRemainder();
        blockEntity.inventory.set(4, new ItemStack(item));
    }

    @Redirect(method = "isValid(ILnet/minecraft/item/ItemStack;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    public boolean lavaIsValidFuel(ItemStack itemStack, Item item) {
        return itemStack.isOf(Items.LAVA_BUCKET);
    }
}
