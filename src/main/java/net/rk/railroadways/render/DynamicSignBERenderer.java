package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.rk.railroadways.entity.blockentity.custom.DynamicSignBE;
import net.rk.railroadways.entity.blockentity.model.DynamicSignModel;

public class DynamicSignBERenderer implements BlockEntityRenderer<DynamicSignBE>{
    public DynamicSignModel dsmodel;

    public DynamicSignBERenderer(BlockEntityRendererProvider.Context ctx){
        this.dsmodel = new DynamicSignModel(ctx.bakeLayer(DynamicSignModel.SIGN_TEXTURE_LOCATION));
    }

    @Override
    public void render(DynamicSignBE dynamicSignBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entityTranslucent(dynamicSignBE.rl()));
        if(dynamicSignBE.customSign){
            vc = multiBufferSource.getBuffer(RenderType.entityTranslucent(dynamicSignBE.getCustomRLAsFull()));
        }
        poseStack.pushPose();
        poseStack.translate(0.5f,-0.5f,0.5f);

        this.dsmodel.setupAnim(dynamicSignBE);
        this.dsmodel.renderToBuffer(poseStack,vc,i,i1);

        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return 32;
    }

    @Override
    public AABB getRenderBoundingBox(DynamicSignBE blockEntity){
        return new AABB(blockEntity.getBlockPos().getX() - 1, blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ() - 1,
                blockEntity.getBlockPos().getX() + 1, blockEntity.getBlockPos().getY() + 1, blockEntity.getBlockPos().getZ() + 1);
    }

    @Override
    public boolean shouldRender(DynamicSignBE be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(1.0, 0.0, 1.0)
                .closerThan(vec3.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
    }

    @Override
    public boolean shouldRenderOffScreen(DynamicSignBE blockEntity) {
        return false;
    }
}
