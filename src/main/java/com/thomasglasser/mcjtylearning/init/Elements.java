package com.thomasglasser.mcjtylearning.init;

import com.mojang.serialization.Codec;
import com.thomasglasser.mcjtylearning.server.blocks.GeneratorBlock;
import com.thomasglasser.mcjtylearning.server.blocks.PortalBlock;
import com.thomasglasser.mcjtylearning.server.blocks.PowerGeneratorBlock;
import com.thomasglasser.mcjtylearning.server.blocks.containers.PowerGeneratorContainer;
import com.thomasglasser.mcjtylearning.server.blocks.entities.GeneratorBlockEntity;
import com.thomasglasser.mcjtylearning.server.blocks.entities.PowerGeneratorBlockEntity;
import com.thomasglasser.mcjtylearning.server.entities.Thief;
import com.thomasglasser.mcjtylearning.server.world.ores.MysteriousOres;
import com.thomasglasser.mcjtylearning.server.world.structures.PortalStructure;
import com.thomasglasser.mcjtylearning.server.world.structures.ThiefDenStructure;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomasglasser.mcjtylearning.McJtyLearning.MODID;

public class Elements {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    private static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, MODID);
    private static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, MODID);

    public static void init()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
        ENTITIES.register(bus);
        STRUCTURE_TYPES.register(bus);
        PLACED_FEATURES.register(bus);
    }

    //BLOCKS
    public static final RegistryObject<Block> VERITE_ORE = BLOCKS.register("verite_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> MYSTERIOUS_ORE = BLOCKS.register("mysterious_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> DEEPSLATE_MYSTERIOUS_ORE = BLOCKS.register("deepslate_mysterious_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> NETHER_MYSTERIOUS_ORE = BLOCKS.register("nether_mysterious_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> END_MYSTERIOUS_ORE = BLOCKS.register("end_mysterious_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> FROST_LOG = BLOCKS.register("frost_log", () -> log(MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.COLOR_LIGHT_GRAY));
    public static final RegistryObject<PowerGeneratorBlock> POWER_GENERATOR = BLOCKS.register("power_generator", PowerGeneratorBlock::new);
    public static final RegistryObject<Block> GENERATOR = BLOCKS.register("generator", GeneratorBlock::new);
    public static final RegistryObject<Block> PORTAL = BLOCKS.register("portal", PortalBlock::new);

    //BLOCK ITEMS
    public static final RegistryObject<Item> VERITE_ORE_ITEM = registerBlock(VERITE_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Item> MYSTERIOUS_ORE_ITEM = registerBlock(MYSTERIOUS_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Item> DEEPSLATE_MYSTERIOUS_ORE_ITEM = registerBlock(DEEPSLATE_MYSTERIOUS_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Item> END_MYSTERIOUS_ORE_ITEM = registerBlock(END_MYSTERIOUS_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Item> NETHER_MYSTERIOUS_ORE_ITEM = registerBlock(NETHER_MYSTERIOUS_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Item> FROST_LOG_ITEM = registerBlock(FROST_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Item> POWER_GENERATOR_ITEM = registerBlock(POWER_GENERATOR, CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Item> GENERATOR_ITEM = registerBlock(GENERATOR, CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<Item> PORTAL_ITEM = registerBlock(PORTAL, CreativeModeTab.TAB_DECORATIONS);

    //ENTITIES
    public static final RegistryObject<EntityType<Thief>> THIEF = ENTITIES.register("thief", () -> EntityType.Builder.of(Thief::new, MobCategory.CREATURE)
            .sized(0.6f, 1.95f)
            .clientTrackingRange(8)
            .setShouldReceiveVelocityUpdates(false)
            .build("thief"));

    //ITEMS
    public static final RegistryObject<Item> RAW_VERITE_CHUNK = ITEMS.register("raw_verite_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> VERITE_INGOT = ITEMS.register("verite_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> RAW_MYSTERIOUS_CHUNK = ITEMS.register("raw_mysterious_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> MYSTERIOUS_INGOT = ITEMS.register("mysterious_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> THIEF_SPAWN_EGG = ITEMS.register("thief", () -> new ForgeSpawnEggItem(THIEF, 0xff0000, 0x00ff00, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    //BLOCK ENTITIES
    public static final RegistryObject<BlockEntityType<PowerGeneratorBlockEntity>> POWER_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("power_generator", () -> BlockEntityType.Builder.of(PowerGeneratorBlockEntity::new, POWER_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<GeneratorBlockEntity>> GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("generator", () -> BlockEntityType.Builder.of(GeneratorBlockEntity::new, GENERATOR.get()).build(null));

    //MENU TYPES
    public static final RegistryObject<MenuType<PowerGeneratorContainer>> POWER_GENERATOR_CONTAINER = CONTAINERS.register("powergen", () -> IForgeMenuType.create(((windowId, inv, data) -> new PowerGeneratorContainer(windowId, data.readBlockPos(), inv, inv.player))));

    //STRUCTURE TYPES
    public static final RegistryObject<StructureType<?>> THIEF_DEN = STRUCTURE_TYPES.register("thiefden", () -> typeConvert(ThiefDenStructure.CODEC));
    public static final RegistryObject<StructureType<?>> PORTAL_STRUCTURE = STRUCTURE_TYPES.register("portal", () -> typeConvert(PortalStructure.CODEC));

    //PLACED FEATURES
    public static final RegistryObject<PlacedFeature> STONE_MYSTERIOUS_ORE_GENERATION = PLACED_FEATURES.register("stone_mysterious_ore", MysteriousOres::createStoneGeneration);
    public static final RegistryObject<PlacedFeature> MYSTERIOUS_MYSTERIOUS_ORE_GENERATION = PLACED_FEATURES.register("mysterious_mysterious_ore", MysteriousOres::createStoneGeneration);

    public static <B extends Block> RegistryObject<Item> registerBlock(RegistryObject<B> block, CreativeModeTab tab)
    {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static RotatedPillarBlock log(MaterialColor pTopColor, MaterialColor pBarkColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor;
        }).strength(2.0F).sound(SoundType.WOOD));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }

    private static <S extends Structure> StructureType<S> typeConvert(Codec<S> codec) {
        return () -> codec;
    }
}
