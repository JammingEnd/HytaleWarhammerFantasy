package com.jammingmods.plugin.Systems;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.DeathSystems;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.Whf_EffectOvertimeComponent;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.Readers.Whf_EffectOvertimeContainer;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Map;

public class Whf_OnKillSystem extends DeathSystems.OnDeathSystem {
    @Override
    public void onComponentAdded(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl DeathComponent deathComponent, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        var source = deathComponent.getDeathInfo().getSource();
        if(source instanceof Damage.EntitySource){
            var entitySource = (Damage.EntitySource) source;
            Ref<EntityStore> sourceRef = entitySource.getRef();
            Store<EntityStore> playerStore = entitySource.getRef().getStore();
            var faction_comp = playerStore.getComponent(sourceRef, Whf_ComponentRegistries.FACTION_COMPONENT_TYPE);
            if(faction_comp != null){
                // now we know the death is caused by the player
                HandleOnDeathEffects(playerStore, sourceRef, faction_comp, store.getComponent(ref, Whf_ComponentRegistries.EFFECT_OVERTIME_COMPONENT_TYPE));
            }
        }

    }

    private void HandleOnDeathEffects(Store<EntityStore> store, Ref<EntityStore> ref, Whf_FactionComponent f_comp, Whf_EffectOvertimeComponent e_c){
        EntityStatMap stats = store.getComponent(ref, EntityStatMap.getComponentType());
        for(Map.Entry<String, Double> entry : f_comp.getTraits().entrySet()){
            String type = entry.getKey();
            Double value = entry.getValue();
            switch(type) {
                case "HealthOnPoisonedKill":
                    if(e_c.TryGetEffect("SkavenPoison") != null ||
                            e_c.TryGetEffect("NurglePoison") != null)
                    {
                        int healthindex = DefaultEntityStatTypes.getHealth();
                        float max = stats.get(healthindex).getMax();
                        float newhealth = stats.get(healthindex).get() + (float)(max * value);
                        stats.setStatValue(healthindex, newhealth);
                    }
            }
        }
    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(EntityStatMap.getComponentType());
    }
}
