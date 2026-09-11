package net.rk.railroadways.entity.custom.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.Util;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.rk.railroadways.entity.custom.DieselLocomotive;
import net.rk.railroadways.util.Utilities;

public class DieselLocomotiveModel extends EntityModel<DieselLocomotive> {
    public static final ModelLayerLocation DEFAULT = new ModelLayerLocation(
            ResourceLocation.parse("thingamajigsrailroadways:textures/entity/diesel_locomotive.png"),"main"
    );
    private final ModelPart base;
    private final ModelPart frontbogey;
    private final ModelPart backbogey;

    public DieselLocomotiveModel(ModelPart root) {
        this.base = root.getChild("base");
        this.frontbogey = this.base.getChild("frontbogey");
        this.backbogey = this.base.getChild("backbogey");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(144, 109).addBox(-7.0F, -5.0F, -8.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 113).addBox(-7.0F, -5.0F, 11.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(20, 149).addBox(-1.0F, -10.0F, -8.0F, 2.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(132, 41).addBox(-7.0F, -10.0F, -12.0F, 14.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(104, 77).addBox(-4.0F, -10.0F, -14.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 120).addBox(-2.0F, 2.0F, -13.0F, 4.0F, 3.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(38, 149).addBox(-1.0F, -8.99F, -16.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(60, 120).addBox(-8.0F, -9.0F, -10.0F, 1.0F, 14.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(108, 120).addBox(7.0F, -9.0F, -10.0F, 1.0F, 14.0F, 23.0F, new CubeDeformation(0.0F))
                .texOffs(132, 0).addBox(-8.0F, -15.0F, 13.0F, 16.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(104, 46).addBox(-8.0F, -15.0F, -8.0F, 16.0F, 6.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 46).addBox(-10.0F, -17.0F, -12.0F, 20.0F, 2.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(132, 24).addBox(-11.0F, -2.0F, 23.0F, 22.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(104, 73).addBox(-11.0F, -2.0F, -21.0F, 22.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 80).addBox(-11.0F, -2.0F, -17.0F, 0.0F, 4.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(72, 80).addBox(11.0F, -2.0F, -17.0F, 0.0F, 4.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(132, 28).addBox(-7.0F, -9.0F, -10.0F, 14.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-11.0F, 1.95F, -21.0F, 22.0F, 2.0F, 44.0F, new CubeDeformation(0.0F))
                .texOffs(82, 157).addBox(-1.0F, -13.0F, 17.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(88, 157).addBox(-1.0F, -9.0F, -17.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(124, 77).addBox(-7.0F, 2.0F, -22.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(130, 77).addBox(5.0F, 2.0F, -22.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(144, 117).addBox(-7.0F, 2.0F, 23.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(136, 77).addBox(5.0F, 2.0F, 23.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition frontbogeycon_r1 = base.addOrReplaceChild("frontbogeycon_r1", CubeListBuilder.create().texOffs(66, 157).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -7.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition backbogeycon_r1 = base.addOrReplaceChild("backbogeycon_r1", CubeListBuilder.create().texOffs(74, 157).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 11.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition leftfrontpanel_r1 = base.addOrReplaceChild("leftfrontpanel_r1", CubeListBuilder.create().texOffs(0, 149).addBox(-6.0F, -11.0F, -2.0F, 8.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 2.0F, -13.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rightfrontpanel_r1 = base.addOrReplaceChild("rightfrontpanel_r1", CubeListBuilder.create().texOffs(144, 93).addBox(-2.0F, -11.0F, -2.0F, 8.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 2.0F, -13.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition frontbogey = base.addOrReplaceChild("frontbogey", CubeListBuilder.create().texOffs(144, 77).addBox(-7.0F, -1.0F, -4.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 85).addBox(-7.0F, -1.0F, 2.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(148, 73).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(46, 149).addBox(-8.0F, -2.0F, -5.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(156, 117).addBox(7.0F, -2.0F, -5.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(156, 133).addBox(-8.0F, -2.0F, 1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(156, 125).addBox(7.0F, -2.0F, 1.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -7.0F));

        PartDefinition backbogey = base.addOrReplaceChild("backbogey", CubeListBuilder.create().texOffs(144, 81).addBox(-7.0F, -1.0F, -1.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(144, 89).addBox(-7.0F, -1.0F, 5.0F, 14.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(156, 73).addBox(-1.0F, -1.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(156, 141).addBox(-8.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(156, 149).addBox(7.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(46, 157).addBox(-8.0F, -2.0F, 4.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(56, 157).addBox(7.0F, -2.0F, 4.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 8.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public ModelPart getBase() {
        return base;
    }

    public ModelPart getFrontbogey() {
        return frontbogey;
    }

    public ModelPart getBackbogey() {
        return backbogey;
    }

    @Override
    public void setupAnim(DieselLocomotive dieselLocomotive, float v, float v1, float v2, float v3, float v4) {

    }

    // unused as it does not work
    public void setup(DieselLocomotive locomotive,float partialTick){
        if(locomotive.getBlockStateOn().getBlock() instanceof RailBlock){
            RailShape value = locomotive.getBlockStateOn().getValue(RailBlock.SHAPE);
            if (value == RailShape.ASCENDING_NORTH) {
                if (locomotive.getDeltaMovement().z > 0.0D) {
                    base.xRot = Utilities.degreesToRadians(45.0f);
                    base.yRot = Utilities.degreesToRadians(0.0f);
                    return;
                } else {
                    base.xRot = Utilities.degreesToRadians(-45.0f);
                    base.yRot = Utilities.degreesToRadians(180.0f);
                    return;
                }
            } else if (value == RailShape.ASCENDING_SOUTH) {
                if (locomotive.getDeltaMovement().z > 0.0D) {
                    base.xRot = Utilities.degreesToRadians(-45.0f);
                    base.yRot = Utilities.degreesToRadians(0.0f);
                    return;
                } else {
                    base.xRot = Utilities.degreesToRadians(45.0f);
                    base.yRot = Utilities.degreesToRadians(180.0f);
                    return;
                }
            } else if (value == RailShape.ASCENDING_EAST) {
                if (locomotive.getDeltaMovement().x > 0.0D) {
                    base.xRot = Utilities.degreesToRadians(-45.0f);
                    base.yRot = Utilities.degreesToRadians(-90.0f);
                    return;
                } else {
                    base.xRot = Utilities.degreesToRadians(45.0f);
                    base.yRot = Utilities.degreesToRadians(90.0f);
                    return;
                }
            } else if (value == RailShape.ASCENDING_WEST) {
                if (locomotive.getDeltaMovement().x > 0.0D) {
                    base.xRot = Utilities.degreesToRadians(45.0f);
                    base.yRot = Utilities.degreesToRadians(-90.0f);
                    return;
                } else {
                    base.xRot = Utilities.degreesToRadians(-45.0f);
                    base.yRot = Utilities.degreesToRadians(90.0f);
                    return;
                }
            } else if (value == RailShape.NORTH_SOUTH) {
                if (locomotive.getDeltaMovement().z > 0.0D) {
                    base.xRot = 0.0f;
                    base.yRot = Utilities.degreesToRadians(0.0f);
                    return;
                } else {
                    base.xRot = 0.0f;
                    base.yRot = Utilities.degreesToRadians(180.0f);
                    return;
                }
            } else if (value == RailShape.EAST_WEST) {
                if (locomotive.getDeltaMovement().x > 0.0D) {
                    base.xRot = 0.0f;
                    base.yRot = Utilities.degreesToRadians(-90.0f);
                    return;
                } else {
                    base.xRot = 0.0f;
                    base.yRot = Utilities.degreesToRadians(90.0f);
                    return;
                }
            }
            base.xRot = 0.0f;
            base.yRot = locomotive.getViewYRot(partialTick) + Utilities.degreesToRadians(90.0f);
            base.zRot = 0.0f;
            return;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        this.base.render(poseStack,vertexConsumer,i,i1);
    }
}
