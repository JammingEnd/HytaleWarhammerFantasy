package com.jammingmods.plugin.Components;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.PlayerTraitComponents.EffectOvertimeDef;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Whf_EffectOvertimeComponent implements Component<EntityStore> {

    private List<EffectOvertimeDef> activeEffects = new ArrayList<>();

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new Whf_EffectOvertimeComponent(this);
    }
    public Whf_EffectOvertimeComponent() {}
    public Whf_EffectOvertimeComponent(Whf_EffectOvertimeComponent other) {
        this.activeEffects = other.activeEffects;
    }

    public void AddEffectOrIncreaseStacks(String type, Double TickDamage, int Duration, int maxStacks, float Interval) {
        EffectOvertimeDef effect = TryGetEffect(type);
        if(effect != null) {
            effect.AddStack();
        } else {
            activeEffects.add(new EffectOvertimeDef(type, TickDamage, Duration, maxStacks, Interval));
        }
    }

    public EffectOvertimeDef[] getActiveEffects() {
        return this.activeEffects.toArray(new EffectOvertimeDef[0]);
    }
    public void DecreaseTicks(){
        Iterator<EffectOvertimeDef> it = activeEffects.iterator();
        while (it.hasNext()) {
            EffectOvertimeDef effect = it.next();
            if (effect.IsExpired()) {
                if(effect.GetCurrentStacks() > 0){
                    effect.RemoveStack();
                } else {
                    it.remove();
                }
            }
            effect.DecrementRemainingTicks();
        }
    }
    public void AddElapsedTime(float dt){
        for(EffectOvertimeDef effect : activeEffects){
            effect.AddElapsedTime(dt);
        }
    }

    public EffectOvertimeDef[] GetValidEffects(){
        return activeEffects.stream().filter(e -> e.IsValid()).toArray(EffectOvertimeDef[]::new);
    }

    public EffectOvertimeDef TryGetEffect(String type){
        return activeEffects.stream()
                .filter(e -> e.GetEffectType().equals(type))
                .findFirst()
                .orElse(null);
    }

    public static final BuilderCodec<Whf_EffectOvertimeComponent> CODEC = BuilderCodec.builder(
            Whf_EffectOvertimeComponent.class, Whf_EffectOvertimeComponent::new)
            .addField(
                    new KeyedCodec<>("ActiveEffects",
                            new ArrayCodec<>(EffectOvertimeDef.CODEC, EffectOvertimeDef[]::new)),
                    (data, value) -> data.activeEffects = new ArrayList<>(Arrays.asList(value)),
                    data -> data.activeEffects.toArray(EffectOvertimeDef[]::new)
            )
            .build();
}
