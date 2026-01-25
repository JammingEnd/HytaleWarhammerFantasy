package com.jammingmods.plugin.Registries;

import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PacketFilter;
import com.jammingmods.plugin.Watchers.Whf_RollChanceWatcher;

public class Whf_PacketRegistries {
    public static PacketFilter RollChanceWatcher;

    public static void DeregisterInbounds() {
        PacketAdapters.deregisterInbound(RollChanceWatcher);
    }
}
