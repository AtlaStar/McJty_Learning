package com.thomasglasser.mcjtylearning.server.entities.goals;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class AvoidEntityNoCombatGoal<T extends LivingEntity> extends Goal
{
    protected final PathfinderMob mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    protected T toAvoid;
    protected final float maxDistance;
    protected Path path;
    protected final PathNavigation pathNavigation;
    protected final Class<T> avoidClass;
    private final TargetingConditions avoidEntityTargeting;

    public AvoidEntityNoCombatGoal(PathfinderMob mob, Class<T> entityClassToAvoid, float maxDistance, double walkSpeedModifier, double sprintSpeedModifier)
    {
        this.mob = mob;
        this.avoidClass = entityClassToAvoid;
        this.maxDistance = maxDistance;
        this.walkSpeedModifier = walkSpeedModifier;
        this.sprintSpeedModifier = sprintSpeedModifier;
        this.pathNavigation = mob.getNavigation();
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forNonCombat().range(maxDistance).selector(EntitySelector.NO_CREATIVE_OR_SPECTATOR::test);
    }

    @Override
    public boolean canUse() {
        List<T> entitiesOfClass = this.mob.level.getEntitiesOfClass(this.avoidClass, this.mob.getBoundingBox().inflate(this.maxDistance, 3.0D, this.maxDistance), (ent) -> true);
        this.toAvoid = this.mob.level.getNearestEntity(entitiesOfClass, this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ());
        if (this.toAvoid == null) return false;
        Vec3 vec3 = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
        if (vec3 == null)
        {
            return false;
        } else if (this.toAvoid.distanceToSqr(vec3) < this.toAvoid.distanceToSqr(this.mob)) {
            return false;
        }
        this.path = this.pathNavigation.createPath(vec3.x, vec3.y, vec3.z, 0);
        return this.path != null;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pathNavigation.isDone();
    }

    @Override
    public void start() {
        this.pathNavigation.moveTo(this.path, this.walkSpeedModifier);
    }

    @Override
    public void stop() {
        this.toAvoid = null;
    }

    @Override
    public void tick() {
        if (this.mob.distanceToSqr(this.toAvoid) < 49.0D)
        {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        }
        else
        {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }
}
