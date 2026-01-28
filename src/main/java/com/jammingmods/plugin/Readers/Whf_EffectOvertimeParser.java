package com.jammingmods.plugin.Readers;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Whf_EffectOvertimeParser {
    private static final Gson GSON = new Gson();
    public static Whf_EffectOvertimeRoot parse() throws Exception {
        InputStream is = Whf_EffectOvertimeParser.class
                .getClassLoader()
                .getResourceAsStream("EffectsCode/DamageOvertimeEffects.json");

        if (is == null) {
            throw new RuntimeException("DamageOvertimeEffects.json not found in plugin resources");
        }

        try (Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, Whf_EffectOvertimeRoot.class);
        }
    }

    public static Whf_EffectOvertimeContainer GetByType(String type) {
        try {
            var guh = parse();
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

