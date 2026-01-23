package com.jammingmods.plugin.Systems;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefChangeSystem;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.Helpers.Whf_playerStatsModifierMethods;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class Whf_FactionComponentListenerSystem extends RefChangeSystem<EntityStore, Whf_FactionComponent> {
    @NonNullDecl
    @Override
    public ComponentType<EntityStore, Whf_FactionComponent> componentType() {
        return Whf_ComponentRegistries.FACTION_COMPONENT_TYPE;
    }

    @Override
    public void onComponentAdded(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl Whf_FactionComponent factionComponent, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        // update the player stats from traits.
        EntityStatMap stats = store.getComponent(ref, EntityStatMap.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (player != null) {
            player.sendMessage(Message.raw("Bonus stats from faction applied."));
        }

        if (stats != null) {
            Whf_playerStatsModifierMethods.ApplyPermanentStatModifiers(stats, factionComponent.getTraits(), player, store, ref, playerRef);
            Whf_playerStatsModifierMethods.ApplyConfigSettings(stats, factionComponent.getTraits(), player, store, ref, playerRef);
        }

    }

    @Override
    public void onComponentSet(@NonNullDecl Ref<EntityStore> ref, @NullableDecl Whf_FactionComponent factionComponent, @NonNullDecl Whf_FactionComponent t1, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {

    }

    @Override
    public void onComponentRemoved(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl Whf_FactionComponent factionComponent, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        EntityStatMap stats = store.getComponent(ref, EntityStatMap.getComponentType());
        Player player = store.getComponent(ref, Player.getComponentType());
        PlayerRef playerRef = store.getComponent(ref, PlayerRef.getComponentType());

        if (stats != null) {
            Whf_playerStatsModifierMethods.ResetModifiers(stats, factionComponent.getTraits(), player, store, ref, playerRef);
            Whf_playerStatsModifierMethods.ApplyConfigSettings(stats, factionComponent.getTraits(), player, store, ref, playerRef);
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return PlayerRef.getComponentType();
    }
}
