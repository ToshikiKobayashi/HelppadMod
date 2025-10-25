package com.abalab.helppadmod.event;

import com.abalab.helppadmod.HelppadMod;
import com.abalab.helppadmod.capability.IToiletData;
import com.abalab.helppadmod.capability.ModCapabilities;
import com.abalab.helppadmod.capability.ToiletData;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = HelppadMod.MODID)
public class ToiletCapabilityAttacher {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Villager)) return;

        ToiletData data = new ToiletData();
        LazyOptional<IToiletData> optional = LazyOptional.of(() -> data);

        event.addCapability(ResourceLocation.fromNamespaceAndPath(HelppadMod.MODID, "toilet_data"),
            new ICapabilitySerializable<CompoundTag>() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    return cap == ModCapabilities.TOILET ? optional.cast() : LazyOptional.empty();
                }

                @Override
                public CompoundTag serializeNBT(HolderLookup.Provider registryAccess) {
                    return data.saveNBT();
                }

                @Override
                public void deserializeNBT(HolderLookup.Provider registryAccess, CompoundTag nbt) {
                    data.loadNBT(nbt);
                }
            });
    }
}