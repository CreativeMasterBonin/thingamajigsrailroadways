package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.block.custom.TriRailwayLightsBlock;
import net.rk.railroadways.entity.blockentity.custom.DualRailwayLightsBE;
import net.rk.railroadways.entity.blockentity.model.DualLightsModel;

public class DualRailwayLightsBERenderer implements BlockEntityRenderer<DualRailwayLightsBE> {
    public DualLightsModel dlmodel;

    public DualRailwayLightsBERenderer(BlockEntityRendererProvider.Context ctx){
        this.dlmodel = new DualLightsModel(ctx.bakeLayer(DualLightsModel.DUAL_TEXTURE_LOC));
    }

    @Override
    public void render(DualRailwayLightsBE brwlbe, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1){
        String strloc = brwlbe.offLoc;
        if(!brwlbe.getBlockState().getValue(TriRailwayLightsBlock.POWERED)){
            if(brwlbe.ticks >= brwlbe.delayTicks / 2){
                strloc = brwlbe.whiteLoc;
            }
            else{
                strloc = brwlbe.offLoc;
            }
        }
        else{
            if(brwlbe.ticks >= brwlbe.delayTicks / 2){
                strloc = brwlbe.redLoc;
            }
            else{
                strloc = brwlbe.offLoc;
            }
        }

        VertexConsumer vc = multiBufferSource.getBuffer(RenderType
                .entityCutout(ResourceLocation.parse(strloc)));

        poseStack.pushPose();
        poseStack.translate(0.5f,0.0f,0.5f);

        this.dlmodel.setupAnim(brwlbe);
        this.dlmodel.renderToBuffer(poseStack,vc,i,i1);

        poseStack.popPose();
    }
}
