package com.jammingmods.plugin.Components.PlayerTraitComponents;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.EnumCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class Whf_DamageIncreaseComponent implements Component<EntityStore> {
    private TraitDamageType Type;
    public TraitDamageType getType(){return this.Type;}
    private Double Value;
    public Double getValue(){return this.Value;}

    public Whf_DamageIncreaseComponent(){}
    public Whf_DamageIncreaseComponent(TraitDamageType type, Double amount){
        this.Type = type;
        this.Value = amount;
    }
    public Whf_DamageIncreaseComponent(Whf_DamageIncreaseComponent other){
        this.Type = other.Type;
        this.Value = other.Value;
    }

    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new Whf_DamageIncreaseComponent(this);
    }

    public static final BuilderCodec<Whf_DamageIncreaseComponent> CODEC = BuilderCodec.builder(
                    Whf_DamageIncreaseComponent.class, Whf_DamageIncreaseComponent::new)
            .append(new KeyedCodec<>("Type", new EnumCodec<>(TraitDamageType.class)),
                    (c, v) -> c.Type = v,
                    c -> c.Type).add()
            .append(new KeyedCodec<>("Value", Codec.DOUBLE),
                    (c, v) -> c.Value = v,
                    c -> c.Value).add()
            .build();
}
