package com.abalab.helppadmod.client.model;

import com.abalab.helppadmod.HelppadMod;
import com.abalab.helppadmod.client.renderer.CareSubjectRenderState;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class CareSubjectModel <T extends Entity> extends EntityModel<CareSubjectRenderState>  {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.tryParse(HelppadMod.MODID + ":care_subject"), "main");
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart hatRim;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart arms;


    public CareSubjectModel(ModelPart p_171051_) {
        super(p_171051_);
        this.head = p_171051_.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hatRim = this.hat.getChild("hat_rim");
        this.rightLeg = p_171051_.getChild("right_leg");
        this.leftLeg = p_171051_.getChild("left_leg");
        this.arms = p_171051_.getChild("arms");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        float f = 0.5F;
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild(
            "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO
        );
        PartDefinition partdefinition2 = partdefinition1.addOrReplaceChild(
            "hat",
            CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.51F)),
            PartPose.ZERO
        );
        partdefinition2.addOrReplaceChild(
            "hat_rim",
            CubeListBuilder.create().texOffs(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16.0F, 16.0F, 1.0F),
            PartPose.rotation((float) (-Math.PI / 2), 0.0F, 0.0F)
        );
        partdefinition1.addOrReplaceChild(
            "nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F)
        );
        PartDefinition partdefinition3 = partdefinition.addOrReplaceChild(
            "body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F), PartPose.ZERO
        );
        partdefinition3.addOrReplaceChild(
            "jacket",
            CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 20.0F, 6.0F, new CubeDeformation(0.5F)),
            PartPose.ZERO
        );
        partdefinition.addOrReplaceChild(
            "arms",
            CubeListBuilder.create()
                .texOffs(44, 22)
                .addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F)
                .texOffs(44, 22)
                .addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, true)
                .texOffs(40, 38)
                .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
            PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
            "right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F)
        );
        partdefinition.addOrReplaceChild(
            "left_leg",
            CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
            PartPose.offset(2.0F, 12.0F, 0.0F)
        );
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void setupAnim(CareSubjectRenderState p_365977_) {
        super.setupAnim(p_365977_);
        this.head.yRot = p_365977_.yRot * (float) (Math.PI / 180.0);
        this.head.xRot = p_365977_.xRot * (float) (Math.PI / 180.0);
        this.head.zRot = 0.0F;

        this.rightLeg.xRot = Mth.cos(p_365977_.walkAnimationPos * 0.6662F) * 1.4F * p_365977_.walkAnimationSpeed * 0.5F;
        this.leftLeg.xRot = Mth.cos(p_365977_.walkAnimationPos * 0.6662F + (float) Math.PI) * 1.4F * p_365977_.walkAnimationSpeed * 0.5F;
        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
    }
}
