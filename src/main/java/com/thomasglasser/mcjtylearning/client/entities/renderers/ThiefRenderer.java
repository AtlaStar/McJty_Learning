package com.thomasglasser.mcjtylearning.client.entities.renderers;

import com.thomasglasser.mcjtylearning.McJtyLearning;
import com.thomasglasser.mcjtylearning.client.entities.models.ThiefModel;
import com.thomasglasser.mcjtylearning.server.entities.Thief;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class ThiefRenderer extends HumanoidMobRenderer<Thief, ThiefModel>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation(McJtyLearning.MODID, "textures/entity/thief.png");

    public ThiefRenderer(EntityRendererProvider.Context context)
    {
        super(context, new ThiefModel(context.bakeLayer(ThiefModel.THIEF_LAYER)), 1f);
    }

    @Override
    public ResourceLocation getTextureLocation(Thief pEntity) {
        return TEXTURE;
    }
}
