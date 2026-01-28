package com.jammingmods.plugin.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.EntityEffect;
import com.hypixel.hytale.server.core.asset.type.entityeffect.config.OverlapBehavior;
import com.hypixel.hytale.server.core.entity.effect.EffectControllerComponent;
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
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import com.jammingmods.plugin.WarhammerFantasyPlugin;
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

        Ref<EntityStore> ref_player = null;
        Store<EntityStore> store_player = null;
        if(!(damage.getSource() instanceof Damage.ProjectileSource) && damage.getSource() instanceof Damage.EntitySource) {
            Damage.EntitySource source = (Damage.EntitySource)damage.getSource();
            ref_player = source.getRef();
            store_player = ref_player.getStore();
            // increase melee damage
            if(store_player != null){
                HandleDamageModifier(damage, ref_player, store_player, TraitDamageType.MELEE);
            }

        } else if (damage.getSource() instanceof Damage.ProjectileSource) {
            Damage.ProjectileSource source = (Damage.ProjectileSource)damage.getSource();
            ref_player = source.getRef();

            //TODO: check if the projectile is a magic thing or not

            if(ref_player.getStore().getComponent(ref_player, Whf_ComponentRegistries.FACTION_COMPONENT_TYPE) != null){
                store_player = ref_player.getStore();
                // increase ranged damage
                HandleDamageModifier(damage, ref_player, store_player, TraitDamageType.RANGED);

            }
        } else if (damage.getSource() instanceof Damage.EnvironmentSource) {
            Damage.EnvironmentSource source = (Damage.EnvironmentSource)damage.getSource();
            // ah damn
        }

        if(store_player != null){
            // handle all the damage modification data
            if(ref_player == null && !ref_player.isValid()) { return; }
            Whf_FactionComponent component = store_player.getComponent(ref_player, Whf_ComponentRegistries.FACTION_COMPONENT_TYPE);

            Player player = store_player.getComponent(ref_player, Player.getComponentType());
            HandleSkavenGambleDamage(damage, ref_player, store, player);

            ApplyOvertimeEffects(component, chunk.getReferenceTo(i), store, player, cmd);
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
    private void ApplyOvertimeEffects(Whf_FactionComponent f_c, Ref<EntityStore> ref, Store<EntityStore> store, Player player, CommandBuffer cmb){
        Whf_EffectOvertimeComponent eo_c = store.getComponent(ref, Whf_ComponentRegistries.EFFECT_OVERTIME_COMPONENT_TYPE);
        if (eo_c == null) {
            eo_c = new Whf_EffectOvertimeComponent();
            cmb.putComponent(ref, Whf_ComponentRegistries.EFFECT_OVERTIME_COMPONENT_TYPE, eo_c);
        }
        for (Map.Entry<String, Double> entry : f_c.getTraits().entrySet()){
            String type = entry.getKey();
            Double value = entry.getValue();
            switch (type){
                case "SkavenPoisonOnMelee":
                    Whf_EffectOvertimeContainer e_v = WarhammerFantasyPlugin.GetOvertimeEffect("SkavenPoison");
                    eo_c.AddEffectOrIncreaseStacks(e_v.Type, e_v.TickDamage, e_v.DurationTicks, e_v.MaxStacks, (float) e_v.IntervalTicks);
                    ApplyEffect("SkavenPoison_Effect", e_v.DurationTicks, OverlapBehavior.OVERWRITE, ref ,store);
            }
        }
    }

    private void ApplyEffect(String key, float duration, OverlapBehavior overlapBehavior, Ref<EntityStore> ref, Store<EntityStore> store){
        EntityEffect effect = EntityEffect.getAssetMap().getAsset(key);
        EffectControllerComponent controller = store.getComponent(ref, EffectControllerComponent.getComponentType());

        if(controller != null && effect != null) {
            controller.addEffect(ref, effect, duration, overlapBehavior, store);
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
