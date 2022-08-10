package com.thomasglasser.mcjtylearning.client.blocks.models.keys;

import net.minecraft.client.resources.model.ModelState;

import javax.annotation.Nullable;
import java.util.Objects;

public record GeneratorModelKey(boolean generating, boolean collecting, boolean actuallyGenerating, @Nullable ModelState modelState) {}