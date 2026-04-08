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

public class PoleWithCrossingStopLightVerticalModel extends Model {
    public final ModelPart vertical_signage;
    public final ModelPart letter_s;
    public final ModelPart letter_t;
    public final ModelPart letter_o;
    public final ModelPart letter_p;
    public final ModelPart main;

    public static final ModelLayerLocation VERTICAL_STOP_LAYER = new ModelLayerLocation(ResourceLocation.parse("thingamajigsrailroadways:textures/entity/pole_with_lettered_stop_vertical_off.png"), "main");

    public PoleWithCrossingStopLightVerticalModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("main");
        this.vertical_signage = root.getChild("vertical_signage");
        this.letter_s = this.vertical_signage.getChild("letter_s");
        this.letter_t = this.vertical_signage.getChild("letter_t");
        this.letter_o = this.vertical_signage.getChild("letter_o");
        this.letter_p = this.vertical_signage.getChild("letter_p");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition vertical_signage = partdefinition.addOrReplaceChild("vertical_signage", CubeListBuilder.create().texOffs(0, 20).addBox(-1.0F, -7.0F, -3.0F, 3.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition letter_s = vertical_signage.addOrReplaceChild("letter_s", CubeListBuilder.create().texOffs(26, 0).addBox(-2.0F, -3.625F, -0.875F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 0).addBox(1.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 0).addBox(-2.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(0.0F, -4.625F, -1.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(0.0F, 8.375F, -1.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(-2.0F, 8.375F, -1.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(-2.0F, -4.625F, -1.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.375F, -1.125F));

        PartDefinition letter_t = vertical_signage.addOrReplaceChild("letter_t", CubeListBuilder.create().texOffs(26, 5).addBox(-2.0F, -3.625F, -0.875F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 5).addBox(1.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 5).addBox(-2.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 3).addBox(-1.0F, -3.625F, -1.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.375F, -1.125F));

        PartDefinition letter_o = vertical_signage.addOrReplaceChild("letter_o", CubeListBuilder.create().texOffs(26, 10).addBox(-2.0F, -3.625F, -0.875F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 10).addBox(1.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 10).addBox(-2.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 6).addBox(-1.0F, -3.625F, -1.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.625F, -1.125F));

        PartDefinition letter_p = vertical_signage.addOrReplaceChild("letter_p", CubeListBuilder.create().texOffs(26, 15).addBox(-2.0F, -3.625F, -0.875F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(14, 15).addBox(1.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(9, 15).addBox(-2.0F, -3.625F, -1.875F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(19, 9).addBox(-1.0F, -3.625F, -1.875F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.625F, -1.125F));

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public void setupAnim(PoleWithCrossingStopLightBE be){
        main.y = 8.0f;
        main.xRot = Mth.PI;
        vertical_signage.xRot = Mth.PI;
        vertical_signage.yRot = Utilities.degreesToRadians(be.yAngle);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
        this.vertical_signage.render(poseStack,vertexConsumer,i,i1);
    }

    public ModelPart getVerticalSignage() {
        return vertical_signage;
    }
}
