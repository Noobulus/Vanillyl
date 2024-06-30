package mod.noobulus.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.RabbitEntityRenderer;
import net.minecraft.client.render.entity.model.RabbitEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.RabbitEntity;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = RabbitEntityRenderer.class, priority = 800)
public abstract class RabbitEntityRendererMixin extends MobEntityRenderer<RabbitEntity, RabbitEntityModel<RabbitEntity>> {
    @Unique
    private final float rabbitScale = 1.2f;

    protected RabbitEntityRendererMixin(EntityRendererFactory.Context context, RabbitEntityModel<RabbitEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Intrinsic @Override
    public void render(RabbitEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.scale(rabbitScale, rabbitScale, rabbitScale);
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    /*@Inject(method = "Lnet/minecraft/client/render/entity/RabbitEntityRenderer;render(Lnet/minecraft/entity/mob/RabbitEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void embiggenRabbit(RabbitEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        matrixStack.scale(rabbitScale, rabbitScale, rabbitScale);
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
        ci.cancel();
    }*/
}
