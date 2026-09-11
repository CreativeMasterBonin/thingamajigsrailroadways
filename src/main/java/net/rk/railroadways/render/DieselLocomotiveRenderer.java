package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.rk.railroadways.entity.custom.DieselLocomotive;
import net.rk.railroadways.entity.custom.model.DieselLocomotiveModel;
import net.rk.railroadways.util.Utilities;

public class DieselLocomotiveRenderer extends EntityRenderer<DieselLocomotive> {
    public DieselLocomotiveModel dieselModel;
    public static final ResourceLocation defaultTexture = ResourceLocation.parse("thingamajigsrailroadways:textures/entity/diesel_locomotive.png");

    public DieselLocomotiveRenderer(EntityRendererProvider.Context context) {
        super(context);
        dieselModel = new DieselLocomotiveModel(context.bakeLayer(DieselLocomotiveModel.DEFAULT));
    }

    @Override
    public ResourceLocation getTextureLocation(DieselLocomotive dieselLocomotive) {
        return DieselLocomotiveModel.DEFAULT.getModel();
    }

    @Override
    public void render(DieselLocomotive diesel, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.rotateAround(Axis.XP.rotationDegrees(180.0f),0.5f,0.5f,0.5f);
        poseStack.rotateAround(Axis.ZP.rotationDegrees(0.0f),0.5f,0.5f,0.5f);
        poseStack.translate(0D,-0.475D,1.0D);

        boolean isOppositeSpeedX = diesel.getDeltaMovement().x < 0.0f;
        boolean isOppositeSpeedZ = diesel.getDeltaMovement().z < 0.0f;

        // try to properly rotate locomotive to face direction
        if(diesel.getBlockStateOn().hasProperty(BlockStateProperties.RAIL_SHAPE)){
            if(diesel.getBlockStateOn().getValue(RailBlock.SHAPE).isAscending()){
                if(diesel.getBlockStateOn().getValue(RailBlock.SHAPE).equals(RailShape.ASCENDING_NORTH)){
                    poseStack.translate(0D,-0.5D,-1.0D);
                    if(isOppositeSpeedX){
                        poseStack.rotateAround(Axis.XP.rotationDegrees(-45.0f),0.5f,0.5f,0.5f);
                    }
                    else{
                        poseStack.rotateAround(Axis.XP.rotationDegrees(45.0f),0.5f,0.5f,0.5f);
                    }
                }
                else if(diesel.getBlockStateOn().getValue(RailBlock.SHAPE).equals(RailShape.ASCENDING_SOUTH)){
                    poseStack.translate(0D,0.15D,0.0D);
                    if(isOppositeSpeedX){
                        poseStack.rotateAround(Axis.XP.rotationDegrees(45.0f),0.5f,0.5f,0.5f);
                    }
                    else{
                        poseStack.rotateAround(Axis.XP.rotationDegrees(-45.0f),0.5f,0.5f,0.5f);
                    }
                }
                else if(diesel.getBlockStateOn().getValue(RailBlock.SHAPE).equals(RailShape.ASCENDING_EAST)){
                    poseStack.translate(-0.75D,-0.25D,0.0D);
                    if(isOppositeSpeedZ){
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(45.0f),0.5f,0.5f,0.5f);
                    }
                    else{
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(-45.0f),0.5f,0.5f,0.5f);
                    }
                }
                else if(diesel.getBlockStateOn().getValue(RailBlock.SHAPE).equals(RailShape.ASCENDING_WEST)){
                    if(isOppositeSpeedZ){
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(-45.0f),0.5f,0.5f,0.5f);
                    }
                    else{
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(45.0f),0.5f,0.5f,0.5f);
                    }
                }
            }
            else{

            }
        }
        dieselModel.getBase().zRot = 0.0f;
        dieselModel.getBase().xRot = 0.0f;
        float angle = diesel.getPreciseBodyRotation(partialTick);
        dieselModel.getBase().yRot = Utilities.degreesToRadians(Mth.wrapDegrees(angle + 90.0f));

        //dieselModel.setup(diesel,partialTick);
        dieselModel.renderToBuffer(poseStack,
                bufferSource.getBuffer(
                        RenderType.entityCutout(defaultTexture)),
                packedLight,OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(diesel, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
