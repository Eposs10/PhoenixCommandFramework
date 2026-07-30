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

    public Optional<T> getByID(String id) {
        if (id == null || id.isEmpty()) return Optional.empty();
        return handlers.entrySet().stream()
                .filter(entry -> id.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }

    /// Retrieves the first handler in the registry that is an instance of the specified class or its subclasses.
    ///
    /// @param <S>   the type parameter that extends the base handler type T
    /// @param clazz the class of the handler to retrieve
    /// @return an `Optional` containing the first handler of the specified class type, or `Optional.empty()` if none is found
    public <S extends T> Optional<T> get(Class<S> clazz) {
        return handlers.values().stream().filter(clazz::isInstance).findFirst();
    }
}
