package com.jammingmods.plugin;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Commands.Whf_CharacterSelectorCommand;
import com.jammingmods.plugin.Commands.Whf_ClearModifierDataCommand;
import com.jammingmods.plugin.Commands.Whf_PlayerFactionInfoCommand;
import com.jammingmods.plugin.Commands.Whf_PrintPlayerStatDataCommand;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import com.jammingmods.plugin.Registries.Whf_SystemRegistries;
import com.jammingmods.plugin.Systems.Whf_FactionComponentListenerSystem;
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
        // register systems
        Whf_SystemRegistries.RegisterSystems(this);


        // commands
        this.getCommandRegistry().registerCommand(new Whf_CharacterSelectorCommand());
        this.getCommandRegistry().registerCommand(new Whf_PlayerFactionInfoCommand());
        this.getCommandRegistry().registerCommand(new Whf_PrintPlayerStatDataCommand());
        this.getCommandRegistry().registerCommand(new Whf_ClearModifierDataCommand());
    }



}
