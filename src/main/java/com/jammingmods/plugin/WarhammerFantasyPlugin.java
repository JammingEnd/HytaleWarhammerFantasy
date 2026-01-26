package com.jammingmods.plugin;

import com.hypixel.hytale.server.core.io.adapter.PacketAdapters;
import com.hypixel.hytale.server.core.io.adapter.PacketFilter;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.jammingmods.plugin.Commands.Whf_CharacterSelectorCommand;
import com.jammingmods.plugin.Commands.Whf_ClearModifierDataCommand;
import com.jammingmods.plugin.Commands.Whf_PlayerFactionInfoCommand;
import com.jammingmods.plugin.Commands.Whf_PrintPlayerStatDataCommand;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import com.jammingmods.plugin.Registries.Whf_SystemRegistries;
import com.jammingmods.plugin.Watchers.Whf_RollChanceWatcher;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class WarhammerFantasyPlugin extends JavaPlugin {

    public WarhammerFantasyPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();

        // register components
        Whf_ComponentRegistries.RegisterFactionComponent(this);
        Whf_ComponentRegistries.RegisterDamageIncreaseComponent(this);
        Whf_ComponentRegistries.RegisterSkavenGambleComponent(this);
        Whf_ComponentRegistries.RegisterProximityStatComponent(this);
        Whf_ComponentRegistries.RegisterEffectOvertimeComponent(this);

        // register systems
        Whf_SystemRegistries.RegisterSystems(this);

        // packets
        //Whf_PacketRegistries.RollChanceWatcher = PacketAdapters.registerInbound(new Whf_RollChanceWatcher());
        inboundFilter = PacketAdapters.registerInbound(new Whf_RollChanceWatcher());

        // commands
        this.getCommandRegistry().registerCommand(new Whf_CharacterSelectorCommand());
        this.getCommandRegistry().registerCommand(new Whf_PlayerFactionInfoCommand());
        this.getCommandRegistry().registerCommand(new Whf_PrintPlayerStatDataCommand());
        this.getCommandRegistry().registerCommand(new Whf_ClearModifierDataCommand());

    }

    @Override
    protected void shutdown()
    {
        //Whf_PacketRegistries.DeregisterInbounds();
        if (inboundFilter != null) {
            PacketAdapters.deregisterInbound(inboundFilter);
        }
    }

    private PacketFilter inboundFilter;

}
