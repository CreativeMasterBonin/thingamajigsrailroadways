package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.rk.railroadways.entity.blockentity.custom.BritRailwayLightsBE;
import net.rk.railroadways.entity.blockentity.model.BritRRLightsModel;
import net.rk.railroadways.util.Utilities;

public class BritRailwayLightsBERenderer implements BlockEntityRenderer<BritRailwayLightsBE>{
    public BritRRLightsModel britrrlightsmodel;

    public BritRailwayLightsBERenderer(BlockEntityRendererProvider.Context ctx){
        this.britrrlightsmodel = new BritRRLightsModel(ctx.bakeLayer(BritRRLightsModel.BRIT_LIGHTS_OFF_LOC));
    }

    @Override
    public void render(BritRailwayLightsBE brwlbe, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1){
        String strloc = brwlbe.offLoc;
        boolean left = brwlbe.onLeftFlash;

        if(brwlbe.lightsState == BritRailwayLightsBE.BritRailwayLightsState.OFF){
            strloc = brwlbe.offLoc;
        }
        else if(brwlbe.lightsState == BritRailwayLightsBE.BritRailwayLightsState.AMBER){
            strloc = brwlbe.amberLoc;
        }
        else{
            if(left){
                strloc = brwlbe.on0;
            }
            else{
                strloc = brwlbe.on1;
            }
        }

        VertexConsumer vc = multiBufferSource.getBuffer(RenderType
                .entityCutout(ResourceLocation.parse(strloc)));

        poseStack.pushPose();
        this.britrrlightsmodel.getLights().yRot = Utilities.degreesToRadians(brwlbe.yAngle);
        this.britrrlightsmodel.getLights().xRot = Utilities.degreesToRadians(180);
        this.britrrlightsmodel.getLights().zRot = Utilities.degreesToRadians(0);
        poseStack.translate(0.5f,-0.5f,0.5f);
        if(brwlbe.lightsState == BritRailwayLightsBE.BritRailwayLightsState.ON){
            this.britrrlightsmodel.getLights().render(poseStack,vc,Utilities.getLightLevel(2),i1);
        }
        else if(brwlbe.lightsState == BritRailwayLightsBE.BritRailwayLightsState.AMBER){
            this.britrrlightsmodel.getLights().render(poseStack,vc,Utilities.getLightLevel(2),i1);
        }
        else{
            this.britrrlightsmodel.getLights().render(poseStack,vc,i,i1);
        }
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0.5f,-0.5f,0.5f);
        this.britrrlightsmodel.getMain().render(poseStack,vc,i,i1);
        poseStack.popPose();
    }
}
