package com.jammingmods.plugin.Components.PlayerTraitComponents;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class EffectOvertimeDef {
    private String type;
    public String GetEffectType(){return this.type;};
    private Double tickDamage;
    public Double GetDamagePerTick(){
        return this.tickDamage * currentStacks;
    }
    private int currentStacks;
    private int maxStacks;
    private int remainingTicks;
    private float interval;
    private float elapsedTime;

    public EffectOvertimeDef(){}
    public EffectOvertimeDef(String type, Double TickDamage, int Duration, int maxStacks, float Interval) {
        this.type = type;
        this.tickDamage = TickDamage;
        this.maxStacks = maxStacks;
        this.currentStacks = 1;
        this.remainingTicks = Duration;
        this.elapsedTime = 0;
        this.interval = Interval;
    }

    public void AddStack(){
        if (this.currentStacks < this.maxStacks){
            currentStacks++;
        }
    }
    public void RemoveStack(){
        if (this.currentStacks > 0){
            currentStacks--;
        }
    }
    public int GetCurrentStacks(){
        return this.currentStacks;
    }

    public float GetElapsedTime(){
        return this.elapsedTime;
    }

    public void ResetTickcount(int duration){
        this.remainingTicks = duration;
    }

    public void AddElapsedTime(float dt){
        this.elapsedTime += dt;
    }
    public void ResetElapsedTime(){
        this.elapsedTime = 0;
    }
    public void DecrementRemainingTicks(){
        this.remainingTicks--;
    }
    public boolean IsExpired(){
        return this.remainingTicks <= 0;
    }

    public boolean IsValid(){
        if(this.remainingTicks <= 0){
            return false;
        }
        if(this.elapsedTime >= this.interval){
            this.ResetElapsedTime();
            return true;
        }
        return false;
    }

    public String ToString(){
        return "Type: " + this.type +
                " - currentstack: " + this.currentStacks +
                " - maxstacks: " + this.maxStacks +
                " - remainingticks: " + this.remainingTicks +
                " - interval: " + this.interval +
                " - elapsedTime: " + this.elapsedTime;
    }

    public static final BuilderCodec<EffectOvertimeDef> CODEC = BuilderCodec.builder(
            EffectOvertimeDef.class, EffectOvertimeDef::new)
            .addField(new KeyedCodec<>("Type", Codec.STRING),
                    (data, value) -> data.type = value,
                    data -> data.type)
            .addField(new KeyedCodec<>("TickDamage", Codec.DOUBLE),
                    (data, value) -> data.tickDamage = value,
                    data -> data.tickDamage)
            .addField(new KeyedCodec<>("CurrentStacks", Codec.INTEGER),
                    (data, value) -> data.currentStacks = value,
                    data -> data.currentStacks)
            .addField(new KeyedCodec<>("MaxStacks", Codec.INTEGER),
                    (data, value) -> data.maxStacks = value,
                    data -> data.maxStacks)
            .addField(new KeyedCodec<>("RemainingTicks", Codec.INTEGER),
                    (data, value) -> data.remainingTicks = value,
                    data -> data.remainingTicks)
            .addField(new KeyedCodec<>("Interval", Codec.FLOAT),
                    (data, value) -> data.interval = value,
                    data -> data.interval)
            .addField(new KeyedCodec<>("ElapsedTime", Codec.FLOAT),
                    (data, value) -> data.elapsedTime = value,
                    data -> data.elapsedTime)
            .build();
}
