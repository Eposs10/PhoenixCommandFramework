package dev.eposs.pcf.api.registry;

import dev.eposs.pcf.api.handler.ButtonInteractionHandler;
import dev.eposs.pcf.internal.registry.AbstractRegistry;

public class ButtonInteractionHandlerRegistry extends AbstractRegistry<ButtonInteractionHandler> {
    @Override
    public void register(ButtonInteractionHandler buttonInteractionHandler) {
        handlers.put(buttonInteractionHandler.getIdPrefix(), buttonInteractionHandler);
    }
}
