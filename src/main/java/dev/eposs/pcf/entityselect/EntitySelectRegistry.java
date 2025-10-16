package dev.eposs.pcf.entityselect;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EntitySelectRegistry {
    private EntitySelectRegistry() {
    }

    private static final Map<String, EntitySelectHandler> ENTITY_SELECTS = new ConcurrentHashMap<>();

    public static void register(EntitySelectHandler action) {
        ENTITY_SELECTS.put(action.getIdPrefix(), action);
    }

    public static void register(@NotNull EntitySelectHandler... actions) {
        for (EntitySelectHandler action : actions) register(action);
    }

    @NotNull
    public static Optional<EntitySelectHandler> getEntitySelect(String customId) {
        return ENTITY_SELECTS.entrySet().stream()
                .filter(entry -> customId.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
