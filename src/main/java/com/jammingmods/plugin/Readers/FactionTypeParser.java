package com.jammingmods.plugin.Readers;

import com.jammingmods.plugin.FactionTypes.FactionType;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public final class FactionTypeParser {

    private final HashMap<String, FactionType> registry = new HashMap<>();

    public FactionTypeParser() {
        loadFromResources("FactionTypes");
    }

    public HashMap<String, FactionType> getRegistry() {
        return registry;
    }

    public static FactionType[] loadFactionTypes() {
        try {
            FactionTypeParser parser = new FactionTypeParser();
            var factionRegistry = parser.getRegistry();

            if (factionRegistry.isEmpty()) {
                throw new IllegalStateException("No faction types loaded");
            }

            // sorting based on category
            FactionType[] typeOrdered = factionRegistry.values()
                    .stream()
                    .sorted(Comparator.comparing(f -> f.category))
                    .toArray(FactionType[]::new);

            return typeOrdered;

        } catch (Exception e) {
            // Abort plugin startup
            throw new RuntimeException(
                    "Failed to load faction types", e
            );
        }
    }

    // ---------------- loading ----------------

    private void loadFromResources(String resourceDir) {
        try {
            ClassLoader cl = getClass().getClassLoader();
            URL dirUrl = cl.getResource(resourceDir);

            if (dirUrl == null) {
                throw new IllegalStateException(
                        "Resource directory not found: " + resourceDir
                );
            }

            if (dirUrl.getProtocol().equals("file")) {
                loadFromFileSystem(new File(dirUrl.toURI()));
            } else if (dirUrl.getProtocol().equals("jar")) {
                loadFromJar(dirUrl);
            } else {
                throw new IllegalStateException(
                        "Unsupported resource protocol: " + dirUrl.getProtocol()
                );
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load faction types", e);
        }
    }

    private void loadFromFileSystem(File dir) throws IOException {
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files == null) return;

        for (File file : files) {
            parseAndRegister(
                    file.getName(),
                    new FileInputStream(file)
            );
        }
    }

    private void loadFromJar(URL dirUrl) throws IOException {
        String jarPath = dirUrl.getPath().substring(5, dirUrl.getPath().indexOf("!"));
        try (JarFile jar = new JarFile(URLDecoder.decode(jarPath, StandardCharsets.UTF_8))) {

            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.getName().startsWith("FactionTypes/")
                        && entry.getName().endsWith(".json")) {

                    parseAndRegister(
                            entry.getName(),
                            jar.getInputStream(entry)
                    );
                }
            }
        }
    }

    private void parseAndRegister(String source, InputStream in) throws IOException {
        String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);

        for (FactionType faction : parse(json)) {
            String id = faction.category + ":" + faction.name;

            if (registry.containsKey(id)) {
                throw new IllegalStateException(
                        "Duplicate faction type: " + id + " (from " + source + ")"
                );
            }

            registry.put(id, faction);
        }
    }

    // ---------------- parser ----------------
    public static List<FactionType> parse(String json) {
        json = stripWhitespace(json);

        String category = extractString(json, "\"Type\":");
        String subTypesArray = extractArray(json, "\"SubTypes\":");

        List<FactionType> result = new ArrayList<>();

        for (String obj : splitObjects(subTypesArray)) {
            String name = extractString(obj, "\"Name\":");
            String id = category + ":" + name;
            String desc = extractString(obj, "\"Desc\":");
            String traitsObj = extractObject(obj, "\"Traits\":");

            Map<String, Double> traits = parseTraits(traitsObj);

            result.add(new FactionType(
                    id,
                    category,
                    name,
                    desc,
                    traits
            ));
        }

        return List.copyOf(result);
    }

    // ---------------- helpers ----------------

    private static String stripWhitespace(String s) {
        return s.replaceAll("\\s+", "");
    }

    private static String extractString(String src, String key) {
        int k = src.indexOf(key);
        if (k < 0) throw new IllegalStateException("Missing " + key);

        int start = src.indexOf('"', k + key.length()) + 1;
        int end = src.indexOf('"', start);

        return src.substring(start, end);
    }

    private static String extractArray(String src, String key) {
        int k = src.indexOf(key);
        if (k < 0) throw new IllegalStateException("Missing " + key);

        int start = src.indexOf('[', k);
        return extractBlock(src, start, '[', ']');
    }

    private static String extractObject(String src, String key) {
        int k = src.indexOf(key);
        if (k < 0) throw new IllegalStateException("Missing " + key);

        int start = src.indexOf('{', k);
        return extractBlock(src, start, '{', '}');
    }

    private static String extractBlock(String src, int start, char open, char close) {
        int depth = 0;
        for (int i = start; i < src.length(); i++) {
            char c = src.charAt(i);
            if (c == open) depth++;
            else if (c == close) {
                depth--;
                if (depth == 0) {
                    return src.substring(start, i + 1);
                }
            }
        }
        throw new IllegalStateException("Unbalanced " + open + close);
    }

    private static List<String> splitObjects(String array) {
        List<String> result = new ArrayList<>();
        int depth = 0;
        int start = -1;

        for (int i = 0; i < array.length(); i++) {
            char c = array.charAt(i);
            if (c == '{') {
                if (depth == 0) start = i;
                depth++;
            } else if (c == '}') {
                depth--;
                if (depth == 0 && start >= 0) {
                    result.add(array.substring(start, i + 1));
                }
            }
        }
        return result;
    }

    private static Map<String, Double> parseTraits(String obj) {
        Map<String, Double> traits = new HashMap<>();

        String body = obj.substring(1, obj.length() - 1);
        if (body.isEmpty()) return traits;

        for (String entry : body.split(",")) {
            String[] kv = entry.split(":");
            String key = kv[0].substring(1, kv[0].length() - 1);
            double value = Double.parseDouble(kv[1]);
            traits.put(key, value);
        }

        return traits;
    }
}
