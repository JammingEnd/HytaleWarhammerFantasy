package com.jammingmods.plugin.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.item.config.ItemWeapon;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageEventSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.PlayerTraitComponents.TraitDamageType;
import com.jammingmods.plugin.Components.Whf_EffectOvertimeComponent;
import com.jammingmods.plugin.Components.Whf_FactionComponent;
import com.jammingmods.plugin.Readers.Whf_EffectOvertimeContainer;
import com.jammingmods.plugin.Readers.Whf_EffectOvertimeParser;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nullable;
import java.util.Map;

public class Whf_OnDamageEvents extends DamageEventSystem {
    @Override
    public void handle(
            int i,
            @NonNullDecl ArchetypeChunk<EntityStore> chunk,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl CommandBuffer<EntityStore> cmd,
            @NonNullDecl Damage damage)
    {

        Whf_FactionComponent component = chunk.getComponent(i, Whf_ComponentRegistries.FACTION_COMPONENT_TYPE);
        Ref<EntityStore> ref = null;
        Store<EntityStore> sourceStore = null;
        if(!(damage.getSource() instanceof Damage.ProjectileSource) && damage.getSource() instanceof Damage.EntitySource) {
            Damage.EntitySource source = (Damage.EntitySource)damage.getSource();
            ref = source.getRef();
            sourceStore = ref.getStore();
            // increase melee damage
            if(sourceStore != null){
                HandleDamageModifier(damage, ref, sourceStore, TraitDamageType.MELEE);
            }

        } else if (damage.getSource() instanceof Damage.ProjectileSource) {
            Damage.ProjectileSource source = (Damage.ProjectileSource)damage.getSource();
            ref = source.getRef();

            //TODO: check if the projectile is a magic thing or not

            if(ref.getStore().getComponent(ref, Whf_ComponentRegistries.FACTION_COMPONENT_TYPE) != null){
                sourceStore = ref.getStore();
                // increase ranged damage
                HandleDamageModifier(damage, ref, sourceStore, TraitDamageType.RANGED);

            }
        } else if (damage.getSource() instanceof Damage.EnvironmentSource) {
            Damage.EnvironmentSource source = (Damage.EnvironmentSource)damage.getSource();
            // ah damn
        }

        if(sourceStore != null){
            // handle all the damage modification data
            if(ref == null && !ref.isValid()) { return; }

            Player player = sourceStore.getComponent(ref, Player.getComponentType());
            HandleSkavenGambleDamage(damage, ref, store, player);

        }
    }

    private void HandleDamageModifier(Damage damage, Ref<EntityStore> ref, Store<EntityStore> store, TraitDamageType type){
        if(store.getComponent(ref, Whf_ComponentRegistries.DAMAGE_INCREASE_COMPONENT_TYPE) != null){
            var di_c = store.getComponent(ref, Whf_ComponentRegistries.DAMAGE_INCREASE_COMPONENT_TYPE);
            if(di_c.getType() == type){
                float oldDamage = damage.getAmount();
                float newDamage = damage.getAmount() * (1 + di_c.getValue().floatValue());

                damage.setAmount(newDamage);
            }
        }
    }

    private void HandleSkavenGambleDamage(Damage damage, Ref<EntityStore> ref, Store<EntityStore> store, Player player)
    {
        if(store.getComponent(ref, Whf_ComponentRegistries.SKAVEN_GAMBLE_COMPONENT_TYPE) != null){
            var s_c = store.getComponent(ref, Whf_ComponentRegistries.SKAVEN_GAMBLE_COMPONENT_TYPE);
            if(s_c.GetRoll()){
                float oldDamage = damage.getAmount();
                float newDamage = damage.getAmount() * (0.5f + s_c.getDamageMultiplier());
                damage.setAmount(newDamage);

                player.sendMessage(Message.raw("Roll succeed, multiplier: " + (1 + s_c.getDamageMultiplier())));
                player.sendMessage(Message.raw("Old Damage: " + oldDamage + " - New Damage: " + newDamage));

            } else {
                player.sendMessage(Message.raw("Roll failed"));
            }
        }
    }

    /// Manage the effects on EffectOvertimeComponent
    /// Duration is in ticks, 20 ticks is one second
    /// Interval is in ticks, so 20 interval means every second it fires
    private void ApplyOvertimeEffects(Whf_FactionComponent f_c, Ref<EntityStore> ref, Store<EntityStore> store, Player player){
        Whf_EffectOvertimeComponent eo_c = store.getComponent(ref, Whf_ComponentRegistries.EFFECT_OVERTIME_COMPONENT_TYPE);
        if(eo_c == null) {
            store.putComponent(ref, Whf_ComponentRegistries.EFFECT_OVERTIME_COMPONENT_TYPE, new Whf_EffectOvertimeComponent());
        }
        for (Map.Entry<String, Double> entry : f_c.getTraits().entrySet()){
            String type = entry.getKey();
            Double value = entry.getValue();
            switch (type){
                case "SkavenPoisonOnMelee":
                    Whf_EffectOvertimeContainer e_v = Whf_EffectOvertimeParser.GetByType("SkavenPoison");
                    eo_c.AddEffectOrIncreaseStacks(e_v.Type, e_v.TickDamage, e_v.DurationTicks, e_v.MaxStacks, e_v.Interval);
            }
        }
    }


    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        // basically every entity
        return EntityStatMap.getComponentType();
    }

    @Nullable
    @Override
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getGatherDamageGroup();
    }
}
