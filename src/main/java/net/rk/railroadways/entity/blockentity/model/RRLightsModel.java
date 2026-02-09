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
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingLightsBE;

public class RRLightsModel extends Model{
    public static final ModelLayerLocation LIGHTS_TEXTURE_LOCATION = new ModelLayerLocation(
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/lights_off.png"), "main");

    private final ModelPart main;
    private final ModelPart lights_holder;
    private final ModelPart front_right;
    private final ModelPart front_left;
    private final ModelPart back_left;
    private final ModelPart back_right;


    public RRLightsModel(ModelPart root){
        super(RenderType::entityTranslucent);
        this.main = root.getChild("pole");
        this.lights_holder = main.getChild("lights");
        this.front_left = lights_holder.getChild("lightfrontleft");
        this.front_right = lights_holder.getChild("lightfrontright");
        this.back_left = lights_holder.getChild("lightbackleft");
        this.back_right = lights_holder.getChild("lightbackright");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition pole = partdefinition.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition lights = pole.addOrReplaceChild("lights", CubeListBuilder.create().texOffs(0, 27).addBox(-8.0F, -1.0F, -1.0F, 16.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-2.0F, -1.0F, 1.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition lightfrontright = lights.addOrReplaceChild("lightfrontright", CubeListBuilder.create().texOffs(26, 1).addBox(-2.0F, -0.75F, -1.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 15).addBox(-2.0F, -1.75F, -2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 0.75F, -2.75F));

        PartDefinition lightfrontleft = lights.addOrReplaceChild("lightfrontleft", CubeListBuilder.create().texOffs(26, 8).addBox(-2.0F, -0.75F, -1.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 10).addBox(-2.0F, -1.75F, -2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 0.75F, -2.75F));

        PartDefinition lightbackright = lights.addOrReplaceChild("lightbackright", CubeListBuilder.create().texOffs(26, 15).addBox(-2.0F, -0.75F, -1.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 5).addBox(-2.0F, -1.75F, -2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 0.75F, 2.75F, 0.0F, 3.1416F, 0.0F));

        PartDefinition lightbackleft = lights.addOrReplaceChild("lightbackleft", CubeListBuilder.create().texOffs(11, 20).addBox(-2.0F, -0.75F, -1.25F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(10, 0).addBox(-2.0F, -1.75F, -2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 0.75F, 2.75F, 0.0F, 3.1416F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public ModelPart getLights_holder() {
        return lights_holder;
    }

    public ModelPart getBack_left() {
        return back_left;
    }

    public ModelPart getMain() {
        return main;
    }

    public ModelPart getBack_right() {
        return back_right;
    }

    public ModelPart getFront_left() {
        return front_left;
    }

    public ModelPart getFront_right() {
        return front_right;
    }

    public void setupAnim(RailroadCrossingLightsBE rclbe){
        front_left.yRot = rclbe.frontLeftAngle;
        front_right.yRot = rclbe.frontRightAngle;
        back_left.yRot = rclbe.backLeftAngle;
        back_right.yRot = rclbe.backRightAngle;
        main.y = 0.0f;
        main.xRot = 3.14555111f;
        lights_holder.y = -8.0f;
        lights_holder.yRot = rclbe.yAngle;
        if(rclbe.showFrontLights){
            front_left.visible = true;
            front_right.visible = true;
        }
        else{
            front_left.visible = false;
            front_right.visible = false;
        }
        //
        if(rclbe.showBackLights){
            back_left.visible = true;
            back_right.visible = true;
        }
        else{
            back_left.visible = false;
            back_right.visible = false;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
    }
}
