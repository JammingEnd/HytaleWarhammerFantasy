package com.jammingmods.plugin.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.WarhammerFantasyPlugin;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class PlayerFactionInfoCommand extends AbstractPlayerCommand {
    public PlayerFactionInfoCommand() {
        super("PlayerInfo", "Print the info on the Player FactionComponent", false);
    }

    @Override
    protected void execute(
            @NonNullDecl CommandContext commandContext,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            @NonNullDecl PlayerRef playerRef,
            @NonNullDecl World world)
    {
        var comp = store.getComponent(ref, WarhammerFantasyPlugin.FACTION_COMPONENT_TYPE);
        Player player = (Player) store.getComponent(ref, Player.getComponentType());

        if(comp != null) {
            player.sendMessage(Message.raw("Faction ID: " + comp.getFactionId()));
            player.sendMessage(Message.raw("Faction Name: " + comp.getFactionName()));
            player.sendMessage(Message.raw("Subtype Name: " + comp.GetSubtypeName()));
            player.sendMessage(Message.raw("Traits:"));
            for(var trait : comp.getTraits().entrySet()) {
                player.sendMessage(Message.raw(" - " + trait.getKey() + ": " + trait.getValue()));
            }
        } else {
            player.sendMessage(Message.raw("No FactionComponent found on player."));
        }
    }
}
