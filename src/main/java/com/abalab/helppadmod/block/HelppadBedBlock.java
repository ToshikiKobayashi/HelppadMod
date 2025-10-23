package com.abalab.helppadmod.block;

import com.abalab.helppadmod.blockentity.HelppadBedBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class HelppadBedBlock extends BedBlock {
    public HelppadBedBlock(Properties properties) {
        super(null, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // 独自の BlockEntity を生成
        return new HelppadBedBlockEntity(pos, state);
    }
}