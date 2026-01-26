package com.jammingmods.plugin.Systems;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageModule;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.jammingmods.plugin.Components.PlayerTraitComponents.EffectOvertimeDef;
import com.jammingmods.plugin.Components.Whf_EffectOvertimeComponent;
import com.jammingmods.plugin.Registries.Whf_ComponentRegistries;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import javax.annotation.Nullable;

public class Whf_EffectOvertimeSystem extends EntityTickingSystem<EntityStore> {
    @Override
    public void tick(float dt, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        Whf_EffectOvertimeComponent c = archetypeChunk.getComponent(i, Whf_ComponentRegistries.EFFECT_OVERTIME_COMPONENT_TYPE);
        Ref<EntityStore> ref = archetypeChunk.getReferenceTo(i);

        c.AddElapsedTime(dt);

        EffectOvertimeDef[] validEffects = c.GetValidEffects();
        for (EffectOvertimeDef effect : validEffects) {
            Damage damage = new Damage(Damage.NULL_SOURCE, DamageCause.OUT_OF_WORLD, effect.GetDamagePerTick().floatValue());
            DamageSystems.executeDamage(ref, commandBuffer, damage);
            effect.DecrementRemainingTicks();
        }

    }

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.and(Whf_ComponentRegistries.EFFECT_OVERTIME_COMPONENT_TYPE);
    }
    @Nullable
    @Override
    public SystemGroup<EntityStore> getGroup() {
        return DamageModule.get().getGatherDamageGroup();
    }
}
