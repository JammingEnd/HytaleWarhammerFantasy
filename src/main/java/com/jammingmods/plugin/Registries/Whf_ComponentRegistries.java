package com.jammingmods.plugin.Registries;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.WarhammerFantasyPlugin;

public class Whf_ComponentRegistries {
    public static ComponentType<EntityStore, Whf_FactionComponent> FACTION_COMPONENT_TYPE;
    public static void RegisterFactionComponent(WarhammerFantasyPlugin plugin) {
        FACTION_COMPONENT_TYPE = plugin.getEntityStoreRegistry().registerComponent(
                Whf_FactionComponent.class,
                "warhammerfantasy:faction_component",
                Whf_FactionComponent.CODEC);
    }
}
