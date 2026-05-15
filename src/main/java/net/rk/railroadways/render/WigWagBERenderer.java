package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.entity.blockentity.custom.WigWagBE;
import net.rk.railroadways.util.Utilities;

import java.util.Objects;

public class WigWagBERenderer implements BlockEntityRenderer<WigWagBE> {
    private Minecraft mc;
    private BlockRenderDispatcher dispatcher;
    private ModelBlockRenderer blockRenderer;
    private ModelManager manager;

    public static final ModelResourceLocation wigwagArm = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_arm"),"standalone");
    public static final ModelResourceLocation wigwagArmOn = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_arm_on"),"standalone");
    public static final ModelResourceLocation wigwagArmPole = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_arm_pole"),"standalone");
    public static final ModelResourceLocation wigwagArmBase = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_base"),"standalone");
    public static final ModelResourceLocation wigwagArmWeighted = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_arm_with_weight"),"standalone");
    public static final ModelResourceLocation wigwagArmWeightedOn = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_arm_with_weight_on"),"standalone");
    public static final ModelResourceLocation wigwagArmBaseStandalone = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_base_standalone"),"standalone");
    public static final ModelResourceLocation wigwagArmBaseTwoRing = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"block/wig_wag/wig_wag_base_two_ring"),"standalone");

    public WigWagBERenderer(BlockEntityRendererProvider.Context ctx) {
        mc = Objects.requireNonNull(Minecraft.getInstance());
        dispatcher = Objects.requireNonNull(mc.getBlockRenderer());
        blockRenderer = mc.getBlockRenderer().getModelRenderer();
        manager = dispatcher.getBlockModelShaper().getModelManager();
    }

    @Override
    public void render(WigWagBE wigWagBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.entitySolid(
                ResourceLocation.fromNamespaceAndPath(Thingamajigsrailroadways.MODID,"textures/block/wig_wag_on.png")));

        poseStack.translate(0,0,0);

        // rotate per wanted dir
        poseStack.rotateAround(Axis.YP.rotationDegrees(wigWagBE.yAngle),0.5f,0.5f,0.5f);

        if(wigWagBE.getLevel() != null){
            vc.setOverlay(OverlayTexture.NO_OVERLAY);
            vc.setLight(packedLight);
        }

        if(wigWagBE.signalDesignType == 127){// wigwag with pole
            this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                    manager.getModel(wigwagArmPole), 1.0f, 1.0f, 1.0f,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);

            // render wigwagbase
            poseStack.translate(0,0.8,-1.2);
            this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                    manager.getModel(wigwagArmBase), 1.0f, 1.0f, 1.0f,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);

            poseStack.translate(-0.09,-1.0,1.0);
            poseStack.rotateAround(Axis.YP.rotationDegrees(90),0f,0f,0f);

            if(blockRenderer != null && wigWagBE.getLevel() != null){
                if(wigWagBE.getBlockState().getValue(BlockStateProperties.POWERED)){
                    poseStack.rotateAround(Axis.ZP.rotationDegrees(wigWagBE.swingAngle * Mth.sin(Util.getMillis() / 285.0f) + 5.0f),0.5f,1.35f,0.5f);
                    this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                            manager.getModel(wigwagArmOn), 1.0f, 1.0f, 1.0f,
                            Utilities.getLightLevel(2),
                            OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);
                }
                else{
                    if(wigWagBE.swingAngle == 0.0f){
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(0),0.5f,1.35f,0.5f);
                    }
                    else{
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(wigWagBE.swingAngle * Mth.sin(Util.getMillis() / 285.0f) + 5.0f),0.5f,1.35f,0.5f);
                    }
                    this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                            manager.getModel(wigwagArm), 1.0f, 1.0f, 1.0f,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);
                }
            }
        }
        else if(wigWagBE.signalDesignType == 64){
            float offsetYRotation = 0.76f;
            // base with special shape
            poseStack.translate(0,0,0);
            this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                    manager.getModel(wigwagArmBaseTwoRing), 1.0f, 1.0f, 1.0f,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);

            poseStack.rotateAround(Axis.YP.rotationDegrees(90),0.5f,0.5f,0.5f);
            poseStack.translate(0,0.35,0.26);
            poseStack.scale(1.0f,1.0f,0.7f);

            // wig wag arm
            if(blockRenderer != null && wigWagBE.getLevel() != null){
                if(wigWagBE.getBlockState().getValue(BlockStateProperties.POWERED)){
                    poseStack.rotateAround(Axis.ZP.rotationDegrees(wigWagBE.swingAngle * Mth.sin(Util.getMillis() / 285.0f) + 5.0f),0.5f,offsetYRotation,0.5f);
                    this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                            manager.getModel(wigwagArmOn), 1.0f, 1.0f, 1.0f,
                            Utilities.getLightLevel(2),
                            OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);
                }
                else{
                    if(wigWagBE.swingAngle == 0.0f){
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(0),0.5f,offsetYRotation,0.5f);
                    }
                    else{
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(wigWagBE.swingAngle * Mth.sin(Util.getMillis() / 285.0f) + 5.0f),0.5f,offsetYRotation,0.5f);
                    }
                    this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                            manager.getModel(wigwagArm), 1.0f, 1.0f, 1.0f,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);
                }
            }
        }
        else if(wigWagBE.signalDesignType <= (byte)16){
            // only the base for other kinds of wigwags
            poseStack.translate(0,0,0);
            this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                    manager.getModel(wigwagArmBaseStandalone), 1.0f, 1.0f, 1.0f,
                    packedLight,
                    OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);

            // wig wag arm
            poseStack.translate(-0.22,0.8,0.15);
            poseStack.rotateAround(Axis.YP.rotationDegrees(270),0f,0f,0f);
            poseStack.rotateAround(Axis.XP.rotationDegrees(180),0f,0f,0f);

            poseStack.scale(0.7f,0.7f,0.7f);

            if(blockRenderer != null && wigWagBE.getLevel() != null){
                if(wigWagBE.getBlockState().getValue(BlockStateProperties.POWERED)){
                    poseStack.rotateAround(Axis.ZP.rotationDegrees(wigWagBE.swingAngle * Mth.sin(Util.getMillis() / 285.0f) + 5.0f),0.5f,0.85f,0.5f);
                    this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                            manager.getModel(wigwagArmWeightedOn), 1.0f, 1.0f, 1.0f,
                            Utilities.getLightLevel(2),
                            OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);
                }
                else{
                    if(wigWagBE.swingAngle == 0.0f){
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(0),0.5f,0.85f,0.5f);
                    }
                    else{
                        poseStack.rotateAround(Axis.ZP.rotationDegrees(wigWagBE.swingAngle * Mth.sin(Util.getMillis() / 285.0f) + 5.0f),0.5f,0.85f,0.5f);
                    }
                    this.blockRenderer.renderModel(poseStack.last(), multiBufferSource.getBuffer(Sheets.solidBlockSheet()), null,
                            manager.getModel(wigwagArmWeighted), 1.0f, 1.0f, 1.0f,
                            packedLight,
                            OverlayTexture.NO_OVERLAY,ModelData.EMPTY,RenderType.SOLID);
                }
            }
        }

        poseStack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(WigWagBE blockEntity){
        return new AABB(blockEntity.getBlockPos().getX() - 2, blockEntity.getBlockPos().getY(), blockEntity.getBlockPos().getZ() - 2,
                blockEntity.getBlockPos().getX() + 2, blockEntity.getBlockPos().getY() + 2, blockEntity.getBlockPos().getZ() + 2);
    }

    @Override
    public boolean shouldRender(WigWagBE be, Vec3 vec3) {
        return Vec3.atCenterOf(be.getBlockPos()).multiply(2.0, 2.0, 2.0)
                .closerThan(vec3.multiply(2.0, 2.0, 2.0), (double)this.getViewDistance());
    }

    @Override
    public int getViewDistance() {
        return 82;
    }

    @Override
    public boolean shouldRenderOffScreen(WigWagBE blockEntity){
        return true;
    }
}
