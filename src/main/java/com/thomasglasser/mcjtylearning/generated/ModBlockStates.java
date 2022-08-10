package com.thomasglasser.mcjtylearning.generated;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.init.Elements;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.thomasglasser.mcjtylearning.client.blocks.models.loaders.GeneratorModelLoader.GENERATOR_LOADER;

public class ModBlockStates extends BlockStateProvider {
    public ModBlockStates(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, McJtyLearning.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels()
    {
        registerPowerGenerator();
        registerGenerator();
        registerPortal();

        simpleBlock(Elements.VERITE_ORE.get());
        simpleBlock(Elements.MYSTERIOUS_ORE.get());
        simpleBlock(Elements.NETHER_MYSTERIOUS_ORE.get());
        simpleBlock(Elements.DEEPSLATE_MYSTERIOUS_ORE.get());
        simpleBlock(Elements.END_MYSTERIOUS_ORE.get());

        logBlock((RotatedPillarBlock) Elements.FROST_LOG.get());
    }

    private void registerPortal()
    {
        ResourceLocation side = modLoc("block/portal_side");
        ResourceLocation top = modLoc("block/portal_top");
        simpleBlock(Elements.PORTAL.get(), models().cube(Elements.PORTAL.getId().getPath(), side, top, side, side, side, side));
    }

    private void registerGenerator()
    {
        BlockModelBuilder generatorModel = models().getBuilder(Elements.GENERATOR.getId().getPath())
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((blockModelBuilder, helper) -> new CustomLoaderBuilder<BlockModelBuilder>(GENERATOR_LOADER, blockModelBuilder, helper) {} )
                .end();

        directionalBlock(Elements.GENERATOR.get(), generatorModel);
    }

    private void registerPowerGenerator()
    {
        BlockModelBuilder frame = models().getBuilder("block/powergen/main");
        frame.parent(models().getExistingFile(mcLoc("cube")));

        floatingCube(frame, 0f, 0f, 0f, 1f, 16f, 1f);
        floatingCube(frame, 15f, 0f, 0f, 16f, 16f, 1f);
        floatingCube(frame, 0f, 0f, 15f, 1f, 16f, 16f);
        floatingCube(frame, 15f, 0f, 15f, 16f, 16f, 16f);

        floatingCube(frame, 1f, 0f, 0f, 15f, 1f, 1f);
        floatingCube(frame, 1f, 15f, 0f, 15f, 16f, 1f);
        floatingCube(frame, 1f, 0f, 15f, 15f, 1f, 16f);
        floatingCube(frame, 1f, 15f, 15f, 15f, 16f, 16f);

        floatingCube(frame, 0f, 0f, 1f, 1f, 1f, 15f);
        floatingCube(frame, 15f, 0f, 1f, 16f, 1f, 15f);
        floatingCube(frame, 0f, 15f, 1f, 1f, 16f, 15f);
        floatingCube(frame, 15f, 15f, 1f, 16f, 16f, 15f);

        floatingCube(frame, 1f, 1f, 1f, 15f, 15f, 15f);

        frame.texture("window", modLoc("block/powergen_window"));
        frame.texture("particle", modLoc("block/powergen_off"));

        frame.renderType("translucent");

        createPowerGeneratorModel(Elements.POWER_GENERATOR.get(), frame);
    }

    private void floatingCube(BlockModelBuilder builder, float fx, float fy, float fz, float tx, float ty, float tz)
    {
        builder.element()
                .from(fx, fy, fz)
                .to(tx, ty, tz)
                .allFaces((direction, faceBuilder) -> faceBuilder.texture("#window"))
                .end();
    }

    private void createPowerGeneratorModel(Block block, BlockModelBuilder frame)
    {
        //Faces
        BlockModelBuilder singleOff = models().getBuilder("block/powergen/singleoff")
                .element().from(3, 3, 3).to(13, 13, 13).face(Direction.DOWN).texture("#single").end().end()
                .texture("single", modLoc("block/powergen_off"));
        BlockModelBuilder singleOn = models().getBuilder("block/powergen/singleon")
                .element().from(3, 3, 3).to(13, 13, 13).face(Direction.DOWN).texture("#single").end().end()
                .texture("single", modLoc("block/powergen_on"));

        MultiPartBlockStateBuilder builder = getMultipartBuilder(block);

        builder.part().modelFile(frame).addModel();

        BlockModelBuilder[] models = new BlockModelBuilder[] {singleOff, singleOn};

        //Puts faces (in models[]) on all sides of block
        for (int i = 0; i < models.length; i++)
        {
            boolean powered = i == 1;
            builder.part().modelFile(models[i]).addModel().condition(BlockStateProperties.POWERED, powered);
            builder.part().modelFile(models[i]).rotationX(180).addModel().condition(BlockStateProperties.POWERED, powered);
            builder.part().modelFile(models[i]).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
            builder.part().modelFile(models[i]).rotationX(270).addModel().condition(BlockStateProperties.POWERED, powered);
            builder.part().modelFile(models[i]).rotationY(90).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
            builder.part().modelFile(models[i]).rotationY(270).rotationX(90).addModel().condition(BlockStateProperties.POWERED, powered);
        }
    }
}
