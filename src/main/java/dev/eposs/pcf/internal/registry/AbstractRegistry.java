package dev.eposs.pcf.internal.registry;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegistry<T> implements IRegistry<T> {
    protected final Map<String, T> handlers = new ConcurrentHashMap<>();

    @Override
    public Optional<T> get(String id) {
        if (id == null || id.isEmpty()) return Optional.empty();
        return handlers.entrySet().stream()
                .filter(entry -> id.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
