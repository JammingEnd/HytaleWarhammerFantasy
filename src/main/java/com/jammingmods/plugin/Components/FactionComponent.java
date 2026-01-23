package com.jammingmods.plugin.Components;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.map.MapCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;

public final class FactionComponent implements Component<EntityStore> {
    private String TYPE_ID; // Empire:Imperial_Knight
    private String Category;   // Empire
    private String Name;       // Imperial Knight
    private String Description;
    private Map<String, Double> Traits;
    public FactionComponent(String typeId, String category, String name, String description, Map<String, Double> traits) {
        this.TYPE_ID = typeId;
        this.Category = category;
        this.Name = name;
        this.Description = description;
        this.Traits = traits;
    }
    public FactionComponent(FactionComponent other) {
        this.TYPE_ID = other.TYPE_ID;
        this.Category = other.Category;
        this.Name = other.Name;
        this.Description = other.Description;
        this.Traits = other.Traits;
    }

    public FactionComponent() {

    }
    @NullableDecl
    @Override
    public Component<EntityStore> clone() {
        return new FactionComponent(this);
    }
    public void Update(FactionComponent other) {
        this.TYPE_ID = other.TYPE_ID;
        this.Category = other.Category;
        this.Name = other.Name;
        this.Description = other.Description;
        this.Traits = other.Traits;
    }

    public String getFactionId() {
        return TYPE_ID;
    }
    public String getFactionName() {
        return Category;
    }
    public String GetSubtypeName() {
        return Name;
    }
    public String getDescription() {
        return Description;
    }
    public Map<String, Double> getTraits() {
        return Traits;
    }
    public OptionalDouble getTraitValue(String traitName) {
        if (Traits.containsKey(traitName)) {;
            return OptionalDouble.of(Traits.get(traitName));
        }
        return OptionalDouble.empty();
    }

    // build codec here right?
    public static final BuilderCodec<FactionComponent> CODEC = BuilderCodec.builder(
            FactionComponent.class, FactionComponent::new)
            .append(new KeyedCodec<>("TYPE_ID", Codec.STRING),
                    (c, v) -> c.TYPE_ID = v,
                    c -> c.TYPE_ID).add()
            .append(new KeyedCodec<>("Category", Codec.STRING),
                    (c, v) -> c.Category = v,
                    c -> c.Category).add()
            .append(new KeyedCodec<>("Name", Codec.STRING),
                    (c, v) -> c.Name = v,
                    c -> c.Name).add()
            .append(new KeyedCodec<>("Description", Codec.STRING),
                    (c, v) -> c.Description = v,
                    c -> c.Description).add()
            .append(new KeyedCodec<>("Traits", new MapCodec<>(Codec.DOUBLE, HashMap::new)),
                    (c, v) -> c.Traits = v,
                    c -> c.Traits).add()
            .build();
}
