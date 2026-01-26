package com.jammingmods.plugin.Components.PlayerTraitComponents;

public class ProximityModifier {
    public final String StatType;
    private final Double IncreasePerStack;
    public Double GetTotalIncrease(){
        return IncreasePerStack * Math.min(CurrentStacks, MaxStacks);
    }
    public final int MinimumEntities;
    public final int MaxStacks;
    public int CurrentStacks;
    public final boolean Stackable;
    public final TraitDamageType DamageType;
    public final Whf_EntityType EntityType;

    public ProximityModifier(
            String statType,
            Double increasePerStack,
            int minimumEntities,
            int maxStacks,
            int currentStacks,
            boolean stackable,
            TraitDamageType type,
            Whf_EntityType entityType) {
        this.StatType = statType;
        this.IncreasePerStack = increasePerStack;
        this.MinimumEntities = minimumEntities;
        this.MaxStacks = maxStacks;
        this.CurrentStacks = currentStacks;
        this.Stackable = stackable;
        this.DamageType = type;
        this.EntityType = entityType;
    }
}
