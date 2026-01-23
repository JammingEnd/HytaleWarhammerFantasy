package com.jammingmods.plugin.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.UI.Whf_CharacterSelectorUIPage;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class Whf_CharacterSelectorCommand extends AbstractPlayerCommand {
    public Whf_CharacterSelectorCommand() {
        super("CharacterSelector", "Opens the GUI for selecting a character", false);
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
        Player player = (Player) store.getComponent(ref, Player.getComponentType());
        Whf_CharacterSelectorUIPage page = new Whf_CharacterSelectorUIPage(playerRef);

        player.getPageManager().openCustomPage(ref, store, page);
    }
}
