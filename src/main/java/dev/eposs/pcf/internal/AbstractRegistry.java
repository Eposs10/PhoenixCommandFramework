package dev.eposs.pcf.internal;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractRegistry<T extends InteractionHandler<?>> {
    protected final Map<String, T> handlers = new ConcurrentHashMap<>();

    public abstract void register(T t);

    public void registerAll(List<T> ts) {
        ts.forEach(this::register);
    }

    public Optional<T> get(String id) {
        if (id == null || id.isEmpty()) return Optional.empty();
        return handlers.entrySet().stream()
                .filter(entry -> id.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
