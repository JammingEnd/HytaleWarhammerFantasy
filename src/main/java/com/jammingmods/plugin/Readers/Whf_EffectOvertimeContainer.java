package com.jammingmods.plugin.Readers;



public class Whf_EffectOvertimeContainer {
    public final String Type;
    public final Double TickDamage;
    public final int MaxStacks;
    public final int DurationTicks;
    public final int IntervalTicks;

    public Whf_EffectOvertimeContainer(String type, Double tickDamage, int maxStacks, int durationTicks, int interval) {
        this.Type = type;
        this.TickDamage = tickDamage;
        this.MaxStacks = maxStacks;
        this.DurationTicks = durationTicks;
        this.IntervalTicks = interval;
    }
}
