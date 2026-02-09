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
import net.rk.railroadways.entity.blockentity.custom.TriRailwayLightsBE;

public class TriLightsModel extends Model{
    public static final ModelLayerLocation TRI_TEXTURE_LOC = new ModelLayerLocation(
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/tri_off.png"), "main");

    private final ModelPart lights;
    private final ModelPart main;

    public TriLightsModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("pole");
        this.lights = main.getChild("tri_lights");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition pole = partdefinition.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(50, 0).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tri_lights = pole.addOrReplaceChild("tri_lights", CubeListBuilder.create().texOffs(16, 1).addBox(-8.0F, -8.0F, -2.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-6.0F, -6.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(2.0F, -6.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 13).addBox(-2.0F, 2.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(1.0F, -7.0F, -4.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 3).addBox(1.0F, -6.0F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(6.0F, -6.0F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(-2.0F, -6.0F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -7.0F, -4.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 3).addBox(-7.0F, -6.0F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 12).addBox(2.0F, 2.0F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, 1.0F, -4.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(10, 3).addBox(-3.0F, 2.0F, -4.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 58, 18);
    }

    public ModelPart getMain() {
        return main;
    }

    public ModelPart getLights() {
        return lights;
    }

    public void setupAnim(TriRailwayLightsBE trlbe){
        lights.yRot = trlbe.yAngle;
        main.zRot = 0;
        main.xRot = 3.14555111f;
        main.y = 0.0f;
        lights.y = -8.0f;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
    }
}
