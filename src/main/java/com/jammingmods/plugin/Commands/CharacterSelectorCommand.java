package com.jammingmods.plugin.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.FactionComponent;
import com.jammingmods.plugin.UI.CharacterSelectorUIPage;
import com.jammingmods.plugin.WarhammerFantasyPlugin;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class CharacterSelectorCommand extends AbstractPlayerCommand {
    public CharacterSelectorCommand() {
        super("CharacterSelector", "Opens the GUI for selecting a character", false);
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player player = (Player) store.getComponent(ref, Player.getComponentType());
        CharacterSelectorUIPage page = new CharacterSelectorUIPage(playerRef);

        player.getPageManager().openCustomPage(ref, store, page);
    }
}
