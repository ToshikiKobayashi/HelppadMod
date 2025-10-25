package com.abalab.helppadmod;

public class VillagerAccidentCapability {
    private boolean hasAccident = false;
    private int discomfort = 0;

    public boolean hasAccident() {
        return hasAccident;
    }

    public void setAccident(boolean value) {
        this.hasAccident = value;
    }

    public int getDiscomfort() {
        return discomfort;
    }

    public void setDiscomfort(int value) {
        this.discomfort = value;
    }

    public void tick() {
        if (hasAccident) {
            // 例えば毎ティック 1 増加、最大 100
            discomfort = Math.min(100, discomfort + 1);
        }
    }
}