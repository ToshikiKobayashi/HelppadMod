package com.abalab.helppadmod.client.renderer;

import com.abalab.helppadmod.HelppadMod;
import com.abalab.helppadmod.client.model.CareSubjectModel;
import com.abalab.helppadmod.entity.CareSubjectEntity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CareSubjectRenderer extends MobRenderer<CareSubjectEntity, CareSubjectRenderState, CareSubjectModel<CareSubjectEntity>> {
    private static final ResourceLocation TEXTURE = ResourceLocation.tryParse(HelppadMod.MODID + ":textures/entity/care_subject.png");

    public CareSubjectRenderer(EntityRendererProvider.Context context) {
        super(context, new CareSubjectModel(context.bakeLayer(CareSubjectModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(CareSubjectRenderState state) {
        return TEXTURE;
    }

    @Override
    public CareSubjectRenderState createRenderState() {
        return new CareSubjectRenderState();
    }

}
