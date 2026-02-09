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
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingArmWithLights;

public class RRArmLightsModel extends Model {
    public static final ModelLayerLocation RAILROAD_CROSSING_ARM_WITH_LIGHTS = new ModelLayerLocation(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/railroad_arm_no_light.png"), "main");
    private final ModelPart main;
    private final ModelPart gate;
    private final ModelPart arm;

    public RRArmLightsModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.main = root.getChild("main");
        this.gate = this.main.getChild("gate");
        this.arm = this.gate.getChild("arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(9, 26).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(21, 17).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-3.0F, -15.0F, -5.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition gate = main.addOrReplaceChild("gate", CubeListBuilder.create().texOffs(51, 44).addBox(-6.0F, -1.0F, -1.0F, 12.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 36).addBox(5.0F, -1.0F, -10.0F, 1.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-6.0F, -1.0F, -10.0F, 1.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(17, 4).addBox(4.0F, -1.0F, 1.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(52, 39).addBox(-5.0F, -1.0F, 11.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -1.0F, 1.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, -3.0F));

        PartDefinition arm = gate.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(0, 1).addBox(-0.5F, -1.0F, 13.0F, 1.0F, 2.0F, 48.0F, new CubeDeformation(0.0F))
                .texOffs(12, 37).addBox(-0.5F, -2.0F, 16.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(19, 41).addBox(-0.5F, -2.0F, 37.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 41).addBox(-0.5F, -2.0F, 59.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 100, 52);
    }

    public ModelPart getMain(){
        return this.main;
    }

    public ModelPart getArm(){
        return this.arm;
    }

    public ModelPart getGate(){
        return this.gate;
    }

    public void setupAnim(RailroadCrossingArmWithLights rcbe) {
        arm.zScale = rcbe.armLength;
        arm.z = rcbe.armGateOffsetZ;
        main.yRot = rcbe.yAngle;
        gate.xRot = rcbe.armAngle; // reversed
        main.zRot = 0;
        main.xRot = Mth.PI;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
    }
}
