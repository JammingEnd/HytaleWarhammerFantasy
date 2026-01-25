package com.jammingmods.plugin.Watchers;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.protocol.Packet;
import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChain;
import com.hypixel.hytale.protocol.packets.interaction.SyncInteractionChains;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.io.adapter.PlayerPacketFilter;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.PlayerTraitComponents.Whf_SkavenGambleDamageComponent;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;

public class Whf_RollChanceWatcher implements PlayerPacketFilter {
    private static final int INTERACTION_PACKET_ID = 290;
    @Override
    public boolean test(PlayerRef playerRef, Packet packet) {
        if (packet.getId() != INTERACTION_PACKET_ID) return false;
        if (!(packet instanceof SyncInteractionChains interactionChains)) return false;
        for (SyncInteractionChain item : interactionChains.updates) {
            InteractionType interactionType = item.interactionType;
            switch (interactionType){
                case InteractionType.Primary:
                case InteractionType.Secondary:
                case InteractionType.Ability1:
                case InteractionType.Ability2:
                case InteractionType.Ability3:
                    if(item.initial){
                        HandleInitial(playerRef);
                    }
            }
        }
        return false;
    }


    private void HandleInitial(PlayerRef playerRef) {
        var entityRef = playerRef.getReference();
        if (entityRef == null || !entityRef.isValid()) return;

        var store = entityRef.getStore();
        var world = store.getExternalData().getWorld();

        world.execute(() -> {
            Player player = store.getComponent(entityRef, Player.getComponentType());
            if (player != null) {
                player.sendMessage(Message.raw("You right-clicked!"));
                RollSkavenGamble(entityRef, store);
            }
        });
    }

    private void RollSkavenGamble(Ref<EntityStore> ref, Store<EntityStore> store)
    {
        if(store.getComponent(ref, Whf_ComponentRegistries.SKAVEN_GAMBLE_COMPONENT_TYPE) != null){
            Whf_SkavenGambleDamageComponent c = store.getComponent(ref, Whf_ComponentRegistries.SKAVEN_GAMBLE_COMPONENT_TYPE);
            c.Roll();
        }
    }

}
