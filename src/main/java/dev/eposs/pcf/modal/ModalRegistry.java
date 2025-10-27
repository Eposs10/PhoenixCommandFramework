package dev.eposs.pcf.modal;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ModalRegistry {
    private ModalRegistry() {
    }

    private static final Map<String, ModalHandler> MODALS = new ConcurrentHashMap<>();

    public static void register(@NotNull ModalHandler action) {
        MODALS.put(action.getCustomId(), action);
    }

    public static void register(@NotNull ModalHandler... actions) {
        for (ModalHandler action : actions) MODALS.put(action.getCustomId(), action);
    }

    @NotNull
    public static Optional<ModalHandler> getModal(String customId) {
        return Optional.ofNullable(MODALS.get(customId));
    }
}
