package com.jammingmods.plugin.FactionTypes;

import java.util.HashMap;
import java.util.Map;

public final class FactionType {

    public String TYPE_ID; // Empire:Imperial_Knight
    public String category;   // Empire
    public String name;       // Imperial Knight
    public String description;
    public Map<String, Double> traits;
    public FactionType(
            String typeId,
            String category,
            String name,
            String description,
            Map<String, Double> traits
    ) {
        this.TYPE_ID = typeId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.traits = Map.copyOf(traits);
    }
}
