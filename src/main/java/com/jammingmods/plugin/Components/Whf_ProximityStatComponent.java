package com.jammingmods.plugin.Components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.PlayerTraitComponents.ProximityModifier;
import com.jammingmods.plugin.Components.PlayerTraitComponents.TraitDamageType;
import com.jammingmods.plugin.Components.PlayerTraitComponents.Whf_EntityType;
import com.jammingmods.plugin.Components.PlayerTraitComponents.Whf_SkavenGambleDamageComponent;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.List;

public class Whf_ProximityStatComponent implements Component<EntityStore> {
    private List<ProximityModifier> ProximityModifiers;

    public void AddModifier(
            String statType,
            Double increasePerStack,
            int minimumEntities,
            int maxStacks,
            boolean stackable,
            TraitDamageType type,
            Whf_EntityType entityType
    ) {
        this.ProximityModifiers.add(new ProximityModifier(
                statType,
                increasePerStack,
                minimumEntities,
                maxStacks,
                0,
                stackable,
                type,
                entityType
        ));
    }
    public void ClearModifiers(){
        this.ProximityModifiers.clear();
    }
    public ProximityModifier[] GetGreaterThanInRangeModifiers(String statType, int entities, Whf_EntityType type){
        List<ProximityModifier> modifiers = new ArrayList<>();
        for (ProximityModifier mod : ProximityModifiers) {
            if(mod.EntityType == type && entities >= mod.MinimumEntities && mod.StatType == statType){
                modifiers.add(mod);
            }
        }
        return modifiers.toArray(new ProximityModifier[0]);
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new Whf_ProximityStatComponent(this);
    }

    public Whf_ProximityStatComponent(Whf_ProximityStatComponent component) {
        this.ProximityModifiers = component.ProximityModifiers;
    }
    public Whf_ProximityStatComponent() {}

    public static final BuilderCodec<Whf_ProximityStatComponent> CODEC = BuilderCodec.builder(
                    Whf_ProximityStatComponent.class, Whf_ProximityStatComponent::new)
            .build();
}

