package com.abalab.helppadmod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CareSubjectEntity  extends Villager {
    private int discomfortLevel = 0;  // 不快指数
    private int tickCounter = 0;      // 不快指数更新用タイマー

    public CareSubjectEntity(EntityType<? extends Villager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if (!level().isClientSide) {
            tickCounter++;

            // 一定間隔ごとに不快指数を上げる
            if (tickCounter % 200 == 0) { // 10秒ごと（20tick * 10）
                discomfortLevel = Mth.clamp(discomfortLevel + 1, 0, 100);
            }

            // 常に寝ようとする
            trySleepAnytime();
        }
    }

    /** ベッドがあれば昼夜問わず寝る */
    private void trySleepAnytime() {
        if (this.isSleeping()) return; // 既に寝ているならスキップ

        BlockPos pos = this.blockPosition();

        // 周囲5x3x5ブロックを探索
        for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-2, -1, -2), pos.offset(2, 1, 2))) {
            BlockState state = level().getBlockState(checkPos);

            if (state.getBlock() instanceof BedBlock bedBlock) {
                // OCCUPIED プロパティでベッド使用中を確認
                boolean occupied = state.getValue(BedBlock.OCCUPIED);
                if (!occupied) {
                    this.startSleeping(checkPos);
                    return;
                }
            }
        }
    }

    /** 起床処理（例えばプレイヤーが起こした時に呼び出す想定） */
    public void wakeUpByPlayer() {
        if (isSleeping()) {
            stopSleeping();
            this.discomfortLevel = Math.max(0, discomfortLevel - 20); // 起こされると不快指数が下がる
        }
    }

    public int getDiscomfortLevel() {
        return discomfortLevel;
    }
}
