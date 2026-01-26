package com.jammingmods.plugin.Registries;

import com.jammingmods.plugin.Systems.*;
import com.jammingmods.plugin.WarhammerFantasyPlugin;

public class Whf_SystemRegistries
{
    public static void RegisterSystems(WarhammerFantasyPlugin plugin)
    {
        plugin.getEntityStoreRegistry().registerSystem(new Whf_FactionComponentListenerSystem());
        plugin.getEntityStoreRegistry().registerSystem(new Whf_SettingReapplyOnConnect());
        plugin.getEntityStoreRegistry().registerSystem(new Whf_OnDamageEvents());
        plugin.getEntityStoreRegistry().registerSystem(new Whf_OnKillSystem());
        plugin.getEntityStoreRegistry().registerSystem(new Whf_EffectOvertimeSystem());
        plugin.getEntityStoreRegistry().registerSystem(new Whf_ProximityTickSystem());
    }
}
