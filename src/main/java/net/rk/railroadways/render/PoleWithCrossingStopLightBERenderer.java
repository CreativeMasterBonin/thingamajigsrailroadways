package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.rk.railroadways.TRRClient;
import net.rk.railroadways.entity.blockentity.custom.PoleWithCrossingStopLightBE;
import net.rk.railroadways.entity.blockentity.model.PoleWithCrossingStopLightHorizontalModel;
import net.rk.railroadways.entity.blockentity.model.PoleWithCrossingStopLightVerticalModel;

import java.util.List;

public class PoleWithCrossingStopLightBERenderer implements BlockEntityRenderer<PoleWithCrossingStopLightBE> {
    public PoleWithCrossingStopLightVerticalModel verticalModel;
    public PoleWithCrossingStopLightHorizontalModel horizontalModel;

    public static final List<ResourceLocation> verticalLocations = List.of(
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_vertical_off.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_vertical_partial_on_1.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_vertical_partial_on_2.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_vertical_partial_on_3.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_vertical_on.png")
    );

    public static final List<ResourceLocation> horizontalLocations = List.of(
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_horizontal_off.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_horizontal_partial_on_1.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_horizontal_partial_on_2.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_horizontal_partial_on_3.png"),
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_horizontal_on.png")
    );

    public PoleWithCrossingStopLightBERenderer(BlockEntityRendererProvider.Context berpContext){
        verticalModel = new PoleWithCrossingStopLightVerticalModel(berpContext.bakeLayer(PoleWithCrossingStopLightVerticalModel.VERTICAL_STOP_LAYER));
        horizontalModel = new PoleWithCrossingStopLightHorizontalModel(berpContext.bakeLayer(PoleWithCrossingStopLightHorizontalModel.HORIZONTAL_STOP_LAYER));
    }

    @Override
    public void render(PoleWithCrossingStopLightBE pole, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        poseStack.pushPose();
        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entityCutout(verticalLocations.getFirst()));

        int lightBrightness = pole.brightness;

        poseStack.translate(0.5,-0.5,0.5);

        switch(lightBrightness){
            case 0:{
                if (pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.VERTICAL){
                    this.verticalModel.setupAnim(pole);
                    this.verticalModel.renderToBuffer(poseStack,vc,i,i1);
                }
                else if(pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.HORIZONTAL){
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(horizontalLocations.getFirst()));
                    this.horizontalModel.setupAnim(pole);
                    this.horizontalModel.renderToBuffer(poseStack,vc,i,i1);
                }
                break;
            }
            case 1:{
                if (pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.VERTICAL){
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(verticalLocations.get(1)));
                    this.verticalModel.setupAnim(pole);
                    this.verticalModel.vertical_signage.render(poseStack,vc,3932212,i1);
                    this.verticalModel.main.render(poseStack,vc,i, i1);
                }
                else if(pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.HORIZONTAL){
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(horizontalLocations.get(1)));
                    this.horizontalModel.setupAnim(pole);
                    this.horizontalModel.horizontalSignage.render(poseStack,vc,3932212,i1);
                    this.horizontalModel.main.render(poseStack,vc,i,i1);
                }
                break;
            }
            case 2:{
                if (pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.VERTICAL){
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(verticalLocations.get(2)));
                    this.verticalModel.setupAnim(pole);
                    this.verticalModel.vertical_signage.render(poseStack,vc,7864425,i1);
                    this.verticalModel.main.render(poseStack,vc,i, i1);
                }
                else if(pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.HORIZONTAL){
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(horizontalLocations.get(2)));
                    this.horizontalModel.setupAnim(pole);
                    this.horizontalModel.horizontalSignage.render(poseStack,vc,7864425,i1);
                    this.horizontalModel.main.render(poseStack,vc,i,i1);
                }
                break;
            }
            case 3:{
                if (pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.VERTICAL){
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(verticalLocations.get(3)));
                    this.verticalModel.setupAnim(pole);
                    this.verticalModel.vertical_signage.render(poseStack,vc,15728850,i1);
                    this.verticalModel.main.render(poseStack,vc,i, i1);
                }
                else if(pole.currentOrientation == PoleWithCrossingStopLightBE.Orientation.HORIZONTAL){
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(horizontalLocations.get(3)));
                    this.horizontalModel.setupAnim(pole);
                    this.horizontalModel.horizontalSignage.render(poseStack,vc,15728850,i1);
                    this.horizontalModel.main.render(poseStack,vc,i,i1);
                }
                break;
            }
            default:{
                break;
            }
        }
        poseStack.popPose();
    }
}
