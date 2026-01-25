package com.jammingmods.plugin.Registries;

import com.jammingmods.plugin.Systems.Whf_FactionComponentListenerSystem;
import com.jammingmods.plugin.Systems.Whf_OnDamageEvents;
import com.jammingmods.plugin.Systems.Whf_SettingReapplyOnConnect;
import com.jammingmods.plugin.WarhammerFantasyPlugin;

public class Whf_SystemRegistries
{
    public static void RegisterSystems(WarhammerFantasyPlugin plugin)
    {
        plugin.getEntityStoreRegistry().registerSystem(new Whf_FactionComponentListenerSystem());
        plugin.getEntityStoreRegistry().registerSystem(new Whf_SettingReapplyOnConnect());
        plugin.getEntityStoreRegistry().registerSystem(new Whf_OnDamageEvents());
    }
}
