package com.jammingmods.plugin.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class Whf_PrintPlayerStatDataCommand extends AbstractPlayerCommand {
    public Whf_PrintPlayerStatDataCommand() {
        super("PlayerStats", "Display Current Player Stats");
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player player = (Player) store.getComponent(ref, Player.getComponentType());
        EntityStatMap stats = store.getComponent(ref, EntityStatMap.getComponentType());
        MovementManager movementManager = store.getComponent(ref, MovementManager.getComponentType());

        if (stats != null) {
            player.sendMessage(Message.raw("=== Player Stats ==="));

            player.sendMessage(Message.raw("Health: " + stats.get(DefaultEntityStatTypes.getHealth()).getMax()));
            //player.sendMessage(Message.raw("Modifiers: " + stats.get(DefaultEntityStatTypes.getHealth()).getModifiers() != null ? stats.get(DefaultEntityStatTypes.getHealth()).getModifiers().toString() : "None"));

            player.sendMessage(Message.raw("Stamina: " + stats.get(DefaultEntityStatTypes.getStamina()).getMax()));
            //player.sendMessage(Message.raw("Modifiers: " + stats.get(DefaultEntityStatTypes.getStamina()).getModifiers() != null ? stats.get(DefaultEntityStatTypes.getStamina()).getModifiers().toString() : "None"));

            player.sendMessage(Message.raw("Mana: " + stats.get(DefaultEntityStatTypes.getMana()).getMax()));
            //player.sendMessage(Message.raw("Modifiers: " + stats.get(DefaultEntityStatTypes.getMana()).getModifiers() != null ? stats.get(DefaultEntityStatTypes.getMana()).getModifiers().toString() : "None"));

            player.sendMessage(Message.raw("Base Movement Speed: " + movementManager.getSettings().baseSpeed));
        }

    }
}
