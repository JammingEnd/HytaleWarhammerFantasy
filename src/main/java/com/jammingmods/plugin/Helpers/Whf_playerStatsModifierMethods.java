package com.jammingmods.plugin.Helpers;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.MovementSettings;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementConfig;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.RegeneratingValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.modules.entitystats.asset.EntityStatType;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.StaticModifier;
import com.hypixel.hytale.server.core.modules.physics.component.PhysicsValues;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.Map;

public class Whf_playerStatsModifierMethods {
    /// For available stats see the json files, i might want to index them via a command or something
    public static void ApplyPermanentStatModifiers(
            EntityStatMap stats, Map<String, Double> statModifiers,
            Player player,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            PlayerRef playerRef)
    {
        for (Map.Entry<String, Double> entry : statModifiers.entrySet()) {
            String statName = entry.getKey();
            Double modifierValue = entry.getValue();
            EntityStatValue statValue;
            switch (statName) {
                case "MaxHealth":
                    int healthIndex = DefaultEntityStatTypes.getHealth();
                    StaticModifier modifier = new StaticModifier(
                            Modifier.ModifierTarget.MAX,
                            StaticModifier.CalculationType.MULTIPLICATIVE,
                            1 + modifierValue.floatValue()
                    );
                    stats.putModifier(healthIndex, "subtype_MaxHealth", modifier);

                    player.sendMessage(Message.raw("Applied MaxHealth modifier: " + modifierValue.floatValue()));

                    break;
                case "MaxStamina":
                    int staminaIndex = DefaultEntityStatTypes.getStamina();
                    statValue = stats.get(staminaIndex);
                    StaticModifier staminaModifier = new StaticModifier(
                            Modifier.ModifierTarget.MAX,
                            StaticModifier.CalculationType.MULTIPLICATIVE,
                            1 + modifierValue.floatValue()
                    );
                    stats.putModifier(staminaIndex, "subtype_MaxStamina", staminaModifier);
                    break;
                case "MaxMana":
                    int manaIndex = DefaultEntityStatTypes.getMana();
                    statValue = stats.get(manaIndex);
                    StaticModifier manaModifier = new StaticModifier(
                            Modifier.ModifierTarget.MAX,
                            StaticModifier.CalculationType.MULTIPLICATIVE,
                            1 + modifierValue.floatValue()
                    );
                    stats.putModifier(manaIndex, "subtype_MaxMana", manaModifier);
                    break;
                case "MaxOxygen":
                    int oxygenIndex = DefaultEntityStatTypes.getOxygen();
                    statValue = stats.get(oxygenIndex);
                    StaticModifier oxygenModifier = new StaticModifier(
                            Modifier.ModifierTarget.MAX,
                            StaticModifier.CalculationType.MULTIPLICATIVE,
                            1 + modifierValue.floatValue()
                    );
                    stats.putModifier(oxygenIndex, "subtype_MaxOxygen", oxygenModifier);
                    break;
                case "SignatureBuildup":
                    // increase the amount of the Signature Buildup
                    int ultIndex = DefaultEntityStatTypes.getSignatureEnergy();
                    var guh = stats.get(ultIndex);
                    var gah = guh.getRegeneratingValues();
                    var geh = getRegeneratingValue(gah, modifierValue);
                    gah[0] = geh;
                    break;
            }

        }
    }

    @NonNullDecl
    private static RegeneratingValue getRegeneratingValue(RegeneratingValue[] gah, Double modifierValue) {
        var gih = gah[0];

        float amountIncrease = 1 + modifierValue.floatValue();

        EntityStatType.Regenerating gouh = new EntityStatType.Regenerating(
                (long) gih.getRegenerating().getInterval(),
                gih.getRegenerating().getAmount() * amountIncrease,
                gih.getRegenerating().getRegenType(),
                gih.getRegenerating().getConditions(),
                gih.getRegenerating().getModifiers()
        );
        var geh = new RegeneratingValue(gouh);
        return geh;
    }

    public static void ResetModifiers(
            EntityStatMap stats,
            Map<String, Double> statModifiers,
            Player player,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            PlayerRef playerRef)
    {
        for (Map.Entry<String, Double> entry : statModifiers.entrySet()) {
            String statName = entry.getKey();
            switch (statName) {
                case "MaxHealth":
                    int healthIndex = DefaultEntityStatTypes.getHealth();
                    stats.removeModifier(healthIndex, "subtype_MaxHealth");
                    player.sendMessage(Message.raw("Removed MaxHealth modifier."));
                    break;
                case "MaxStamina":
                    int staminaIndex = DefaultEntityStatTypes.getStamina();
                    stats.removeModifier(staminaIndex, "subtype_MaxStamina");
                    break;
                case "MaxMana":
                    int manaIndex = DefaultEntityStatTypes.getMana();
                    stats.removeModifier(manaIndex, "subtype_MaxMana");
                    break;
                case "MaxOxygen":
                    int oxygenIndex = DefaultEntityStatTypes.getOxygen();
                    stats.removeModifier(oxygenIndex, "subtype_MaxOxygen");
                    break;
                case "SignatureBuildup":
            }
        }
    }

    public static void ApplyConfigSettings(
            EntityStatMap stats,
            Map<String, Double> statModifiers,
            Player player,
            @NonNullDecl Store<EntityStore> store,
            @NonNullDecl Ref<EntityStore> ref,
            PlayerRef playerRef)
    {
        if(statModifiers.containsKey("MoveSpeed")){
            float modifierValue = statModifiers.get("MoveSpeed").floatValue();
            ModifyMovement(player, store, ref, playerRef,true, modifierValue);
        } else {
            ModifyMovement(player, store, ref, playerRef,false, 0f);
        }
    }

    private static void ModifyMovement(Player player, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, PlayerRef playerRef, boolean modify, float modifierValue) {
        MovementManager movementManager = store.getComponent(ref, MovementManager.getComponentType());
        MovementSettings movementSettings = movementManager.getSettings().clone();
        float speedIncrease = 1 + modifierValue;

        movementSettings.baseSpeed = 5.5f;
        if(modify){
            movementSettings.baseSpeed *= speedIncrease;
        }
        PhysicsValues physicsValues = store.getComponent(ref, PhysicsValues.getComponentType());

        movementManager.setDefaultSettings(
                movementSettings,
                physicsValues,
                player.getGameMode()
        );
        movementManager.applyDefaultSettings();
        movementManager.update(playerRef.getPacketHandler());
        player.sendMessage(Message.raw("Applied MoveSpeed modifier: " + modifierValue));
    }
}

