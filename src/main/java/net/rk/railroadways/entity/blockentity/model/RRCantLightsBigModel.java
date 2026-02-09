package net.rk.railroadways.entity.blockentity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.rk.railroadways.block.custom.RailroadCrossingCantileverLights;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingCantLightsBE;

public class RRCantLightsBigModel extends Model {
    public static final ModelLayerLocation DEFAULT_TEXTURE = new ModelLayerLocation(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_cantilever_lights_off.png"),"main");
    private final ModelPart main;
    private final ModelPart lights_holder;
    private final ModelPart frontlightsconnector;
    private final ModelPart frontrightlight;
    private final ModelPart frontleftlight;
    private final ModelPart backlightsconnector;
    private final ModelPart backleftlight;
    private final ModelPart backrightlight;

    public RRCantLightsBigModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("main");
        this.lights_holder = this.main.getChild("lights");
        this.frontlightsconnector = this.lights_holder.getChild("frontlightsconnector");
        this.frontrightlight = this.lights_holder.getChild("frontrightlight");
        this.frontleftlight = this.lights_holder.getChild("frontleftlight");
        this.backlightsconnector = this.lights_holder.getChild("backlightsconnector");
        this.backleftlight = this.lights_holder.getChild("backleftlight");
        this.backrightlight = this.lights_holder.getChild("backrightlight");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, -2.0F, 5.0F, 16.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-8.0F, -2.0F, -7.0F, 16.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, 7.0F, -8.0F, 16.0F, 1.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(-8.0F, -8.0F, -8.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(4, 40).addBox(-8.0F, -8.0F, 7.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 40).addBox(7.0F, -8.0F, 7.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 40).addBox(7.0F, -8.0F, -8.0F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-7.0F, -8.0F, 7.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 29).addBox(-7.0F, -8.0F, -8.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition lights = main.addOrReplaceChild("lights", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition frontlightsconnector = lights.addOrReplaceChild("frontlightsconnector", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -7.0F));

        PartDefinition frontrightlightholder_r1 = frontlightsconnector.addOrReplaceChild("frontrightlightholder_r1", CubeListBuilder.create().texOffs(16, 46).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, -0.5F, 0.0F, 0.4363F, 0.0F));

        PartDefinition frontleftlightholder_r1 = frontlightsconnector.addOrReplaceChild("frontleftlightholder_r1", CubeListBuilder.create().texOffs(32, 43).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, -0.5F, 0.0F, -0.4363F, 0.0F));

        PartDefinition frontrightlight = lights.addOrReplaceChild("frontrightlight", CubeListBuilder.create().texOffs(36, 26).addBox(-3.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 31).addBox(-3.0F, -0.5F, 1.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -0.5F, -12.0F));

        PartDefinition frontleftlight = lights.addOrReplaceChild("frontleftlight", CubeListBuilder.create().texOffs(32, 40).addBox(-3.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 17).addBox(-3.0F, -0.5F, 1.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -0.5F, -12.0F));

        PartDefinition backlightsconnector = lights.addOrReplaceChild("backlightsconnector", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 0.0F, 7.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition rightlightholder_r1 = backlightsconnector.addOrReplaceChild("rightlightholder_r1", CubeListBuilder.create().texOffs(30, 46).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition leftlightholder_r1 = backlightsconnector.addOrReplaceChild("leftlightholder_r1", CubeListBuilder.create().texOffs(46, 43).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition backleftlight = lights.addOrReplaceChild("backleftlight", CubeListBuilder.create().texOffs(18, 31).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 40).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 9.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition backrightlight = lights.addOrReplaceChild("backrightlight", CubeListBuilder.create().texOffs(36, 31).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 43).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 9.5F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelPart getMain() {
        return main;
    }

    public ModelPart getBackleftlight() {
        return backleftlight;
    }

    public ModelPart getBackrightlight() {
        return backrightlight;
    }

    public ModelPart getBacklightsconnector() {
        return backlightsconnector;
    }

    public ModelPart getLights_holder() {
        return lights_holder;
    }

    public ModelPart getFrontleftlight() {
        return frontleftlight;
    }

    public ModelPart getFrontrightlight() {
        return frontrightlight;
    }

    public ModelPart getFrontlightsconnector() {
        return frontlightsconnector;
    }

    public void setupAnim(RailroadCrossingCantLightsBE lights){
        frontleftlight.yRot = lights.frontLeftAngle;
        frontrightlight.yRot = lights.frontRightAngle;
        backleftlight.yRot = lights.backLeftAngle;
        backrightlight.yRot = lights.backRightAngle;
        main.y = 8.0f;

        if(lights.getBlockState().getValue(RailroadCrossingCantileverLights.FACING) == Direction.NORTH || lights.getBlockState().getValue(RailroadCrossingCantileverLights.FACING) == Direction.SOUTH){
            main.yRot = 0;
        }
        else{
            main.yRot = 1.57000000f;
        }

        main.xRot = Mth.PI;
        lights_holder.y = 0.0f;
        lights_holder.yRot = lights.yAngle;
        if(lights.showFrontLights){
            frontleftlight.visible = true;
            frontrightlight.visible = true;
            frontlightsconnector.visible = true;
        }
        else{
            frontleftlight.visible = false;
            frontrightlight.visible = false;
            frontlightsconnector.visible = false;
        }
        //
        if(lights.showBackLights){
            backleftlight.visible = true;
            backrightlight.visible = true;
            backlightsconnector.visible = true;
        }
        else{
            backleftlight.visible = false;
            backrightlight.visible = false;
            backlightsconnector.visible = false;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
    }
}
