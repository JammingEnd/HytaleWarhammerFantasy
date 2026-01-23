package com.jammingmods.plugin.Helpers;

import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.modules.entitystats.modifier.Modifier;

import java.util.Map;

public class Whf_playerStatsModifierMethods {
    /// For available stats see the json files, i might want to index them via a command or something
    public static void ApplyPermanentStatModifiers(EntityStatMap stats, Map<String, Double> statModifiers) {
        for (Map.Entry<String, Double> entry : statModifiers.entrySet()) {
            String statName = entry.getKey();
            Double modifierValue = entry.getValue();
            EntityStatValue statValue;
            switch (statName)
            {
                case "MaxHealth":
                    int healthIndex = DefaultEntityStatTypes.getHealth();
                    statValue = stats.get(healthIndex);
                    Modifier modifier = new Modifier(Modifier.ModifierTarget.MAX) {
                        @Override
                        public float apply(float v) {
                            return v + modifierValue.floatValue();
                        }
                    };
                    stats.putModifier(healthIndex, "subtype_MaxHealth", modifier);
                    break;

            }

        }
    }
}
