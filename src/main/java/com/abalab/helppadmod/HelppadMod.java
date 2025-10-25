package com.abalab.helppadmod;

import com.abalab.helppadmod.blockentity.HelppadBedBlockEntity;

import com.abalab.helppadmod.block.HelppadBedBlock;
import com.abalab.helppadmod.entity.CareSubjectEntity;
import com.abalab.helppadmod.event.ToiletCapabilityAttacher;
import com.abalab.helppadmod.event.VillagerToiletHandler;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.EventBusMigrationHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HelppadMod.MODID)
public final class HelppadMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "helppadmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<EntityType<?>> ENTITIES =
    DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HelppadMod.MODID);
    public static final RegistryObject<EntityType<CareSubjectEntity>> CARE_SUBJECT_ENTITY = 
        ENTITIES.register("care_subject",
            () -> EntityType.Builder.of(CareSubjectEntity::new, MobCategory.MISC)
                .sized(0.6f, 1.8f)
                .build(ENTITIES.key("care_subject")));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);  

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HelppadMod.MODID);
    public static final RegistryObject<Block> HELPPAD_BED_BLOCK = BLOCKS.register("helppad_bed",
            () -> new HelppadBedBlock(BlockBehaviour.Properties.of().strength(3.0f, 3.0f).requiresCorrectToolForDrops().setId(BLOCKS.key("helppad_bed"))));
    public static final RegistryObject<Item> HELPPAD_BED_ITEM = ITEMS.register("helppad_bed",
            () -> new BlockItem(HELPPAD_BED_BLOCK.get(), new Item.Properties().setId(ITEMS.key("helppad_bed"))));


    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, HelppadMod.MODID);
    public static final RegistryObject<BlockEntityType> HELPPAD_BED_BLOCK_ENTITY_TYPE = BLOCK_ENTITIES.register("helppad_bed",
     () -> new BlockEntityType<>((pos, state) -> new HelppadBedBlockEntity(pos, state), Set.of(HELPPAD_BED_BLOCK.get())));


    public HelppadMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Register the commonSetup method for modloading
        FMLCommonSetupEvent.getBus(modBusGroup).addListener(this::commonSetup);
        ITEMS.register(modBusGroup);
        ENTITIES.register(modBusGroup);
        BLOCKS.register(modBusGroup);
        BLOCK_ENTITIES.register(modBusGroup);

        // Register the item to a creative tab
        BuildCreativeModeTabContentsEvent.getBus(modBusGroup).addListener(HelppadMod::addCreative);


        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusSubscriber {
        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            try {
                event.put(CARE_SUBJECT_ENTITY.get(), CareSubjectEntity.createAttributes().build());
            } catch (IllegalStateException e) {
                // すでに登録されている場合は無視
            }
        }
    }
}
