package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.rk.railroadways.block.custom.MultipurposeSignBlock;
import net.rk.railroadways.entity.blockentity.custom.MultipurposeSignBE;
import net.rk.railroadways.entity.blockentity.model.MultipurposeSignModel;
import net.rk.railroadways.util.Utilities;

public class MultipurposeSignBERenderer implements BlockEntityRenderer<MultipurposeSignBE> {
    public MultipurposeSignModel signModel;

    public MultipurposeSignBERenderer(BlockEntityRendererProvider.Context ctx){
        this.signModel = new MultipurposeSignModel(ctx.bakeLayer(MultipurposeSignModel.DEFAULT_MULTIPURPOSE_TEXTURE));
    }

    @Override
    public void render(MultipurposeSignBE multipurposeSign, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay){
        poseStack.pushPose();
        String str;
        String strOn;
        String strOnAlt;
        VertexConsumer vc;

        boolean failedTest = false;

        // prevent the condition of uninitialized variables, even if the location is not confirmed yet
        str = multipurposeSign.texture + ".png";
        strOn = multipurposeSign.textureOn + ".png";
        strOnAlt = multipurposeSign.textureOnAlt + ".png";
        // the default vertex consumer for when textures are faulty or missing
        vc = multiBufferSource.getBuffer(RenderType.entityCutout(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png")));

        poseStack.translate(0.5f, -0.5f, 0.5f);

        signModel.getFcon().visible = multipurposeSign.getBlockState().getValue(MultipurposeSignBlock.NORTH);
        signModel.getBcon().visible = multipurposeSign.getBlockState().getValue(MultipurposeSignBlock.SOUTH);
        signModel.getLcon().visible = multipurposeSign.getBlockState().getValue(MultipurposeSignBlock.EAST);
        signModel.getRcon().visible = multipurposeSign.getBlockState().getValue(MultipurposeSignBlock.WEST);

        signModel.getSign().xRot = Mth.PI + Utilities.degreesToRadians(multipurposeSign.zAngle);
        this.signModel.setupAnim(multipurposeSign);

        try{
            // all texture locations must be valid or use the default texture as the last fallback
            if(ResourceLocation.read(str).isError() || ResourceLocation.read(strOn).isError() || ResourceLocation.read(strOnAlt).isError()){
                failedTest = true;
                str = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png";
                strOn = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png";
                strOnAlt = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png";
            }

            if(failedTest){
                LogUtils.getLogger().warn("MultipurposeSignType {} has failed the test! Texture locations were: OFF: {} ON: {} ON ALT:{}",multipurposeSign.indexId,str,strOn,strOnAlt);
            }

            if(multipurposeSign.getBlockState().getValue(MultipurposeSignBlock.POWERED)){
                if(multipurposeSign.alternatingTextures) {
                    if (multipurposeSign.alternateTexture) {
                        vc = multiBufferSource.getBuffer(RenderType.entityCutout(ResourceLocation.parse(strOn)));
                    } else {
                        vc = multiBufferSource.getBuffer(RenderType.entityCutout(ResourceLocation.parse(strOnAlt)));
                    }
                }
                else{
                    vc = multiBufferSource.getBuffer(RenderType.entityCutout(ResourceLocation.parse(strOn)));
                }
                this.signModel.getPole().render(poseStack,vc,packedLight,packedOverlay);
                this.signModel.sign.render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
            }
            else{
                vc = multiBufferSource.getBuffer(RenderType.entityCutout(ResourceLocation.parse(str)));
                this.signModel.getPole().render(poseStack,vc,packedLight,packedOverlay);
                this.signModel.sign.render(poseStack,vc,packedLight,packedOverlay);
            }
        }
        catch (Exception e){

        }
        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return 54; // signs need to be visible from a distance
    }

    @Override
    public AABB getRenderBoundingBox(MultipurposeSignBE blockEntity){
        return new AABB(blockEntity.getBlockPos().getX() - 1, blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ() - 1,
                blockEntity.getBlockPos().getX() + 1, blockEntity.getBlockPos().getY() + 1, blockEntity.getBlockPos().getZ() + 1);
    }

    @Override
    public boolean shouldRender(MultipurposeSignBE be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(1.0, 0.0, 1.0)
                .closerThan(vec3.multiply(1.0, 0.0, 1.0), (double)this.getViewDistance());
    }

    @Override
    public boolean shouldRenderOffScreen(MultipurposeSignBE blockEntity) {
        return false;
    }
}
