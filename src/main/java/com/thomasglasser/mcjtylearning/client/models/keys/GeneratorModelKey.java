package com.thomasglasser.mcjtylearning.client.models.keys;

import net.minecraft.client.resources.model.ModelState;

import java.util.Objects;

public class GeneratorModelKey
{
    private final boolean generating;
    private final boolean collecting;
    private final boolean actuallyGenerating;
    private final ModelState modelState;

    public GeneratorModelKey(boolean generating, boolean collecting, boolean actuallyGenerating, ModelState modelState)
    {
        this.generating = generating;
        this.collecting = collecting;
        this.actuallyGenerating = actuallyGenerating;
        this.modelState = modelState;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GeneratorModelKey that = (GeneratorModelKey) obj;
        return generating == that.generating && collecting == that.collecting && actuallyGenerating == that.actuallyGenerating && Objects.equals(modelState, that.modelState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(generating, collecting, actuallyGenerating, modelState);
    }
}
