package com.abalab.helppadmod.event;

import com.abalab.helppadmod.HelppadMod;
import com.abalab.helppadmod.block.HelppadBedBlock;

import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.abalab.helppadmod.capability.IToiletData;
import com.abalab.helppadmod.capability.ModCapabilities;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraftforge.common.util.LazyOptional;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = HelppadMod.MODID)
public class VillagerToiletHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onVillagerTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Villager villager)) return;
        if (villager.level().isClientSide) return;

        LazyOptional<IToiletData> cap = villager.getCapability(ModCapabilities.TOILET);
        cap.ifPresent(data -> {
            boolean sleeping = villager.isSleeping();
            BlockPos pos = villager.blockPosition();
            BlockState state = villager.level().getBlockState(pos);
            Block block = state.getBlock();
            String villagerName = villager.hasCustomName() ? villager.getCustomName().getString() : "村人";

            if (sleeping && !data.hasAccident() && RANDOM.nextInt(2000) == 0) {
                data.setAccident(true);
                if (block instanceof HelppadBedBlock) {
                    broadcastNearPlayers(villager,
                    Component.literal("💧排泄あり！").
                    withStyle(style -> style.withColor(ChatFormatting.RED).withBold(true).withUnderlined(true)),
                    Component.literal(villagerName));
                }
            }

            if (!sleeping && data.hasAccident()) {
                data.setAccident(false);
                data.setDiscomfort(0);
                broadcastNearPlayers(villager,
                Component.literal("✨排泄ケア完了！").
                withStyle(style -> style.withColor(ChatFormatting.BLUE).withBold(true).withUnderlined(true)),
                Component.literal(villagerName));
            } else if(!sleeping && !(data.hasAccident())) {
                data.setAccident(false);
                data.setDiscomfort(0);
                broadcastNearPlayers(villager,
                Component.literal("😞空振り…").
                withStyle(style -> style.withColor(ChatFormatting.BLUE).withBold(true).withUnderlined(true)),
                Component.literal(villagerName));     
            }

            if (data.hasAccident()) {
                data.setDiscomfort(data.getDiscomfort() + 1);
                if (data.getDiscomfort() % 200 == 0) {
                    broadcastNearPlayers(villager,
                    Component.literal("💢不快状態！").
                    withStyle(style -> style.withColor(ChatFormatting.RED).withBold(true).withUnderlined(true)),
                    Component.literal(villagerName));
                }
            }
        });
    }

    private static void broadcastNearPlayers(Villager villager, Component message, Component message2) {
        double radius = 64.0D;
        List<ServerPlayer> players = villager.level().getEntitiesOfClass(ServerPlayer.class, villager.getBoundingBox().inflate(radius));
        for (ServerPlayer player : players) {
            player.connection.send(new ClientboundSetTitleTextPacket(message));
            player.connection.send(new ClientboundSetSubtitleTextPacket(message2));
        }
    }
}