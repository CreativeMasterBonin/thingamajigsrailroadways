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
import net.rk.railroadways.entity.blockentity.custom.BritRailwayLightsBE;

public class BritRRLightsModel extends Model{
    public static final ModelLayerLocation BRIT_LIGHTS_OFF_LOC =
            new ModelLayerLocation(
                    ResourceLocation.parse("thingamajigsrailroadways:textures/entity/brit_off.png"), "main");

    private final ModelPart lights;
    private final ModelPart main;
    private final ModelPart overlay;

    public BritRRLightsModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("pole");
        this.lights = main.getChild("lights");
        this.overlay = lights.getChild("overlay");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition pole = partdefinition.addOrReplaceChild("pole",
                CubeListBuilder.create().texOffs(0, 28).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition lights = pole.addOrReplaceChild("lights",
                CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -11.0F, -5.0F, 32.0F, 19.0F, 4.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition overlay = lights.addOrReplaceChild("overlay",
                CubeListBuilder.create().texOffs(8, 27).addBox(-16.0F, -9.5F, 0.0F, 32.0F, 19.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, -5.05F));

        return LayerDefinition.create(meshdefinition, 72, 46);
    }

    public void setupAnim(BritRailwayLightsBE brwlbe){
        lights.yRot = brwlbe.yAngle;
        lights.y = -8.0f;
        main.y = 0.0f;
        main.xRot = 3.14555111f;
    }

    public ModelPart getMain() {
        return main;
    }

    public ModelPart getLights() {
        return lights;
    }

    public ModelPart getOverlay(){
        return overlay;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vc, int i, int i1, int i2){
        this.main.render(poseStack,vc,i,i1);
    }
}
