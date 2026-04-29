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
import net.rk.railroadways.entity.blockentity.custom.MultipurposeSignBE;
import net.rk.railroadways.util.Utilities;

public class MultipurposeSignModel extends Model {
    public static final ModelLayerLocation DEFAULT_MULTIPURPOSE_TEXTURE = new ModelLayerLocation(ResourceLocation.parse(""), "main");
    private final ModelPart pole;
    private final ModelPart rcon;
    private final ModelPart lcon;
    private final ModelPart bcon;
    private final ModelPart fcon;
    public final ModelPart sign;
    public final ModelPart glowingpart;

    public MultipurposeSignModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.pole = root.getChild("pole");
        this.rcon = this.pole.getChild("rcon");
        this.lcon = this.pole.getChild("lcon");
        this.bcon = this.pole.getChild("bcon");
        this.fcon = this.pole.getChild("fcon");
        this.sign = root.getChild("sign");
        this.glowingpart = this.sign.getChild("glowingpart");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition pole = partdefinition.addOrReplaceChild("pole", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition rcon = pole.addOrReplaceChild("rcon", CubeListBuilder.create().texOffs(46, 0).addBox(-3.5F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, 0.0F, 0.0F));

        PartDefinition lcon = pole.addOrReplaceChild("lcon", CubeListBuilder.create().texOffs(28, 0).addBox(-3.5F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, 0.0F, 0.0F));

        PartDefinition bcon = pole.addOrReplaceChild("bcon", CubeListBuilder.create().texOffs(37, 4).addBox(-1.0F, -1.0F, 1.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fcon = pole.addOrReplaceChild("fcon", CubeListBuilder.create().texOffs(19, 4).addBox(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition sign = partdefinition.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 18).addBox(-8.0F, -8.0F, -5.0F, 16.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition glowingpart = sign.addOrReplaceChild("glowingpart", CubeListBuilder.create().texOffs(4, 38).addBox(-8.0F, -8.0F, -4.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelPart getLcon() {
        return lcon;
    }

    public ModelPart getRcon() {
        return rcon;
    }

    public ModelPart getFcon() {
        return fcon;
    }

    public ModelPart getBcon() {
        return bcon;
    }

    public ModelPart getGlowingpart() {
        return glowingpart;
    }

    public ModelPart getPole() {
        return pole;
    }

    public ModelPart getSign() {
        return sign;
    }

    public void setupAnim(MultipurposeSignBE sign){
        this.sign.y = 16.0f;
        this.sign.xRot = Mth.PI;
        this.sign.yRot = Utilities.degreesToRadians(sign.yAngle + 180);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        pole.render(poseStack,vertexConsumer,i,i1,i2);
    }
}
