package com.jammingmods.plugin.Readers;

import com.google.gson.Gson;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Whf_EffectOvertimeParser {
    private static final Gson GSON = new Gson();
    private static final Path path = Path.of("EffectsCode/DamageOvertimeEffects.json");
    public static Whf_EffectOvertimeRoot parse(Path path) throws Exception {
        try (Reader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, Whf_EffectOvertimeRoot.class);
        }
    }

    public static Whf_EffectOvertimeContainer GetByType(String type) {
        try {
            var guh = parse(path);
            return Arrays.stream(guh.Types)
                    .filter(e -> e.Type.equals(type))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

