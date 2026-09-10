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
import net.minecraft.world.level.block.RailBlock;
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
        // make locomotive right side up
        poseStack.rotateAround(Axis.XP.rotationDegrees(180.0f),0.5f,0.5f,0.5f);
        poseStack.translate(0D,-0.475D,1.0D);

        // try to properly rotate locomotive to face direction
        if(diesel.getBlockStateOn().getBlock() instanceof RailBlock){

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
