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
import net.rk.railroadways.entity.blockentity.custom.PoleWithCrossingStopLightBE;
import net.rk.railroadways.util.Utilities;

public class PoleWithCrossingStopLightHorizontalModel extends Model {
    public static final ModelLayerLocation HORIZONTAL_STOP_LAYER = new ModelLayerLocation(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_horizontal_off.png"), "main");
    public ModelPart horizontalSignage;
    public ModelPart main;

    public PoleWithCrossingStopLightHorizontalModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("bb_main");
        this.horizontalSignage = root.getChild("horizontal_signage");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition horizontal_signage = partdefinition.addOrReplaceChild("horizontal_signage", CubeListBuilder.create().texOffs(0, 6).addBox(-7.0F, -6.0F, -4.0F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 11).addBox(-8.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.0F, -6.0F, -3.0F, 16.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(33, 6).addBox(-7.0F, -5.0F, -4.0F, 14.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 11).addBox(7.0F, -6.0F, -4.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 11).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-6.0F, -14.0F, -2.0F, 12.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void setupAnim(PoleWithCrossingStopLightBE pole){
        main.y = 8.0f;
        main.xRot = Mth.PI;
        horizontalSignage.xRot = Mth.PI;
        horizontalSignage.yRot = Utilities.degreesToRadians(pole.yAngle);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
        this.horizontalSignage.render(poseStack,vertexConsumer,i,i1);
    }

}
