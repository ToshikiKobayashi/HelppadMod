package com.abalab.helppadmod.client;

import com.abalab.helppadmod.HelppadMod;
import com.abalab.helppadmod.client.model.CareSubjectModel;
import com.abalab.helppadmod.client.renderer.CareSubjectRenderer;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HelppadMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(HelppadMod.CARE_SUBJECT_ENTITY.get(), CareSubjectRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CareSubjectModel.LAYER_LOCATION, () -> CareSubjectModel.createBodyLayer());
     }
}