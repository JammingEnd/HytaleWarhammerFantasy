package com.jammingmods.plugin.Services;

import com.jammingmods.plugin.FactionTypes.FactionType;
import com.jammingmods.plugin.Readers.FactionTypeParser;

import java.util.Map;

public final class FactionService {

    private static FactionService INSTANCE;

    private final Map<String, FactionType> factions;

    private FactionService() {
        FactionTypeParser parser = new FactionTypeParser();
        this.factions = parser.getRegistry();
    }

    public static void init() {
        if (INSTANCE != null) {
            throw new IllegalStateException("FactionService already initialized");
        }
        INSTANCE = new FactionService();
    }

    public static FactionService get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("FactionService not initialized");
        }
        return INSTANCE;
    }

    public FactionType getFaction(String id) {
        FactionType faction = factions.get(id);
        if (faction == null) {
            throw new IllegalArgumentException("Unknown faction: " + id);
        }
        return faction;
    }
}
