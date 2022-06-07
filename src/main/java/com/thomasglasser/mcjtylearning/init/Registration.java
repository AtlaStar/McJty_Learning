package com.thomasglasser.mcjtylearning.init;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.blocks.PowerGeneratorBlock;
import com.thomasglasser.mcjtylearning.blocks.containers.PowerGeneratorContainer;
import com.thomasglasser.mcjtylearning.blocks.entities.PowerGeneratorBlockEntity;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.thomasglasser.mcjtylearning.McJtyLearning.MODID;

public class Registration {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

    public static void init()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(bus);
        ITEMS.register(bus);
        BLOCK_ENTITIES.register(bus);
        CONTAINERS.register(bus);
    }

    //BLOCKS
    public static final RegistryObject<Block> VERITE_ORE = BLOCKS.register("verite_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(2.0F)));
    public static final RegistryObject<Block> FROST_LOG = BLOCKS.register("frost_log", () -> log(MaterialColor.COLOR_LIGHT_BLUE, MaterialColor.COLOR_LIGHT_GRAY));

    //BLOCK ITEMS
    public static final RegistryObject<Item> VERITE_ORE_ITEM = registerBlock(VERITE_ORE, CreativeModeTab.TAB_BUILDING_BLOCKS);
    public static final RegistryObject<Item> FROST_LOG_ITEM = registerBlock(FROST_LOG, CreativeModeTab.TAB_BUILDING_BLOCKS);

    //ITEMS
    public static final RegistryObject<Item> RAW_VERITE_CHUNK = ITEMS.register("raw_verite_chunk", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> VERITE_INGOT = ITEMS.register("verite_ingot", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<PowerGeneratorBlock> POWER_GENERATOR = BLOCKS.register("power_generator", PowerGeneratorBlock::new);
    public static final RegistryObject<Item> POWER_GENERATOR_ITEM = registerBlock(POWER_GENERATOR, CreativeModeTab.TAB_REDSTONE);
    public static final RegistryObject<BlockEntityType<PowerGeneratorBlockEntity>> POWER_GENERATOR_BLOCK_ENTITY = BLOCK_ENTITIES.register("power_generator", () -> BlockEntityType.Builder.of(PowerGeneratorBlockEntity::new, POWER_GENERATOR.get()).build(null));
    public static final RegistryObject<MenuType<PowerGeneratorContainer>> POWER_GENERATOR_CONTAINER = CONTAINERS.register("powergen", () -> IForgeMenuType.create(((windowId, inv, data) -> new PowerGeneratorContainer(windowId, data.readBlockPos(), inv, inv.player))));


    public static <B extends Block> RegistryObject<Item> registerBlock(RegistryObject<B> block, CreativeModeTab tab)
    {
        return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static RotatedPillarBlock log(MaterialColor pTopColor, MaterialColor pBarkColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopColor : pBarkColor;
        }).strength(2.0F).sound(SoundType.WOOD));
    }
}
