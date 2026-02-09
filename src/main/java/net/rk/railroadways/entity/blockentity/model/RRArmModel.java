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
import net.rk.railroadways.entity.blockentity.custom.RailroadCrossingBE;

public class RRArmModel extends Model{
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/rr_arm.png"), "main");
    private final ModelPart main;
    private final ModelPart gate;
    private final ModelPart barrier;

    public RRArmModel(ModelPart root){
        super(RenderType::entityTranslucent);
        this.main = root.getChild("base");
        this.gate = main.getChild("gate");
        this.barrier = gate.getChild("barrier");
    }

    public static LayerDefinition createBodyLayer(){
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create()
                .texOffs(20, 24).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, 7.0F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(24, 12).addBox(-3.0F, 5.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(28, 24).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition gate = base.addOrReplaceChild("gate", CubeListBuilder.create()
                .texOffs(0, 24).addBox(-4.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-6.0F, -1.0F, -2.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(4.0F, -1.0F, -2.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 45).addBox(3.0F, -1.0F, -6.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(24, 38).addBox(-4.0F, -1.0F, -6.0F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r1 = gate.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(12, 38).addBox(-0.5F, -1.2F, -5.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, 0.0F, -5.5F, 0.0F, -0.6545F, 0.0F));

        PartDefinition cube_r2 = gate.addOrReplaceChild("cube_r2", CubeListBuilder.create()
                .texOffs(0, 38).addBox(-0.5F, -1.15F, -5.5F, 1.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, 0.0F, -5.5F, 0.0F, 0.6545F, 0.0F));

        PartDefinition barrier = gate.addOrReplaceChild("barrier", CubeListBuilder.create()
                .texOffs(55, 53).addBox(-0.5F, -1.1F, -9.0F, 1.0F, 2.0F, -10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition,64,64);
    }

    public void setupAnim(RailroadCrossingBE rcbe){
        barrier.zScale = rcbe.armLength;
        barrier.z = rcbe.armGateOffsetZ;
        main.yRot = rcbe.yAngle;
        gate.xRot = rcbe.armAngle * -1;
        main.zRot = 0;
        main.xRot = Mth.PI;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2){
        this.main.render(poseStack,vertexConsumer,i,i1,i2);
    }

    public ModelPart getBarrier() {
        return barrier;
    }

    public ModelPart getGate() {
        return gate;
    }

    public ModelPart getMain() {
        return main;
    }
}
