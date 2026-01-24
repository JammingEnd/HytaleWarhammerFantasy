package com.jammingmods.plugin.Components.PlayerTraitComponents;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

enum TraitDamageType {
    ALL,
    MELEE,
    RANGED,
    MAGIC
}
public class Whf_DamageIncreaseComponent implements Component<EntityStore> {
    private TraitDamageType type;
    public TraitDamageType getType(){return this.type;}
    private Double value;
    public Double getValue(){return this.value;}

    public Whf_DamageIncreaseComponent(TraitDamageType type, Double amount){
        this.type = type;
        this.value = amount;
    }
    public Whf_DamageIncreaseComponent(Whf_DamageIncreaseComponent other){
        this.type = other.type;
        this.value = other.value;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new Whf_DamageIncreaseComponent(this);
    }
}
