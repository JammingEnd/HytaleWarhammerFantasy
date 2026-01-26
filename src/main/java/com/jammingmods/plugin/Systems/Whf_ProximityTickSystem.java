package com.jammingmods.plugin.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.Whf_ProximityStatComponent;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nullable;

public class Whf_ProximityTickSystem extends EntityTickingSystem<EntityStore> {
    @Override
    public void tick(float v, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Whf_ProximityStatComponent c = archetypeChunk.getComponent(i, Whf_ComponentRegistries.PROXIMITY_STAT_COMPONENT_TYPE);
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);

        //TODO: Proximity calculation
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(Whf_ComponentRegistries.PROXIMITY_STAT_COMPONENT_TYPE);
    }

    @Nullable
    @Override
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getGatherDamageGroup();
    }
}
