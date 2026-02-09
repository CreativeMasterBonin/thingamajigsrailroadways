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
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.block.custom.RailroadCrossingCantileverLights;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingCantLightsBE;
import net.rk.railroadways.entity.blockentity.model.RRCantLightsBigModel;
import net.rk.railroadways.entity.blockentity.model.RRCantLightsModel;

public class RRCantLightsBERenderer implements BlockEntityRenderer<RailroadCrossingCantLightsBE>{
    public RRCantLightsModel model;
    public RRCantLightsBigModel bigModel;

    private ResourceLocation lightsOff = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/rr_cantilever_off.png");
    private ResourceLocation lightsOn0 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/rr_cantilever_on_0.png");
    private ResourceLocation lightsOn1 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/rr_cantilever_on_1.png");

    private ResourceLocation bigLightsOff = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_cantilever_lights_off.png");
    private ResourceLocation bigLightsOn0 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_cantilever_lights_on_0.png");
    private ResourceLocation bigLightsOn1 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_cantilever_lights_on_1.png");


    public RRCantLightsBERenderer(BlockEntityRendererProvider.Context ctx){
        this.model = new RRCantLightsModel(ctx.bakeLayer(RRCantLightsModel.CANT_LIGHTS_TEXTURE_LOCATION));
        this.bigModel = new RRCantLightsBigModel(ctx.bakeLayer(RRCantLightsBigModel.DEFAULT_TEXTURE));
    }

    @Override
    public void render(RailroadCrossingCantLightsBE rrclbe, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1){
        poseStack.pushPose();
        poseStack.translate(0.5f,0.0f,0.5f);

        ResourceLocation tempLoc;

        if(rrclbe.getBlockState().is(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS)){
            tempLoc = bigLightsOff;
        }
        else{
            tempLoc = lightsOff;
        }

        if(rrclbe.getBlockState().getValue(RailroadCrossingCantileverLights.POWERED)){
            if(rrclbe.getBlockState().is(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS)){
                if(rrclbe.getFlashState()){
                    tempLoc = bigLightsOn1; // whoops had to swap these two
                }
                else{
                    tempLoc = bigLightsOn0;
                }
            }
            else {
                if(rrclbe.getFlashState()){
                    tempLoc = lightsOn0;
                }
                else{
                    tempLoc = lightsOn1;
                }
            }
        }

        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entityTranslucent(tempLoc));

        if(rrclbe.getBlockState().is(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS)){
            this.bigModel.setupAnim(rrclbe);
            this.bigModel.renderToBuffer(poseStack,vc,i,i1);
        }
        else {
            this.model.setupAnim(rrclbe);
            this.model.renderToBuffer(poseStack,vc,i,i1);
        }

        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return 32;
    }

    @Override
    public AABB getRenderBoundingBox(RailroadCrossingCantLightsBE blockEntity){
        return new AABB(blockEntity.getBlockPos().getX() - 1, blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ() - 1,
                blockEntity.getBlockPos().getX() + 1, blockEntity.getBlockPos().getY() + 1, blockEntity.getBlockPos().getZ() + 1);
    }

    @Override
    public boolean shouldRender(RailroadCrossingCantLightsBE be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(1.0, 0.0, 1.0)
                .closerThan(vec3.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
    }

    @Override
    public boolean shouldRenderOffScreen(RailroadCrossingCantLightsBE blockEntity) {
        return false;
    }
}
