package com.jammingmods.plugin;

import com.hypixel.hytale.component.ComponentRegistry;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Commands.CharacterSelectorCommand;
import com.jammingmods.plugin.Commands.PlayerFactionInfoCommand;
import com.jammingmods.plugin.Components.FactionComponent;
import com.jammingmods.plugin.FactionTypes.FactionType;
import com.jammingmods.plugin.Readers.FactionTypeParser;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class WarhammerFantasyPlugin extends JavaPlugin {

    public static ComponentType<EntityStore, FactionComponent> FACTION_COMPONENT_TYPE;
    public WarhammerFantasyPlugin(@NonNullDecl JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();

        // register components
        RegisterComponents();

        // commands
        this.getCommandRegistry().registerCommand(new CharacterSelectorCommand());
        this.getCommandRegistry().registerCommand(new PlayerFactionInfoCommand());
    }
    private void RegisterComponents() {
       FACTION_COMPONENT_TYPE = this.getEntityStoreRegistry().registerComponent(
               FactionComponent.class,
               "warhammerfantasy:faction_component",
               FactionComponent.CODEC);
    }


}
