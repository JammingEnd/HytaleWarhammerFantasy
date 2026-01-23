package com.jammingmods.plugin;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Commands.Whf_CharacterSelectorCommand;
import com.jammingmods.plugin.Commands.Whf_PlayerFactionInfoCommand;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class WarhammerFantasyPlugin extends JavaPlugin {

    public static Whf_ComponentRegistries componentRegistries;
    public WarhammerFantasyPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();

        // register components
        componentRegistries = new Whf_ComponentRegistries();
        componentRegistries.RegisterFactionComponent(this);

        // commands
        this.getCommandRegistry().registerCommand(new Whf_CharacterSelectorCommand());
        this.getCommandRegistry().registerCommand(new Whf_PlayerFactionInfoCommand());
    }



}
