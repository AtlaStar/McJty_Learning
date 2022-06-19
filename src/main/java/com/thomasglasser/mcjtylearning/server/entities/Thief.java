package com.thomasglasser.mcjtylearning.server.entities;

import com.thomasglasser.mcjtylearning.server.entities.goals.AvoidEntityNoCombatGoal;
import com.thomasglasser.mcjtylearning.server.entities.goals.ThiefFindChestGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class Thief extends Animal
{
    private boolean stealing = false;

    public Thief(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder prepareAttributes()
    {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.FOLLOW_RANGE, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityNoCombatGoal<>(this, Player.class, 6.0f, 1.2, 1.2));
        this.goalSelector.addGoal(1, new ThiefFindChestGoal(this, 1.3));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    public void load(CompoundTag pCompound) {
        super.load(pCompound);
        stealing = pCompound.getBoolean("Stealing");
    }

    @Override
    public boolean save(CompoundTag pCompound) {
        pCompound.putBoolean("Stealing", stealing);
        return super.save(pCompound);
    }

    public boolean isStealing()
    {
        return stealing;
    }

    public void setStealing(boolean stealing)
    {
        this.stealing = stealing;
    }
}
