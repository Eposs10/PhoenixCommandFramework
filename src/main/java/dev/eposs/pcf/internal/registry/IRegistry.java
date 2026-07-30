package dev.eposs.pcf.internal.registry;

import java.util.List;
import java.util.Optional;

public interface IRegistry<T> {

    void register(T t);

    default void registerAll(List<T> ts) {
        ts.forEach(this::register);
    }
    
    Optional<T> get(String id);
}
