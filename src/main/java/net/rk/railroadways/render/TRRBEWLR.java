package net.rk.railroadways.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.rk.railroadways.block.TRRBlocks;
import net.rk.railroadways.block.custom.EnhancedDirectionalCrossingLight;
import net.rk.railroadways.block.custom.RotatingCrossbuckBlock;
import net.rk.railroadways.entity.blockentity.custom.*;
import net.rk.railroadways.entity.blockentity.model.*;
import net.rk.railroadways.util.Utilities;
import org.joml.Vector3f;

public class TRRBEWLR extends BlockEntityWithoutLevelRenderer{
    public BritRailwayLightsBE britRailwayLightsBE;
    public DualRailwayLightsBE dualRailwayLightsBE;
    public DynamicSignBE dynamicSignBE;
    public RailroadCrossingArmWithLights railroadCrossingArmWithLights;
    public RailroadCrossingBE railroadCrossingBE;
    public RailroadCrossingCantLightsBE railroadCrossingCantLightsBE;
    public RailroadCrossingLightsBE railroadCrossingLightsBE;
    public TriRailwayLightsBE triRailwayLightsBE;
    public PoleWithCrossingStopLightBE poleWithCrossingStopLightBE;
    public MultipurposeSignBE multipurposeSignBE;
    public EnhancedDirectionalCrossingLightBE enhancedDirectionalCrossingLightBE;

    public BritRRLightsModel britRRLightsModel;
    public DualLightsModel dualLightsModel;
    public DynamicSignModel dynamicSignModel;
    public DynamicSignModel dynamicSignModelLadder;
    public RRArmLightsModel rrArmLightsModel;
    public RRArmModel rrArmModel;
    public RRCantLightsBigModel rrCantLightsBigModel;
    public RRCantLightsModel rrCantLightsModel;
    public RRLightsBigModel rrLightsBigModel;
    public RRLightsModel rrLightsModel;
    public TriLightsModel triLightsModel;
    public PoleWithCrossingStopLightVerticalModel poleWithCrossingStopLightVerticalModel;
    public MultipurposeSignModel multipurposeSignModel;
    private static final BlockPos ZERO = new BlockPos(0,0,0);

    public TRRBEWLR(BlockEntityRenderDispatcher dispatcher, EntityModelSet set) {
        super(dispatcher,set);
        this.britRailwayLightsBE = new BritRailwayLightsBE(ZERO,TRRBlocks.BRITISH_RAILWAY_LIGHTS.get().defaultBlockState());
        this.dualRailwayLightsBE = new DualRailwayLightsBE(ZERO,TRRBlocks.DUAL_RAILWAY_LIGHTS.get().defaultBlockState());
        this.dynamicSignBE = new DynamicSignBE(ZERO,TRRBlocks.CROSSBUCK.get().defaultBlockState());
        this.railroadCrossingArmWithLights = new RailroadCrossingArmWithLights(ZERO,TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get().defaultBlockState());
        this.railroadCrossingBE = new RailroadCrossingBE(ZERO,TRRBlocks.RAILROAD_CROSSING_ARM.get().defaultBlockState());
        this.railroadCrossingCantLightsBE = new RailroadCrossingCantLightsBE(ZERO,TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get().defaultBlockState());
        this.railroadCrossingLightsBE = new RailroadCrossingLightsBE(ZERO,TRRBlocks.RAILROAD_CROSSING_LIGHTS.get().defaultBlockState());
        this.triRailwayLightsBE = new TriRailwayLightsBE(ZERO,TRRBlocks.TRI_RAILWAY_LIGHTS.get().defaultBlockState());
        this.poleWithCrossingStopLightBE = new PoleWithCrossingStopLightBE(ZERO,TRRBlocks.POLE_WITH_CROSSING_STOP_LIGHT.get().defaultBlockState());
        this.multipurposeSignBE = new MultipurposeSignBE(ZERO,TRRBlocks.MULTIPURPOSE_SIGN.get().defaultBlockState());
        this.enhancedDirectionalCrossingLightBE = new EnhancedDirectionalCrossingLightBE(ZERO,TRRBlocks.ENHANCED_DIRECTIONAL_CROSSING_LIGHT.get().defaultBlockState());

        this.britRRLightsModel = new BritRRLightsModel(set.bakeLayer(BritRRLightsModel.BRIT_LIGHTS_OFF_LOC));
        this.dualLightsModel = new DualLightsModel(set.bakeLayer(DualLightsModel.DUAL_TEXTURE_LOC));
        this.dynamicSignModel = new DynamicSignModel(set.bakeLayer(DynamicSignModel.SIGN_TEXTURE_LOCATION));
        this.dynamicSignModelLadder = new DynamicSignModel(set.bakeLayer(DynamicSignModel.SIGN_TEXTURE_LOCATION));
        this.rrArmLightsModel = new RRArmLightsModel(set.bakeLayer(RRArmLightsModel.RAILROAD_CROSSING_ARM_WITH_LIGHTS));
        this.rrArmModel = new RRArmModel(set.bakeLayer(RRArmModel.LAYER_LOCATION));
        this.rrCantLightsBigModel = new RRCantLightsBigModel(set.bakeLayer(RRCantLightsBigModel.DEFAULT_TEXTURE));
        this.rrCantLightsModel = new RRCantLightsModel(set.bakeLayer(RRCantLightsModel.CANT_LIGHTS_TEXTURE_LOCATION));
        this.rrLightsBigModel = new RRLightsBigModel(set.bakeLayer(RRLightsBigModel.DEFAULT_TEXTURE));
        this.rrLightsModel = new RRLightsModel(set.bakeLayer(RRLightsModel.LIGHTS_TEXTURE_LOCATION));
        this.triLightsModel = new TriLightsModel(set.bakeLayer(TriLightsModel.TRI_TEXTURE_LOC));
        this.poleWithCrossingStopLightVerticalModel = new PoleWithCrossingStopLightVerticalModel(set.bakeLayer(PoleWithCrossingStopLightVerticalModel.VERTICAL_STOP_LAYER));
        this.multipurposeSignModel = new MultipurposeSignModel(set.bakeLayer(MultipurposeSignModel.DEFAULT_MULTIPURPOSE_TEXTURE));
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        EntityModelSet set = Minecraft.getInstance().getEntityModels();
        this.britRRLightsModel = new BritRRLightsModel(set.bakeLayer(BritRRLightsModel.BRIT_LIGHTS_OFF_LOC));
        this.dualLightsModel = new DualLightsModel(set.bakeLayer(DualLightsModel.DUAL_TEXTURE_LOC));
        this.dynamicSignModel = new DynamicSignModel(set.bakeLayer(DynamicSignModel.SIGN_TEXTURE_LOCATION));
        this.dynamicSignModelLadder = new DynamicSignModel(set.bakeLayer(DynamicSignModel.SIGN_TEXTURE_LOCATION));
        this.rrArmLightsModel = new RRArmLightsModel(set.bakeLayer(RRArmLightsModel.RAILROAD_CROSSING_ARM_WITH_LIGHTS));
        this.rrArmModel = new RRArmModel(set.bakeLayer(RRArmModel.LAYER_LOCATION));
        this.rrCantLightsBigModel = new RRCantLightsBigModel(set.bakeLayer(RRCantLightsBigModel.DEFAULT_TEXTURE));
        this.rrCantLightsModel = new RRCantLightsModel(set.bakeLayer(RRCantLightsModel.CANT_LIGHTS_TEXTURE_LOCATION));
        this.rrLightsBigModel = new RRLightsBigModel(set.bakeLayer(RRLightsBigModel.DEFAULT_TEXTURE));
        this.rrLightsModel = new RRLightsModel(set.bakeLayer(RRLightsModel.LIGHTS_TEXTURE_LOCATION));
        this.triLightsModel = new TriLightsModel(set.bakeLayer(TriLightsModel.TRI_TEXTURE_LOC));
        this.poleWithCrossingStopLightVerticalModel = new PoleWithCrossingStopLightVerticalModel(set.bakeLayer(PoleWithCrossingStopLightVerticalModel.VERTICAL_STOP_LAYER));
        this.multipurposeSignModel = new MultipurposeSignModel(set.bakeLayer(MultipurposeSignModel.DEFAULT_MULTIPURPOSE_TEXTURE));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        VertexConsumer vc;
        if(britRailwayLightsBE != null){
            britRailwayLightsBE.ticks += 1;
            if(britRailwayLightsBE.ticks >= 60){
                britRailwayLightsBE.ticks = 0;
            }
        }
        if(multipurposeSignBE != null){
            multipurposeSignBE.ticks += 1;
            if(multipurposeSignBE.ticks >= 60){
                multipurposeSignBE.ticks = 0;
            }
        }
        if(stack.is(TRRBlocks.BRITISH_RAILWAY_LIGHTS.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(BritRRLightsModel.BRIT_LIGHTS_OFF_LOC.getModel()));
            britRRLightsModel.getLights().y = -1.0f;
            britRRLightsModel.getMain().yRot = 2.55f;
            britRRLightsModel.getLights().zRot = 3.1415f;
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-1.0D,-1.0D,0D);
                    poseStack.mulPose(Axis.XP.rotationDegrees(0));
                    poseStack.mulPose(Axis.YP.rotationDegrees(70));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    britRRLightsModel.getLights().yRot = Utilities.degreesToRadians(210);
                    britRRLightsModel.getLights().y = 25.0f;
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.45D,-1.0D,0D);
                    britRRLightsModel.getLights().yRot = Utilities.degreesToRadians(210);
                    britRRLightsModel.getLights().y = 25.0f;
                }
            }
            else{
                if(displayContext == ItemDisplayContext.GUI){
                    britRRLightsModel.getLights().yRot = Utilities.degreesToRadians(210);
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(1.1D,-0.3D,0D);
                    britRRLightsModel.getLights().y = 25.0f;
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    britRRLightsModel.getLights().yRot = Utilities.degreesToRadians(170);
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(1.0D,0.5D,1D);
                    britRRLightsModel.getLights().y = 25.0f;
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    britRRLightsModel.getLights().yRot = Utilities.degreesToRadians(210);
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(1.0D,0.5D,1D);
                    britRRLightsModel.getLights().y = 25.0f;
                }
                else if(displayContext == ItemDisplayContext.HEAD){
                    poseStack.translate(0.5D,1.55D,0.5D);
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    britRRLightsModel.getLights().y = 1.0f;
                    britRRLightsModel.getLights().xRot = Utilities.degreesToRadians(180);
                    if(britRailwayLightsBE != null){
                        float a = Mth.sin((float)(Util.getMillis() % 2000L) / 2000f * Mth.TWO_PI);
                        britRRLightsModel.getLights().yRot = Mth.lerp(britRailwayLightsBE.ticks,-a,a);
                        if(Mth.sin((float)(Util.getMillis() % 2000L) / 2000f * Mth.TWO_PI) <= 0.5f){
                            vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/brit_on_0.png")));
                        }
                        else{
                            vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/brit_on_1.png")));
                        }
                    }
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.2f,0.2f,0.2f);
                    poseStack.translate(3D,0.5D,2.5D);
                    britRRLightsModel.getLights().y = 25.0f;
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(1.0D,-0.5D,1.2D);
                    britRRLightsModel.getLights().yRot = Utilities.degreesToRadians(0);
                    britRRLightsModel.getMain().z = -1.13f;
                    britRRLightsModel.getLights().z = -1.13f;
                    britRRLightsModel.getLights().y = 25.0f;
                }
            }
            britRRLightsModel.getMain().render(poseStack,vc,packedLight,packedOverlay);
            britRRLightsModel.getLights().render(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.DUAL_RAILWAY_LIGHTS.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(DualLightsModel.DUAL_TEXTURE_LOC.getModel()));

            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.35f,-0.1f,0f);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.5f,-0.1f,0f);
                }
            }
            else{
                if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.4f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.mulPose(Axis.XP.rotationDegrees(35));
                    poseStack.mulPose(Axis.YP.rotationDegrees(135));
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(-0.8f,0.85f,0.55f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.translate(0.1f,0f,-0.12f);
                }
            }
            dualLightsModel.setupAnim(dualRailwayLightsBE);
            dualLightsModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.ENHANCED_DIRECTIONAL_CROSSING_LIGHT.asItem())){
            poseStack.pushPose();

            poseStack.scale(0.35f,0.35f,0.35f);

            boolean glowingEffect = false;

            if(displayContext.firstPerson()){
                glowingEffect = false;
                poseStack.translate(0.95,1.0,0.0);
                if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.0,0,0);
                    poseStack.rotateAround(Axis.YP.rotationDegrees(135),0f,0f,0f);
                    poseStack.translate(-1.2,-1.0,0.3);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-1.0,0,0);
                    poseStack.rotateAround(Axis.YP.rotationDegrees(-135),0f,0f,0f);
                    poseStack.translate(0,-1.0,0);
                }
            }
            else{
                poseStack.translate(0.95,0.75,1.0);
                if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.translate(0.25,0.5,0.25);
                    poseStack.scale(0.35f,0.35f,0.35f);
                    glowingEffect = false;
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.rotateAround(Axis.YP.rotationDegrees(135),0.5f,0f,0.5f);
                    poseStack.rotateAround(Axis.XP.rotationDegrees(10),0.5f,0f,0.5f);
                    poseStack.rotateAround(Axis.ZP.rotationDegrees(5),0.5f,0f,0.5f);
                    glowingEffect = false;
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    glowingEffect = false;
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    glowingEffect = false;
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    glowingEffect = false;
                }
                else if(displayContext == ItemDisplayContext.HEAD){
                    poseStack.translate(0,2.0,0);
                    glowingEffect = true;
                }
            }

            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(TRRBlocks.VERTICAL_POLE_REDSTONE_RR.get().defaultBlockState(),
                    poseStack,buffer,packedLight,packedOverlay,ModelData.EMPTY,RenderType.SOLID);

            if(glowingEffect){
                Minecraft.getInstance().getBlockRenderer().renderSingleBlock(TRRBlocks.ENHANCED_DIRECTIONAL_CROSSING_LIGHT.get().defaultBlockState()
                                .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.ON)
                                .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.CENTER),poseStack,
                        buffer,Utilities.getLightLevel(2),packedOverlay, ModelData.EMPTY, RenderType.CUTOUT);
            }
            else{
                Minecraft.getInstance().getBlockRenderer().renderSingleBlock(enhancedDirectionalCrossingLightBE.getBlockState()
                                .setValue(EnhancedDirectionalCrossingLight.redLightState,EnhancedDirectionalCrossingLight.RedLightStates.OFF)
                                .setValue(EnhancedDirectionalCrossingLight.orangeLightState, EnhancedDirectionalCrossingLight.OrangeLightStates.OFF),poseStack,
                        buffer,packedLight,packedOverlay, ModelData.EMPTY, RenderType.CUTOUT);
            }

            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.MULTIPURPOSE_SIGN.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png")));
            multipurposeSignModel.setupAnim(multipurposeSignBE);

            String offTexture = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png";
            String onTexture = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png";
            String altOnTexture = "thingamajigsrailroadways:textures/entity/multipurpose_sign_variants/multipurpose_sign.png";
            int interval = 20;
            boolean usesAlternatingTextures = false;
            boolean loadedCustomTextures = false;

            if(stack.has(DataComponents.BLOCK_ENTITY_DATA)){
                CompoundTag tag = stack.get(DataComponents.BLOCK_ENTITY_DATA).copyTag();
                if(tag != null){
                    if(tag.get("off_texture") != null){
                        offTexture = tag.get("off_texture").getAsString();
                        loadedCustomTextures = true;
                    }
                    if(tag.get("on_texture") != null){
                        onTexture = tag.get("on_texture").getAsString();
                        loadedCustomTextures = true;
                    }
                    if(tag.get("alt_on_texture") != null){
                        altOnTexture = tag.get("alt_on_texture").getAsString();
                        loadedCustomTextures = true;
                    }
                    if(tag.get("alt_interval") != null){
                        interval = tag.getInt("alt_interval");
                    }
                    if(tag.get("alternating_textures") != null){
                        usesAlternatingTextures = tag.getBoolean("alternating_textures");
                    }
                }
            }

            if(usesAlternatingTextures){
                float a2 = Mth.sin((float)(Util.getMillis() % 2000L) / 2000f * Mth.TWO_PI);
                if(a2 < 0.0){
                    if(loadedCustomTextures){
                        vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(onTexture + Utilities.imgFileEnding)));
                    }
                    else{
                        vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(onTexture)));
                    }
                }
                else{
                    if(loadedCustomTextures){
                        vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(altOnTexture + Utilities.imgFileEnding)));
                    }
                    else{
                        vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(altOnTexture)));
                    }
                }
            }
            else{
                if(loadedCustomTextures){
                    vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(onTexture + Utilities.imgFileEnding)));
                }
                else{
                    vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(onTexture)));
                }
            }

            multipurposeSignModel.getPole().visible = true;
            multipurposeSignModel.getLcon().visible = false;
            multipurposeSignModel.getRcon().visible = false;
            multipurposeSignModel.getFcon().visible = false;
            multipurposeSignModel.getBcon().visible = true;
            multipurposeSignModel.getSign().y = 0.0f;
            multipurposeSignModel.getGlowingpart().y = 16.0f;
            multipurposeSignModel.getPole().yRot = Utilities.degreesToRadians(180);
            multipurposeSignModel.getGlowingpart().xRot = Mth.PI;
            multipurposeSignModel.getGlowingpart().zRot = Utilities.degreesToRadians(0);
            multipurposeSignModel.getSign().visible = true;

            if(displayContext == ItemDisplayContext.GUI){
                poseStack.translate(0.5,-0.5,0);
                multipurposeSignModel.getGlowingpart().xRot = Utilities.degreesToRadians(180);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(0);
                multipurposeSignModel.getGlowingpart().zRot = Utilities.degreesToRadians(0);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2), OverlayTexture.NO_OVERLAY);
            }
            else if(displayContext == ItemDisplayContext.GROUND){
                poseStack.scale(0.35f,0.35f,0.35f);
                poseStack.translate(1.5,0.5,1.5);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(0);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            else if(displayContext == ItemDisplayContext.HEAD){
                float a = Mth.sin((float)(Util.getMillis() % 2000L) / 2000f * Mth.TWO_PI);
                poseStack.translate(0.5,0.65,0.5);
                poseStack.mulPose(Axis.YP.rotationDegrees((a * 20) + 180));
                poseStack.scale(0.5f,0.5f,0.5f);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(0);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                poseStack.translate(1.3,-0.6,-0.35);
                multipurposeSignModel.getGlowingpart().xRot = Utilities.degreesToRadians(180);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(-55);
                multipurposeSignModel.getGlowingpart().zRot = Utilities.degreesToRadians(-7);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            else if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                poseStack.translate(-0.3,-0.6,-0.35);
                multipurposeSignModel.getGlowingpart().xRot = Utilities.degreesToRadians(180);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(55);
                multipurposeSignModel.getGlowingpart().zRot = Utilities.degreesToRadians(7);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                poseStack.translate(0.5,0.27,0.5);
                poseStack.scale(0.35f,0.35f,0.35f);
                multipurposeSignModel.getGlowingpart().xRot = Utilities.degreesToRadians(180);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(-55);
                multipurposeSignModel.getGlowingpart().zRot = Utilities.degreesToRadians(-7);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                poseStack.translate(0.5,0.27,0.5);
                poseStack.scale(0.35f,0.35f,0.35f);
                multipurposeSignModel.getGlowingpart().xRot = Utilities.degreesToRadians(180);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(55);
                multipurposeSignModel.getGlowingpart().zRot = Utilities.degreesToRadians(7);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            else if(displayContext == ItemDisplayContext.FIXED){
                poseStack.translate(0.5,0.05,0.6);
                poseStack.scale(0.5f,0.5f,0.5f);
                multipurposeSignModel.getPole().visible = false;
                multipurposeSignModel.getGlowingpart().xRot = Utilities.degreesToRadians(180);
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(180);
                multipurposeSignModel.getGlowingpart().zRot = Utilities.degreesToRadians(0);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            else{
                multipurposeSignModel.getGlowingpart().yRot = Utilities.degreesToRadians(0);
                multipurposeSignModel.getGlowingpart().render(poseStack,vc,Utilities.getLightLevel(2),packedOverlay);
                multipurposeSignModel.renderToBuffer(poseStack,vc,Utilities.getLightLevel(2),OverlayTexture.NO_OVERLAY);
            }
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.CROSSBUCK.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/signs/sign_usa_crossbuck.png")));
            dynamicSignModel.setupAnim(dynamicSignBE);

            if(stack.has(DataComponents.BLOCK_ENTITY_DATA)){
                CompoundTag tag = stack.get(DataComponents.BLOCK_ENTITY_DATA).copyTag();
                if(tag != null){
                    if(tag.get("custom_sign") != null){
                        if(tag.get("custom_sign").getAsString().equals("1b")){
                            if(tag.get("custom_texture") != null){
                                vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(tag.get("custom_texture").getAsString())));
                            }
                        }
                        else{
                            if(tag.get("sign_override_loc") != null){
                                vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(tag.get("sign_override_loc").getAsString())));
                            }
                        }
                    }
                    else{
                        if(tag.get("sign_override_loc") != null){
                            vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(tag.get("sign_override_loc").getAsString())));
                        }
                    }
                }
            }

            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    dynamicSignModel.getSignHolder().yRot = -1.4f;
                    dynamicSignModel.getMain().x = -1.0f;
                    dynamicSignModel.getMain().y = -1.0f;
                }
                else{
                    dynamicSignModel.getSignHolder().yRot = 1.4f;
                    dynamicSignModel.getMain().x = 14.0f;
                    dynamicSignModel.getMain().y = -1.0f;
                }
                dynamicSignModel.getMain().render(poseStack,vc,packedLight,packedOverlay);
            }
            else{
                if(displayContext == ItemDisplayContext.GUI){
                    dynamicSignModel.getMain().x = 8f;
                    dynamicSignModel.getMain().y = 0f;
                    dynamicSignModel.getMain().render(poseStack,vc,packedLight,packedOverlay);
                }
                else{
                    if(displayContext == ItemDisplayContext.FIXED){
                        dynamicSignModel.getMain().x = 8f;
                        dynamicSignModel.getMain().y = 0f;
                        dynamicSignModel.getMain().z = 8f;
                        dynamicSignModel.getSignHolder().yRot = 3.1415f;
                    }
                    else if(displayContext == ItemDisplayContext.HEAD){
                        dynamicSignModel.getMain().x = 8f;
                        dynamicSignModel.getMain().y = 0f;
                        dynamicSignModel.getMain().z = 8f;
                        dynamicSignModel.getSignHolder().yRot = 3.1415f;
                    }
                    else if(displayContext == ItemDisplayContext.GROUND){
                        dynamicSignModel.getMain().x = 8f;
                        dynamicSignModel.getMain().y = 6f;
                        dynamicSignModel.getMain().z = 8f;
                        dynamicSignModel.getMain().offsetScale(new Vector3f(-0.75f,-0.75f,-0.75f));
                        dynamicSignModel.getSignHolder().yRot = 3.1415f;
                    }
                    else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                        dynamicSignModel.getMain().x = 8.14f;
                        dynamicSignModel.getMain().y = 2.25f;
                        dynamicSignModel.getMain().z = 7.0f;
                        dynamicSignModel.getSignHolder().yRot = 3.1415f;
                        poseStack.scale(0.5f,0.5f,0.5f);
                        poseStack.translate(0.5f,0.75f,0.65f);
                    }
                    else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                        dynamicSignModel.getMain().x = 8.14f;
                        dynamicSignModel.getMain().y = 2.25f;
                        dynamicSignModel.getMain().z = 7.0f;
                        dynamicSignModel.getSignHolder().yRot = 3.1415f;
                        poseStack.scale(0.5f,0.5f,0.5f);
                        poseStack.translate(0.5f,0.75f,0.65f);
                    }
                    dynamicSignModel.getMain().render(poseStack,vc,packedLight,packedOverlay);
                }
            }
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.CROSSBUCK_WITH_LADDER.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/signs/sign_usa_crossbuck.png")));
            dynamicSignModelLadder.setupAnim(dynamicSignBE);

            if(stack.has(DataComponents.BLOCK_ENTITY_DATA)){
                CompoundTag tag = stack.get(DataComponents.BLOCK_ENTITY_DATA).copyTag();
                if(tag != null){
                    if(tag.get("custom_sign") != null){
                        if(tag.get("custom_sign").getAsString().equals("1b")){
                            if(tag.get("custom_texture") != null){
                                vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(tag.get("custom_texture").getAsString())));
                            }
                        }
                        else{
                            if(tag.get("sign_override_loc") != null){
                                vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(tag.get("sign_override_loc").getAsString())));
                            }
                        }
                    }
                    else{
                        if(tag.get("sign_override_loc") != null){
                            vc = buffer.getBuffer(RenderType.entityCutout(ResourceLocation.parse(tag.get("sign_override_loc").getAsString())));
                        }
                    }
                }
            }

            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    dynamicSignModelLadder.getSignHolder().yRot = -1.4f;
                    dynamicSignModelLadder.getMain().x = -1.0f;
                    dynamicSignModelLadder.getMain().y = -1.0f;
                }
                else{
                    dynamicSignModelLadder.getSignHolder().yRot = 1.4f;
                    dynamicSignModelLadder.getMain().x = 14.0f;
                    dynamicSignModelLadder.getMain().y = -1.0f;
                }
                dynamicSignModelLadder.getMain().render(poseStack,vc,packedLight,packedOverlay);
            }
            else{
                if(displayContext == ItemDisplayContext.GUI){
                    dynamicSignModelLadder.getMain().x = 8f;
                    dynamicSignModelLadder.getMain().y = 0f;
                    dynamicSignModelLadder.getMain().render(poseStack,vc,packedLight,packedOverlay);
                }
                else{
                    if(displayContext == ItemDisplayContext.FIXED){
                        dynamicSignModelLadder.getMain().x = 8f;
                        dynamicSignModelLadder.getMain().y = 0f;
                        dynamicSignModelLadder.getMain().z = 8f;
                        dynamicSignModelLadder.getSignHolder().yRot = 3.1415f;
                    }
                    else if(displayContext == ItemDisplayContext.HEAD){
                        dynamicSignModelLadder.getMain().x = 8f;
                        dynamicSignModelLadder.getMain().y = 0f;
                        dynamicSignModelLadder.getMain().z = 8f;
                        dynamicSignModelLadder.getSignHolder().yRot = 3.1415f;
                    }
                    else if(displayContext == ItemDisplayContext.GROUND){
                        dynamicSignModelLadder.getMain().x = 8f;
                        dynamicSignModelLadder.getMain().y = 6f;
                        dynamicSignModelLadder.getMain().z = 8f;
                        dynamicSignModelLadder.getMain().offsetScale(new Vector3f(-0.75f,-0.75f,-0.75f));
                        dynamicSignModelLadder.getSignHolder().yRot = 3.1415f;
                    }
                    else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                        dynamicSignModelLadder.getMain().x = 8.14f;
                        dynamicSignModelLadder.getMain().y = 2.75f;
                        dynamicSignModelLadder.getMain().z = 7.5f;
                        dynamicSignModelLadder.getSignHolder().yRot = 3.1415f;
                        poseStack.scale(0.5f,0.5f,0.5f);
                        poseStack.translate(0.5f,0.75f,0.65f);
                    }
                    else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                        dynamicSignModelLadder.getMain().x = 8.14f;
                        dynamicSignModelLadder.getMain().y = 2.75f;
                        dynamicSignModelLadder.getMain().z = 7.5f;
                        dynamicSignModelLadder.getSignHolder().yRot = 3.1415f;
                        poseStack.scale(0.5f,0.5f,0.5f);
                        poseStack.translate(0.5f,0.75f,0.65f);
                    }
                    dynamicSignModelLadder.getMain().render(poseStack,vc,packedLight,packedOverlay);
                }
            }

            dynamicSignModelLadder.getMain().render(poseStack,vc,packedLight,packedOverlay);
            if(displayContext == ItemDisplayContext.GROUND){
                poseStack.scale(0.35f,0.35f,0.35f);
                poseStack.translate(1.0f,1.1f,1.0f);
            }
            else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                poseStack.mulPose(Axis.YP.rotationDegrees(35));
                poseStack.translate(0.55f,0.25f,0.0f);
            }
            else if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                poseStack.mulPose(Axis.YP.rotationDegrees(35));
                poseStack.translate(-0.4f,0.2f,-0.55f);
            }
            else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                poseStack.translate(0.35f,0.45f,0.12f);
            }
            else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                poseStack.translate(0.35f,0.45f,0.12f);
            }
            else if(displayContext == ItemDisplayContext.FIXED){
                poseStack.translate(0.01f,0.0f,0.14f);
            }
            else if(displayContext == ItemDisplayContext.GUI){
                poseStack.translate(0.00f,0.0f,-0.7f);
            }
            else{
                poseStack.mulPose(Axis.YP.rotationDegrees(0));
            }
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(
                    TRRBlocks.CROSSBUCK_WITH_LADDER.get().defaultBlockState().setValue(RotatingCrossbuckBlock.FACING,Direction.NORTH),
                    poseStack,buffer,packedLight,packedOverlay, ModelData.EMPTY,RenderType.CUTOUT);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.asItem())){
            poseStack.pushPose();
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.1D,1.2D,-0.2D);
                    poseStack.mulPose(Axis.XP.rotationDegrees(197));
                    poseStack.mulPose(Axis.YP.rotationDegrees(-7));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    rrArmLightsModel.getGate().xRot = 1.0f;
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.3D,1.2D,0.2D);
                    poseStack.mulPose(Axis.XP.rotationDegrees(197));
                    poseStack.mulPose(Axis.YP.rotationDegrees(7));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    rrArmLightsModel.getGate().xRot = 1.0f;
                }
            }
            else{
                if(displayContext == ItemDisplayContext.GUI){
                    poseStack.translate(0.75D,1.0D,0.5D);
                    poseStack.scale(0.4f,0.4f,0.4f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-170));
                    poseStack.mulPose(Axis.YP.rotationDegrees(210));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.translate(0.75D,0.75D,0.5D);
                    poseStack.scale(0.25f,0.25f,0.25f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(270));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.translate(0.5D,0.85D,0.5D);
                    poseStack.scale(0.25f,0.25f,0.25f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.translate(0.5D,0.85D,0.5D);
                    poseStack.scale(0.25f,0.25f,0.25f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.HEAD){
                    poseStack.translate(0.5D,1.55D,0.5D);
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    rrArmLightsModel.getGate().xRot = 1.15f;
                    poseStack.translate(0.5D,0.75D,0.5D);
                    poseStack.scale(0.4f,0.4f,0.4f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
            }
            vc = buffer.getBuffer(RenderType.entityCutout(RRArmLightsModel.RAILROAD_CROSSING_ARM_WITH_LIGHTS.getModel()));
            rrArmLightsModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.RAILROAD_CROSSING_ARM.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(RRArmModel.LAYER_LOCATION.getModel()));
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.1D,1.2D,-0.2D);
                    poseStack.mulPose(Axis.XP.rotationDegrees(197));
                    poseStack.mulPose(Axis.YP.rotationDegrees(-17));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    rrArmModel.getGate().xRot = -90;
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.29D,1.2D,0.1D);
                    poseStack.mulPose(Axis.XP.rotationDegrees(197));
                    poseStack.mulPose(Axis.YP.rotationDegrees(7));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    rrArmModel.getGate().xRot = -90;
                }
            }
            else{
                if(displayContext == ItemDisplayContext.GUI){
                    poseStack.translate(0.6D,1.0D,0.5D);
                    poseStack.scale(0.44f,0.44f,0.44f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-170));
                    poseStack.mulPose(Axis.YP.rotationDegrees(55));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.translate(0.5D,0.65D,0.5D);
                    poseStack.scale(0.25f,0.25f,0.25f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(270));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.translate(0.5D,0.85D,0.5D);
                    poseStack.scale(0.25f,0.25f,0.25f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.translate(0.5D,0.85D,0.5D);
                    poseStack.scale(0.25f,0.25f,0.25f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.HEAD){
                    poseStack.translate(0.5D,1.55D,0.5D);
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    rrArmModel.getGate().xRot = -1.15f;
                    poseStack.translate(0.5D,0.75D,0.5D);
                    poseStack.scale(0.4f,0.4f,0.4f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(-180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                }
            }
            rrArmModel.getMain().render(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(RRCantLightsBigModel.DEFAULT_TEXTURE.getModel()));
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.35f,-0.1f,0f);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.5f,-0.1f,0f);
                }
            }
            else{
                if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.mulPose(Axis.XP.rotationDegrees(35));
                    poseStack.mulPose(Axis.YP.rotationDegrees(135));
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(-0.8f,0.85f,0.55f);
                }
            }
            rrCantLightsBigModel.setupAnim(railroadCrossingCantLightsBE);
            rrCantLightsBigModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(RRCantLightsModel.CANT_LIGHTS_TEXTURE_LOCATION.getModel()));
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.35f,-0.1f,0f);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.5f,-0.1f,0f);
                }
            }
            else{
                if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.mulPose(Axis.XP.rotationDegrees(35));
                    poseStack.mulPose(Axis.YP.rotationDegrees(135));
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(-0.8f,0.85f,0.55f);
                }
            }
            rrCantLightsModel.setupAnim(railroadCrossingCantLightsBE);
            rrCantLightsModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(RRLightsBigModel.DEFAULT_TEXTURE.getModel()));
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.35f,-0.1f,0f);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.5f,-0.1f,0f);
                }
            }
            else{
                if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.mulPose(Axis.XP.rotationDegrees(35));
                    poseStack.mulPose(Axis.YP.rotationDegrees(135));
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(-0.8f,0.85f,0.55f);
                }
            }
            rrLightsBigModel.setupAnim(railroadCrossingLightsBE);
            rrLightsBigModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.RAILROAD_CROSSING_LIGHTS.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(RRLightsModel.LIGHTS_TEXTURE_LOCATION.getModel()));
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.35f,-0.1f,0f);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.5f,-0.1f,0f);
                }
            }
            else{
                if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.mulPose(Axis.XP.rotationDegrees(35));
                    poseStack.mulPose(Axis.YP.rotationDegrees(135));
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(-0.8f,0.85f,0.55f);
                }
            }
            rrLightsModel.setupAnim(railroadCrossingLightsBE);
            rrLightsModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.TRI_RAILWAY_LIGHTS.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(TriLightsModel.TRI_TEXTURE_LOC.getModel()));
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.translate(-0.35f,-0.1f,0f);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.translate(1.5f,-0.1f,0f);
                }
            }
            else{
                if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.4f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.4f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.mulPose(Axis.XP.rotationDegrees(35));
                    poseStack.mulPose(Axis.YP.rotationDegrees(135));
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(-0.8f,0.85f,0.55f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.translate(0.1f,0f,-0.12f);
                }
            }
            triLightsModel.setupAnim(triRailwayLightsBE);
            triLightsModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else if(stack.is(TRRBlocks.POLE_WITH_CROSSING_STOP_LIGHT.asItem())){
            poseStack.pushPose();
            vc = buffer.getBuffer(RenderType.entityCutout(PoleWithCrossingStopLightVerticalModel.VERTICAL_STOP_LAYER.getModel()));
            if(displayContext.firstPerson()){
                if(displayContext == ItemDisplayContext.FIRST_PERSON_LEFT_HAND){
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(0.0f,0.0f,0.4f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(55));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(-4));
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
                else if(displayContext == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND){
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(2.0f,0.0f,0.4f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(-38));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(4));
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
            }
            else{
                if(displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,0.9f,1.5f);
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
                else if(displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,0.9f,1.5f);
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
                else if(displayContext == ItemDisplayContext.HEAD){
                    poseStack.translate(0.5D,1.55D,0.5D);
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.mulPose(Axis.XP.rotationDegrees(180));
                    poseStack.mulPose(Axis.YP.rotationDegrees(0));
                    poseStack.mulPose(Axis.ZP.rotationDegrees(0));
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
                else if(displayContext == ItemDisplayContext.FIXED){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,0.5f,1.4f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
                else if(displayContext == ItemDisplayContext.GUI){
                    poseStack.mulPose(Axis.XP.rotationDegrees(35));
                    poseStack.mulPose(Axis.YP.rotationDegrees(135));
                    poseStack.scale(0.5f,0.5f,0.5f);
                    poseStack.translate(-1.0f,0.0f,0.43f);
                    poseStack.mulPose(Axis.YP.rotationDegrees(180));
                    poseStack.scale(1.5f,1.5f,1.5f);
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
                else if(displayContext == ItemDisplayContext.GROUND){
                    poseStack.scale(0.35f,0.35f,0.35f);
                    poseStack.translate(1.5f,1.0f,1.25f);
                    poleWithCrossingStopLightVerticalModel.setupAnim(poleWithCrossingStopLightBE);
                }
            }
            poleWithCrossingStopLightVerticalModel.renderToBuffer(poseStack,vc,packedLight,packedOverlay);
            poseStack.popPose();
        }
        else{
            super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        }
    }
}
