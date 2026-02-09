package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.block.custom.DualRailwayLightsBlock;
import net.rk.railroadways.entity.blockentity.custom.TriRailwayLightsBE;
import net.rk.railroadways.entity.blockentity.model.TriLightsModel;

public class TriRailwayLightsBERenderer implements BlockEntityRenderer<TriRailwayLightsBE>{
    public TriLightsModel tlmodel;

    public TriRailwayLightsBERenderer(BlockEntityRendererProvider.Context ctx){
        this.tlmodel = new TriLightsModel(ctx.bakeLayer(TriLightsModel.TRI_TEXTURE_LOC));
    }

    @Override
    public void render(TriRailwayLightsBE brwlbe, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1){
        String strloc = brwlbe.offLoc;
        if(!brwlbe.getBlockState().getValue(DualRailwayLightsBlock.POWERED)){
            if(brwlbe.ticks >= brwlbe.delayTicks / 2){
                strloc = brwlbe.whiteLoc;
            }
            else{
                strloc = brwlbe.offLoc;
            }
        }
        else{
            if(brwlbe.ticks >= brwlbe.delayTicks / 2){
                strloc = brwlbe.on0;
            }
            else{
                strloc = brwlbe.on1;
            }
        }

        VertexConsumer vc = multiBufferSource.getBuffer(RenderType
                .entityCutout(ResourceLocation.parse(strloc)));

        poseStack.pushPose();
        poseStack.translate(0.5f,0.0f,0.5f);

        this.tlmodel.setupAnim(brwlbe);
        this.tlmodel.renderToBuffer(poseStack,vc,i,i1);

        poseStack.popPose();
    }
}
