package com.abalab.helppadmod.blockentity;

import com.abalab.helppadmod.HelppadMod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HelppadBedBlockEntity extends BlockEntity {
    private int comfortLevel = 0; // 不快指数 (0～100など)

    public HelppadBedBlockEntity(BlockPos pos, BlockState state) {
        super(HelppadMod.HELPPAD_BED_BLOCK_ENTITY_TYPE.get(), pos, state);
    }

    public int getComfortLevel() {
        return comfortLevel;
    }

    public void setComfortLevel(int comfortLevel) {
        this.comfortLevel = comfortLevel;
        setChanged(); // データが変わったことをMinecraftに通知
    }

    public void resetComfort() {
        this.comfortLevel = 0;
        setChanged();
    }

    public void increaseComfort(int amount) {
        this.comfortLevel += amount;
        setChanged();
    }
}
