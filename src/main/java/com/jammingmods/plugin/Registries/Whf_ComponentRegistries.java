package com.jammingmods.plugin.Registries;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.PlayerTraitComponents.Whf_DamageIncreaseComponent;
import com.jammingmods.plugin.Components.PlayerTraitComponents.Whf_SkavenGambleDamageComponent;
import com.jammingmods.plugin.Components.Whf_EffectOvertimeComponent;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.Components.Whf_ProximityStatComponent;
import com.jammingmods.plugin.WarhammerFantasyPlugin;

public class Whf_ComponentRegistries {
    public static ComponentType<EntityStore, Whf_FactionComponent> FACTION_COMPONENT_TYPE;
    public static ComponentType<EntityStore, Whf_DamageIncreaseComponent> DAMAGE_INCREASE_COMPONENT_TYPE;
    public static ComponentType<EntityStore, Whf_SkavenGambleDamageComponent> SKAVEN_GAMBLE_COMPONENT_TYPE;
    public static ComponentType<EntityStore, Whf_ProximityStatComponent> PROXIMITY_STAT_COMPONENT_TYPE;
    public static ComponentType<EntityStore, Whf_EffectOvertimeComponent> EFFECT_OVERTIME_COMPONENT_TYPE;
    public static void RegisterFactionComponent(WarhammerFantasyPlugin plugin) {
        FACTION_COMPONENT_TYPE = plugin.getEntityStoreRegistry().registerComponent(
                Whf_FactionComponent.class,
                "warhammerfantasy:faction_component",
                Whf_FactionComponent.CODEC);
    }
    public static void RegisterDamageIncreaseComponent(WarhammerFantasyPlugin plugin) {
        DAMAGE_INCREASE_COMPONENT_TYPE = plugin.getEntityStoreRegistry().registerComponent(
                Whf_DamageIncreaseComponent.class,
                "warhammerfantasy:damage_increase_component",
                Whf_DamageIncreaseComponent.CODEC);
    }

    public static void RegisterSkavenGambleComponent(WarhammerFantasyPlugin plugin) {
        SKAVEN_GAMBLE_COMPONENT_TYPE = plugin.getEntityStoreRegistry().registerComponent(
                Whf_SkavenGambleDamageComponent.class,
                "warhammerfantasy:skaven_gamble_component",
                Whf_SkavenGambleDamageComponent.CODEC);
    }
    public static void RegisterProximityStatComponent(WarhammerFantasyPlugin plugin) {
        PROXIMITY_STAT_COMPONENT_TYPE = plugin.getEntityStoreRegistry().registerComponent(
                Whf_ProximityStatComponent.class,
                "warhammerfantasy:proximity_stat_component",
                Whf_ProximityStatComponent.CODEC);
    }
    public static void RegisterEffectOvertimeComponent(WarhammerFantasyPlugin plugin) {
        EFFECT_OVERTIME_COMPONENT_TYPE = plugin.getEntityStoreRegistry().registerComponent(
                Whf_EffectOvertimeComponent.class,
                "warhammerfantasy:effect_overtime_component",
                Whf_EffectOvertimeComponent.CODEC);
    }
}
