package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingBE;
import net.rk.railroadways.entity.blockentity.model.RRArmModel;

public class RailroadCrossingArmBERenderer implements BlockEntityRenderer<RailroadCrossingBE>{
    public RailroadCrossingArmBERenderer(BlockEntityRendererProvider.Context berpContext){
        model = new RRArmModel(berpContext.bakeLayer(RRArmModel.LAYER_LOCATION));
    }

    private RRArmModel model;

    @Override
    public void render(RailroadCrossingBE railroadCrossingBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();
        poseStack.scale(1.0f,1.0f,1.0f);
        poseStack.translate(0.5f,-0.5f,0.5f);

        this.model.setupAnim(railroadCrossingBE);
        this.model.renderToBuffer(poseStack,multiBufferSource.getBuffer(RenderType.entityTranslucent(RRArmModel.LAYER_LOCATION.getModel())),i,i1);
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(RailroadCrossingBE blockEntity){
        int additionalamt = 5;
        return new AABB(blockEntity.getBlockPos().getX() - blockEntity.armLength / 2 - additionalamt, blockEntity.getBlockPos().getY() - 2, blockEntity.getBlockPos().getZ() - blockEntity.armLength / 2 - additionalamt,
                blockEntity.getBlockPos().getX() + blockEntity.armLength / 2 + additionalamt, blockEntity.getBlockPos().getY() + additionalamt, blockEntity.getBlockPos().getZ() + blockEntity.armLength / 2 + additionalamt);
    }

    @Override
    public boolean shouldRender(RailroadCrossingBE be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(2.0, 2.0, 2.0)
                .closerThan(vec3.multiply(2.0, 2.0, 2.0), (double)this.getViewDistance());
    }

    @Override
    public int getViewDistance() {
        return 54;
    }

    @Override
    public boolean shouldRenderOffScreen(RailroadCrossingBE blockEntity){
        return true;
    }
}
