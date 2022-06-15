package com.thomasglasser.mcjtylearning.tools;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.Clearable;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class CustomRenderType extends RenderType
{
    public CustomRenderType(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    private static CompositeState addState(ShaderStateShard shard)
    {
        return CompositeState.builder()
                .setLightmapState(LIGHTMAP)
                .setShaderState(shard)
                .setTextureState(BLOCK_SHEET_MIPPED)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setOutputState(TRANSLUCENT_TARGET)
                .createCompositeState(true);
    }

    public static final RenderType ADD = create("translucent", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, true, addState(RENDERTYPE_TRANSLUCENT_SHADER));
}
