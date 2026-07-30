package dev.eposs.pcf.api.registry;

import dev.eposs.pcf.api.handler.ModalInteractionHandler;
import dev.eposs.pcf.internal.registry.AbstractRegistry;

import java.util.Optional;

public class ModalInteractionHandlerRegistry extends AbstractRegistry<ModalInteractionHandler> {
    @Override
    public void register(ModalInteractionHandler modalInteractionHandler) {
        handlers.put(modalInteractionHandler.getCustomId(), modalInteractionHandler);
    }

    @Override
    public Optional<ModalInteractionHandler> get(String id) {
        if (id == null || id.isEmpty()) return Optional.empty();
        return Optional.ofNullable(handlers.get(id));
    }
}
