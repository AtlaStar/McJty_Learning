package com.thomasglasser.mcjtylearning.client.blocks.models;

import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.thomasglasser.mcjtylearning.client.blocks.models.keys.GeneratorModelKey;
import com.thomasglasser.mcjtylearning.client.blocks.models.loaders.GeneratorModelLoader;
import com.thomasglasser.mcjtylearning.server.blocks.GeneratorBlock;
import com.thomasglasser.mcjtylearning.server.blocks.entities.GeneratorBlockEntity;
import com.thomasglasser.mcjtylearning.tools.ClientTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.QuadTransformer;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

import static com.thomasglasser.mcjtylearning.tools.ClientTools.v;
import static java.lang.Boolean.TRUE;

public class GeneratorBakedModel implements IDynamicBakedModel
{
    private final ModelState modelState;
    private final Function<Material, TextureAtlasSprite> spriteGetter;
    private final Map<GeneratorModelKey, List<BakedQuad>> quadCache = new HashMap<>();
    private final ItemOverrides overrides;
    private final ItemTransforms itemTransforms;

    public GeneratorBakedModel(ModelState modelState, Function<Material, TextureAtlasSprite> spriteGetter, ItemOverrides overrides, ItemTransforms itemTransforms)
    {
        this.modelState = modelState;
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;
        this.itemTransforms = itemTransforms;
        generateQuadCache();
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull IModelData extraData) {
        RenderType layer = MinecraftForgeClient.getRenderType();

        if (side != null || (layer != null && !layer.equals(RenderType.solid())))
        {
            return Collections.emptyList();
        }

        boolean generating = TRUE == extraData.getData(GeneratorBlockEntity.GENERATING);
        boolean collecting = TRUE == extraData.getData(GeneratorBlockEntity.COLLECTING);
        boolean actuallyGenerating = TRUE == extraData.getData(GeneratorBlockEntity.ACTUALLY_GENERATING);

        var quads = getQuadsForGeneratingBlock(state, rand, extraData, layer);

        GeneratorModelKey key = new GeneratorModelKey(generating, collecting, actuallyGenerating, modelState);
        quads.addAll(quadCache.get(key));

        return quads;
    }

    private void generateQuadCache()
    {
        quadCache.put(new GeneratorModelKey(true, false, false, modelState), generateQuads(true, false, false));
        quadCache.put(new GeneratorModelKey(true, true, false, modelState), generateQuads(true, true, false));
        quadCache.put(new GeneratorModelKey(true, false, true, modelState), generateQuads(true, false, true));
        quadCache.put(new GeneratorModelKey(true, true, true, modelState), generateQuads(true, true, true));
        quadCache.put(new GeneratorModelKey(false, false, false, modelState), generateQuads(false, false, false));
        quadCache.put(new GeneratorModelKey(false, true, false, modelState), generateQuads(false, true, false));
        quadCache.put(new GeneratorModelKey(false, false, true, modelState), generateQuads(false, false, true));
        quadCache.put(new GeneratorModelKey(false, true, true, modelState), generateQuads(false, true, true));
    }

    private List<BakedQuad> generateQuads(boolean generating, boolean collecting, boolean actuallyGenerating)
    {
        var quads = new ArrayList<BakedQuad>();

        float l = 0;
        float r = 1;
        float p = 13f/16f;

        float bl = 1f/16f;
        float br = 7f/16f;

        float half = .5f;

        Transformation rotation = modelState.getRotation();

        TextureAtlasSprite textureSide = spriteGetter.apply(GeneratorModelLoader.MATERIAL_SIDE);
        TextureAtlasSprite textureFrontPowered = spriteGetter.apply(GeneratorModelLoader.MATERIAL_FRONT_POWERED);
        TextureAtlasSprite textureFront = spriteGetter.apply(GeneratorModelLoader.MATERIAL_FRONT);
        TextureAtlasSprite textureOn = spriteGetter.apply(GeneratorModelLoader.MATERIAL_ON);
        TextureAtlasSprite textureOff = spriteGetter.apply(GeneratorModelLoader.MATERIAL_OFF);

        quads.add(ClientTools.createQuad(v(r, p, r), v(r, p, l), v(l, p, l), v(l, p, r), rotation, actuallyGenerating ? textureFrontPowered : textureFront));      // Top side
        quads.add(ClientTools.createQuad(v(l, l, l), v(r, l, l), v(r, l, r), v(l, l, r), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(r, p, r), v(r, l, r), v(r, l, l), v(r, p, l), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(l, p, l), v(l, l, l), v(l, l, r), v(l, p, r), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(r, p, l), v(r, l, l), v(l, l, l), v(l, p, l), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(l, p, r), v(l, l, r), v(r, l, r), v(r, p, r), rotation, textureSide));

        float s = collecting ? 14f/16f : r;
        float offset = 0;
        quads.add(ClientTools.createQuad(v(br, s, br+offset), v(br, s, bl+offset), v(bl, s, bl+offset), v(bl, s, br+offset), rotation, collecting ? textureOn : textureOff));
        quads.add(ClientTools.createQuad(v(br, s, br+offset), v(br, p, br+offset), v(br, p, bl+offset), v(br, s, bl+offset), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(bl, s, bl+offset), v(bl, p, bl+offset), v(bl, p, br+offset), v(bl, s, br+offset), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(br, s, bl+offset), v(br, p, bl+offset), v(bl, p, bl+offset), v(bl, s, bl+offset), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(bl, s, br+offset), v(bl, p, br+offset), v(br, p, br+offset), v(br, s, br+offset), rotation, textureSide));

        // The generating button
        s = generating ? 14f/16f : r;
        offset = half;
        quads.add(ClientTools.createQuad(v(br, s, br+offset), v(br, s, bl+offset), v(bl, s, bl+offset), v(bl, s, br+offset), rotation, generating ? textureOn : textureOff));
        quads.add(ClientTools.createQuad(v(br, s, br+offset), v(br, p, br+offset), v(br, p, bl+offset), v(br, s, bl+offset), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(bl, s, bl+offset), v(bl, p, bl+offset), v(bl, p, br+offset), v(bl, s, br+offset), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(br, s, bl+offset), v(br, p, bl+offset), v(bl, p, bl+offset), v(bl, s, bl+offset), rotation, textureSide));
        quads.add(ClientTools.createQuad(v(bl, s, br+offset), v(bl, p, br+offset), v(br, p, br+offset), v(br, s, br+offset), rotation, textureSide));

        return quads;
    }

    private List<BakedQuad> getQuadsForGeneratingBlock(BlockState state, RandomSource rand, IModelData extraData, RenderType layer)
    {
        var quads = new ArrayList<BakedQuad>();
        BlockState generatingBlock = extraData.getData(GeneratorBlockEntity.GENERATING_BLOCK);

        if (generatingBlock != null && !(generatingBlock.getBlock() instanceof GeneratorBlock))
        {
            if (layer == null || ItemBlockRenderTypes.canRenderInLayer(generatingBlock, layer))
            {
                BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getBlockModel(generatingBlock);

                try {
                    Direction facing = state == null ? Direction.SOUTH : state.getValue(BlockStateProperties.FACING);
                    Transformation rotation = modelState.getRotation();
                    Transformation translate = transformGeneratingBlock(facing, rotation);
                    QuadTransformer transformer = new QuadTransformer(translate);

                    for (Direction s : Direction.values())
                    {
                        List<BakedQuad> modelQuads = model.getQuads(generatingBlock, s, rand, EmptyModelData.INSTANCE);
                        for (BakedQuad quad : modelQuads)
                        {
                            quads.add(transformer.processOne(quad));
                        }
                    }
                }
                catch (Exception e)
                {

                }
            }
        }

        return quads;
    }

    private Transformation transformGeneratingBlock(Direction facing, Transformation rotation)
    {
        float dX = facing.getStepX();
        float dY = facing.getStepY();
        float dZ = facing.getStepZ();

        switch (facing)
        {
            case DOWN -> {dX = 1; dY = 0; dZ = -1;}
            case UP -> {dX = 1; dY = 0; dZ = 1;}
            case NORTH -> {dX = 1; dY = 1; dZ = 0;}
            case SOUTH -> {dX = -1; dY = 1; dZ = 0;}
            case WEST -> {dX = 0; dY = 1; dZ = -1;}
            case EAST -> {dX = 0; dY = 1; dZ = 1;}
        }

        float stepX = facing.getStepX() / 4f + dX / 4f + .5f;
        float stepY = facing.getStepY() / 4f + dY / 4f + .5f;
        float stepZ = facing.getStepZ() / 4f + dZ / 4f + .5f;

        Transformation translate = new Transformation(Matrix4f.createTranslateMatrix(stepX, stepY, stepZ));

        translate = translate.compose(new Transformation(Matrix4f.createScaleMatrix(.2f, .2f, .2f)));
        translate = translate.compose(rotation);

        translate = translate.compose(new Transformation(Matrix4f.createTranslateMatrix(-.5f, -.5f, -.5f)));
        return translate;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull IModelData data) {
        return spriteGetter.apply(GeneratorModelLoader.MATERIAL_SIDE);
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return spriteGetter.apply(GeneratorModelLoader.MATERIAL_SIDE);
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemTransforms getTransforms() {
        return itemTransforms;
    }
}
