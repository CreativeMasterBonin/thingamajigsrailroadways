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
import net.rk.railroadways.entity.blockentity.custom.DynamicSignBE;

public class DynamicSignModel extends Model{
    public static final ModelLayerLocation SIGN_TEXTURE_LOCATION = new ModelLayerLocation(
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/signs/sign_template.png"), "main");

    private final ModelPart signholder;
    private final ModelPart main;

    public DynamicSignModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.main = root.getChild("base_pole");
        this.signholder = main.getChild("sign_holder");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base_pole = partdefinition.addOrReplaceChild("base_pole",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F,
                new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition sign_holder = base_pole.addOrReplaceChild("sign_holder",
                CubeListBuilder.create().texOffs(8, 0)
                        .addBox(-8.0F, -8.1F, -2.0F, 16.0F, 16.0F, 1.0F,
                new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 42, 18);
    }

    public ModelPart getMain(){
       return this.main;
    }

    public ModelPart getSignHolder(){
        return this.signholder;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.main.render(poseStack,vertexConsumer,i,i1);
    }

    public void setupAnim(DynamicSignBE dsbe){
        signholder.yRot = dsbe.yAngle;
        signholder.y = -8.0f;
        main.zRot = 0;
        main.xRot = 3.14555111f;
        main.y = 8.0f;
    }
}
