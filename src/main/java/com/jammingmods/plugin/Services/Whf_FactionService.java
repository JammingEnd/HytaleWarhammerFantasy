package com.jammingmods.plugin.Services;

import com.jammingmods.plugin.FactionTypes.Whf_FactionType;
import com.jammingmods.plugin.Readers.Whf_FactionTypeParser;

import java.util.Map;

public final class Whf_FactionService {

    private static Whf_FactionService INSTANCE;

    private final Map<String, Whf_FactionType> factions;

    private Whf_FactionService() {
        Whf_FactionTypeParser parser = new Whf_FactionTypeParser();
        this.factions = parser.getRegistry();
    }

    public static void init() {
        if (INSTANCE != null) {
            throw new IllegalStateException("FactionService already initialized");
        }
        INSTANCE = new Whf_FactionService();
    }

    public static Whf_FactionService get() {
        if (INSTANCE == null) {
            throw new IllegalStateException("FactionService not initialized");
        }
        return INSTANCE;
    }

    public Whf_FactionType getFaction(String id) {
        Whf_FactionType faction = factions.get(id);
        if (faction == null) {
            throw new IllegalArgumentException("Unknown faction: " + id);
        }
        return faction;
    }
}
