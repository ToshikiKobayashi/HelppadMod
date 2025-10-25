package com.abalab.helppadmod.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class ModCapabilities {
    public static final Capability<IToiletData> TOILET =
            CapabilityManager.get(new CapabilityToken<IToiletData>() {});
}