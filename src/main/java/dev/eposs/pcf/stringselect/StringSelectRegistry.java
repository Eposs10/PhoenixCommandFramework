package dev.eposs.pcf.stringselect;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class StringSelectRegistry {
    private StringSelectRegistry() {
    }

    private static final Map<String, StringSelectHandler> STRING_SELECTS = new ConcurrentHashMap<>();

    public static void register(StringSelectHandler action) {
        STRING_SELECTS.put(action.getIdPrefix(), action);
    }

    public static void register(@NotNull StringSelectHandler... actions) {
        for (StringSelectHandler action : actions) register(action);
    }

    @NotNull
    public static Optional<StringSelectHandler> getStringSelect(String customId) {
        return STRING_SELECTS.entrySet().stream()
                .filter(entry -> customId.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
