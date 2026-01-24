package com.jammingmods.plugin.Systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.Helpers.Whf_LogicHelpers;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class Whf_OnDamageEvents extends DamageEventSystem {
    @Override
    public void handle(
            int i,
            @NonNullDecl ArchetypeChunk<EntityStore> chunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> cmd,
            @NonNullDecl Damage damage)
    {
        //TODO: this method runs on the enemy being hit, i need to get the Source and check if it has FactionComponent
        Whf_FactionComponent component = chunk.getComponent(i, Whf_ComponentRegistries.FACTION_COMPONENT_TYPE);
        // Skaven gamble effect
        if(component.getTraitValue("EnableSkavenGambleMechanic").isPresent() && Whf_LogicHelpers.Roll(component.getTraitValue("EnableSkavenGambleMechanic").getAsDouble())){
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        // basically every entity
        return EntityStatMap.getComponentType();
    }
}
