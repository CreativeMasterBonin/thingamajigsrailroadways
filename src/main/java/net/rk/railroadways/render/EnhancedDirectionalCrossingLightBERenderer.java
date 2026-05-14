package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.block.custom.EnhancedDirectionalCrossingLight;
import net.rk.railroadways.block.custom.VerticalPoleRedstoneRR;
import net.rk.railroadways.entity.blockentity.custom.EnhancedDirectionalCrossingLightBE;
import net.rk.railroadways.util.Utilities;

import java.util.Objects;

public class EnhancedDirectionalCrossingLightBERenderer implements BlockEntityRenderer<EnhancedDirectionalCrossingLightBE> {
    public Minecraft mc;
    public BlockRenderDispatcher dispatcher;

    public EnhancedDirectionalCrossingLightBERenderer(BlockEntityRendererProvider.Context ctx) {
        mc = Objects.requireNonNull(Minecraft.getInstance());
        dispatcher = Objects.requireNonNull(mc.getBlockRenderer());
    }

    @Override
    public void render(EnhancedDirectionalCrossingLightBE enhancedDirectionalCrossingLightBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.rotateAround(Axis.YP.rotationDegrees(Math.clamp(enhancedDirectionalCrossingLightBE.yAngle,0,360)),0.5f,0f,0.5f);

        boolean renderedLight = false;

        dispatcher.renderSingleBlock(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get().defaultBlockState()
                        .setValue(VerticalPoleRedstoneRR.POWERED,enhancedDirectionalCrossingLightBE.getBlockState().getValue(EnhancedDirectionalCrossingLight.POWERED)),
                poseStack,multiBufferSource,packedLight,packedOverlay,ModelData.EMPTY,RenderType.SOLID);

        if(enhancedDirectionalCrossingLightBE.getBlockState().getValue(EnhancedDirectionalCrossingLight.POWERED)){
            if(enhancedDirectionalCrossingLightBE.orangeLightsShowDirectionOfTravel){
                if(enhancedDirectionalCrossingLightBE.orangeLightState == EnhancedDirectionalCrossingLightBE.DirectionalLightStates.LEFT){
                    if(enhancedDirectionalCrossingLightBE.flasherConfiguration == 127){
                        dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                        .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.ON)
                                        .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.LEFT),poseStack,
                                multiBufferSource, Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                        renderedLight = true;
                    }
                    else if(enhancedDirectionalCrossingLightBE.flasherConfiguration == 64){
                        dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                        .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.OFF)
                                        .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.LEFT),poseStack,
                                multiBufferSource, Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                        renderedLight = true;
                    }
                    else{
                        dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                        .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.ON)
                                        .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.OFF),poseStack,
                                multiBufferSource, Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                        renderedLight = true;
                    }
                }
                else if(enhancedDirectionalCrossingLightBE.orangeLightState == EnhancedDirectionalCrossingLightBE.DirectionalLightStates.RIGHT){
                    if(enhancedDirectionalCrossingLightBE.flasherConfiguration == 127){
                        dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                        .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.ON)
                                        .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.RIGHT),poseStack,
                                multiBufferSource,Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                        renderedLight = true;
                    }
                    else if(enhancedDirectionalCrossingLightBE.flasherConfiguration == 64){
                        dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                        .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.OFF)
                                        .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.RIGHT),poseStack,
                                multiBufferSource,Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                        renderedLight = true;
                    }
                    else{
                        dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                        .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.ON)
                                        .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.OFF),poseStack,
                                multiBufferSource,Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                        renderedLight = true;
                    }
                }
            }
            else if(enhancedDirectionalCrossingLightBE.orangeLightState == EnhancedDirectionalCrossingLightBE.DirectionalLightStates.CENTER){
                if(enhancedDirectionalCrossingLightBE.flasherConfiguration == 127){
                    dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                    .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.ON)
                                    .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.CENTER),poseStack,
                            multiBufferSource,Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                    renderedLight = true;
                }
                else if(enhancedDirectionalCrossingLightBE.flasherConfiguration == 64){
                    dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                    .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.OFF)
                                    .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.CENTER),poseStack,
                            multiBufferSource,Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                    renderedLight = true;
                }
                else{
                    dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                    .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.ON)
                                    .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.OFF),poseStack,
                            multiBufferSource,Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.SOLID);
                    renderedLight = true;
                }
            }
            // this area was being reached... OOPS! a check for not being rendered is below
        }
        else{
            dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                            .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.OFF)
                            .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.OFF),poseStack,
                    multiBufferSource,packedLight,packedOverlay, ModelData.EMPTY, RenderType.SOLID);
            renderedLight = true;
        }

        // check if any model was rendered for the light
        // sometimes this is reached even when the model looks right... potential bug?
        // renders the 'off' model when nothing was rendered
        if(!renderedLight){
            dispatcher.renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                            .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.OFF)
                            .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.OFF),poseStack,
                    multiBufferSource,packedLight,packedOverlay, ModelData.EMPTY, RenderType.SOLID);
        }
        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return 78;
    }

    @Override
    public AABB getRenderBoundingBox(EnhancedDirectionalCrossingLightBE blockEntity){
        return new AABB(blockEntity.getBlockPos().getX() - 2, blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ() - 2,
                blockEntity.getBlockPos().getX() + 2, blockEntity.getBlockPos().getY() + 1, blockEntity.getBlockPos().getZ() + 2);
    }

    @Override
    public boolean shouldRender(EnhancedDirectionalCrossingLightBE be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(2.5, 0.0, 2.5)
                .closerThan(vec3.multiply(2.5, 0.0, 2.5), (double)this.getViewDistance());
    }

    @Override
    public boolean shouldRenderOffScreen(EnhancedDirectionalCrossingLightBE blockEntity) {
        return true;
    }
}
