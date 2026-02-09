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
import net.rk.railroadways.block.custom.RailroadCrossingArmLightedBlock;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingArmWithLights;
import net.rk.railroadways.entity.blockentity.model.RRArmLightsModel;

public class RailroadCrossingArmWithLightsRenderer implements BlockEntityRenderer<RailroadCrossingArmWithLights> {
    private ResourceLocation noLight = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/railroad_arm_no_light.png");
    private ResourceLocation firstLight = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/railroad_arm_first_light.png");
    private ResourceLocation secondLight = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/railroad_arm_second_light.png");

    public RailroadCrossingArmWithLightsRenderer(BlockEntityRendererProvider.Context berpContext){
        model = new RRArmLightsModel(berpContext.bakeLayer(RRArmLightsModel.RAILROAD_CROSSING_ARM_WITH_LIGHTS));
    }

    private RRArmLightsModel model;

    @Override
    public void render(RailroadCrossingArmWithLights railroadCrossingBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();
        poseStack.scale(1.0f,1.0f,1.0f);
        poseStack.translate(0.5f,-1.5f,0.5f);

        ResourceLocation lightsOffTemp = noLight;

        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entityTranslucent(lightsOffTemp));

        if(railroadCrossingBE.getBlockState().getValue(RailroadCrossingArmLightedBlock.POWERED)){
            if(railroadCrossingBE.getFlashState()){
                vc = multiBufferSource.getBuffer(RenderType.entityTranslucent(firstLight));
            }
            else{
                vc = multiBufferSource.getBuffer(RenderType.entityTranslucent(secondLight));
            }
        }

        this.model.setupAnim(railroadCrossingBE);
        this.model.renderToBuffer(poseStack,vc,i,i1);
        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(RailroadCrossingArmWithLights blockEntity){
        int additionalamt = 5;
        return new AABB(blockEntity.getBlockPos().getX() - blockEntity.armLength / 2 - additionalamt, blockEntity.getBlockPos().getY() - 2, blockEntity.getBlockPos().getZ() - blockEntity.armLength / 2 - additionalamt,
                blockEntity.getBlockPos().getX() + blockEntity.armLength / 2 + additionalamt, blockEntity.getBlockPos().getY() + additionalamt, blockEntity.getBlockPos().getZ() + blockEntity.armLength / 2 + additionalamt);
    }

    @Override
    public boolean shouldRender(RailroadCrossingArmWithLights be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(2.0, 2.0, 2.0)
                .closerThan(vec3.multiply(2.0, 2.0, 2.0), (double)this.getViewDistance());
    }

    @Override
    public int getViewDistance() {
        return 54;
    }

    @Override
    public boolean shouldRenderOffScreen(RailroadCrossingArmWithLights blockEntity){
        return true;
    }
}
