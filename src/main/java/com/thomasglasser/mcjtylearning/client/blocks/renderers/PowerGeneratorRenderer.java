package com.thomasglasser.mcjtylearning.client.blocks.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.server.blocks.entities.PowerGeneratorBlockEntity;
import com.thomasglasser.mcjtylearning.init.Registration;
import com.thomasglasser.mcjtylearning.init.config.PowerGeneratorConfig;
import com.thomasglasser.mcjtylearning.tools.CustomRenderType;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static java.lang.Boolean.TRUE;

public class PowerGeneratorRenderer implements BlockEntityRenderer<PowerGeneratorBlockEntity>
{
    public static final ResourceLocation HALO = new ResourceLocation(McJtyLearning.MODID, "effect/halo");

    public PowerGeneratorRenderer(BlockEntityRendererProvider.Context context)
    {

    }

    @Override
    public void render(PowerGeneratorBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        Boolean powered = pBlockEntity.getBlockState().getValue(BlockStateProperties.POWERED);
        if (TRUE != powered)
        {
            return;
        }

        int brightness = LightTexture.FULL_BRIGHT;
        float s = (System.currentTimeMillis() % 1000) / 1000.0f;

        if (s > 0.5f)
        {
            s = 1.0f- s;
        }

        float scale = 0.1f + s * (float) (double) PowerGeneratorConfig.RENDER_SCALE.get();

        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(HALO);

        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 1.5, 0.5);

        Quaternion rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        pPoseStack.mulPose(rotation);

        VertexConsumer builder;
        if (Minecraft.getInstance().options.graphicsMode().get() == GraphicsStatus.FABULOUS) {
            builder = pBufferSource.getBuffer(Sheets.translucentItemSheet());
        }
        else {
           builder = pBufferSource.getBuffer(CustomRenderType.ADD);
        }

        Matrix4f matrix = pPoseStack.last().pose();

        builder.vertex(matrix, -scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU0(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightness).normal(1,0,0).endVertex();
        builder.vertex(matrix, -scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU0(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightness).normal(1,0,0).endVertex();
        builder.vertex(matrix, scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU1(), sprite.getV1()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightness).normal(1,0,0).endVertex();
        builder.vertex(matrix, scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 0.3f).uv(sprite.getU1(), sprite.getV0()).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(brightness).normal(1,0,0).endVertex();

        pPoseStack.popPose();
    }

    public static void register()
    {
        BlockEntityRenderers.register(Registration.POWER_GENERATOR_BLOCK_ENTITY.get(), PowerGeneratorRenderer::new);
    }
}
