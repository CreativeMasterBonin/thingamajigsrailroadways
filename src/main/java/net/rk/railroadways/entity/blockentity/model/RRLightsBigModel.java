package net.rk.railroadways.entity.blockentity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingLightsBE;

public class RRLightsBigModel extends Model{
    public static final ModelLayerLocation DEFAULT_TEXTURE = new ModelLayerLocation(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/big_lights_off.png"), "main");
    private final ModelPart main;
    private final ModelPart lights_holder;
    private final ModelPart frontlightsconnector;
    private final ModelPart front_right;
    private final ModelPart front_left;
    private final ModelPart backlightsconnector;
    private final ModelPart back_left;
    private final ModelPart back_right;

    public RRLightsBigModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.main = root.getChild("main");
        this.lights_holder = main.getChild("lights_holder");
        this.frontlightsconnector = this.lights_holder.getChild("frontlightsconnector");
        this.front_right = this.lights_holder.getChild("frontrightlight");
        this.front_left = this.lights_holder.getChild("frontleftlight");
        this.backlightsconnector = this.lights_holder.getChild("backlightsconnector");
        this.back_left = this.lights_holder.getChild("backleftlight");
        this.back_right = this.lights_holder.getChild("backrightlight");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(18, 9).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition lights_holder = main.addOrReplaceChild("lights_holder",CubeListBuilder.create().texOffs(0,0).addBox(0,0,0,0,0,0,new CubeDeformation(0.0f)),PartPose.offset(0,0,0));

        PartDefinition frontlightsconnector = lights_holder.addOrReplaceChild("frontlightsconnector", CubeListBuilder.create().texOffs(0, 30).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

        PartDefinition frontrightlightholder_r1 = frontlightsconnector.addOrReplaceChild("frontrightlightholder_r1", CubeListBuilder.create().texOffs(26, 24).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, -0.5F, 0.0F, 0.4363F, 0.0F));

        PartDefinition frontleftlightholder_r1 = frontlightsconnector.addOrReplaceChild("frontleftlightholder_r1", CubeListBuilder.create().texOffs(26, 21).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, -0.5F, 0.0F, -0.4363F, 0.0F));

        PartDefinition frontrightlight = lights_holder.addOrReplaceChild("frontrightlight", CubeListBuilder.create().texOffs(26, 9).addBox(-3.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -0.5F, 1.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -0.5F, -7.0F));

        PartDefinition frontleftlight = lights_holder.addOrReplaceChild("frontleftlight", CubeListBuilder.create().texOffs(26, 15).addBox(-3.0F, -0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-3.0F, -0.5F, 1.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -0.5F, -7.0F));

        PartDefinition backlightsconnector = lights_holder.addOrReplaceChild("backlightsconnector", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 0.0F, 3.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition backconnector_r1 = backlightsconnector.addOrReplaceChild("backconnector_r1", CubeListBuilder.create().texOffs(28, 27).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 1.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition rightlightholder_r1 = backlightsconnector.addOrReplaceChild("rightlightholder_r1", CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition leftlightholder_r1 = backlightsconnector.addOrReplaceChild("leftlightholder_r1", CubeListBuilder.create().texOffs(14, 27).addBox(-3.0F, -0.5F, -0.5F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition backleftlight = lights_holder.addOrReplaceChild("backleftlight", CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(26, 12).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 5.5F, 0.0F, 3.1416F, 0.0F));

        PartDefinition backrightlight = lights_holder.addOrReplaceChild("backrightlight", CubeListBuilder.create().texOffs(18, 0).addBox(-3.0F, -3.0F, -1.5F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(26, 18).addBox(-3.0F, -3.0F, -3.5F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 5.5F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 42, 36);
    }

    public ModelPart getFront_right() {
        return front_right;
    }

    public ModelPart getFront_left() {
        return front_left;
    }

    public ModelPart getBack_right() {
        return back_right;
    }

    public ModelPart getBack_left() {
        return back_left;
    }

    public ModelPart getLights_holder() {
        return lights_holder;
    }

    public ModelPart getMain() {
        return main;
    }

    public ModelPart getFrontlightsconnector() {
        return frontlightsconnector;
    }

    public ModelPart getBacklightsconnector() {
        return backlightsconnector;
    }

    public void setupAnim(RailroadCrossingLightsBE lights){
        front_left.yRot = lights.frontLeftAngle;
        front_right.yRot = lights.frontRightAngle;
        back_left.yRot = lights.backLeftAngle;
        back_right.yRot = lights.backRightAngle;
        main.y = 8.0f;
        main.xRot = Mth.PI;
        lights_holder.y = 0.0f;
        lights_holder.yRot = lights.yAngle;
        if(lights.showFrontLights){
            front_left.visible = true;
            front_right.visible = true;
            frontlightsconnector.visible = true;
        }
        else{
            front_left.visible = false;
            front_right.visible = false;
            frontlightsconnector.visible = false;
        }
        //
        if(lights.showBackLights){
            back_left.visible = true;
            back_right.visible = true;
            backlightsconnector.visible = true;
        }
        else{
            back_left.visible = false;
            back_right.visible = false;
            backlightsconnector.visible = false;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
    }
}
