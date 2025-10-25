package com.abalab.helppadmod.capability;

import net.minecraft.nbt.CompoundTag;

public class ToiletData implements IToiletData {
    private boolean accident = false;
    private int discomfort = 0;

    @Override
    public boolean hasAccident() { return accident; }
    @Override
    public void setAccident(boolean value) { this.accident = value; }

    @Override
    public int getDiscomfort() { return discomfort; }
    @Override
    public void setDiscomfort(int value) { this.discomfort = value; }

    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("accident", accident);
        tag.putInt("discomfort", discomfort);
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        this.accident = tag.getBoolean("accident").orElse(false);
        this.discomfort = tag.getInt("discomfort").orElse(0);
    }
}