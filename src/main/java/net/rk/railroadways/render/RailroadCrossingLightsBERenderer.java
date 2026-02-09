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
import net.rk.railroadways.block.custom.RailroadCrossingLights;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingLightsBE;
import net.rk.railroadways.entity.blockentity.model.RRLightsBigModel;
import net.rk.railroadways.entity.blockentity.model.RRLightsModel;

@SuppressWarnings("deprecated")
public class RailroadCrossingLightsBERenderer implements BlockEntityRenderer<RailroadCrossingLightsBE> {
    public RRLightsModel rrlightsmodel;
    public RRLightsBigModel rrLightsBigModel;

    private ResourceLocation lightsOff = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/lights_off.png");
    private ResourceLocation lightsOn0 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/lights_on_0.png");
    private ResourceLocation lightsOn1 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/lights_on_1.png");

    private ResourceLocation bigLightsOff = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_lights_off.png");
    private ResourceLocation bigLightsOn0 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_lights_on_0.png");
    private ResourceLocation bigLightsOn1 = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_lights_on_1.png");

    public RailroadCrossingLightsBERenderer(BlockEntityRendererProvider.Context ctx){
        this.rrlightsmodel = new RRLightsModel(ctx.bakeLayer(RRLightsModel.LIGHTS_TEXTURE_LOCATION));
        this.rrLightsBigModel = new RRLightsBigModel(ctx.bakeLayer(RRLightsBigModel.DEFAULT_TEXTURE));
    }

    @Override
    public void render(RailroadCrossingLightsBE rrclbe, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1){
        poseStack.pushPose();
        poseStack.translate(0.5f,0.0f,0.5f);

        ResourceLocation tempLoc;

        if(rrclbe.getBlockState().is(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS)){
            tempLoc = bigLightsOff;
        }
        else{
            tempLoc = lightsOff;
        }

        if(rrclbe.getBlockState().getValue(RailroadCrossingLights.POWERED)){
            if(rrclbe.getBlockState().is(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS)){
                if(rrclbe.getFlashState()){
                    tempLoc = bigLightsOn0;
                }
                else{
                    tempLoc = bigLightsOn1;
                }
            }
            else{
                if(rrclbe.getFlashState()){
                    tempLoc = lightsOn0;
                }
                else{
                    tempLoc = lightsOn1;
                }
            }
        }

        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entityTranslucent(tempLoc));
        if(rrclbe.getBlockState().is(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS)){
            this.rrLightsBigModel.setupAnim(rrclbe);
            this.rrLightsBigModel.renderToBuffer(poseStack,vc,i,i1);
        }
        else{
            this.rrlightsmodel.setupAnim(rrclbe);
            this.rrlightsmodel.renderToBuffer(poseStack,vc,i,i1);
        }

        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return 32;
    }

    @Override
    public AABB getRenderBoundingBox(RailroadCrossingLightsBE blockEntity){
        return new AABB(blockEntity.getBlockPos().getX() - 1, blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ() - 1,
                blockEntity.getBlockPos().getX() + 1, blockEntity.getBlockPos().getY() + 1, blockEntity.getBlockPos().getZ() + 1);
    }

    @Override
    public boolean shouldRender(RailroadCrossingLightsBE be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(1.0, 0.0, 1.0)
                .closerThan(vec3.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
    }

    @Override
    public boolean shouldRenderOffScreen(RailroadCrossingLightsBE blockEntity) {
        return false;
    }
}
