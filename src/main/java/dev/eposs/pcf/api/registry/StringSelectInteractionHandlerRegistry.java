package dev.eposs.pcf.api.registry;

import dev.eposs.pcf.api.handler.StringSelectInteractionHandler;
import dev.eposs.pcf.internal.registry.AbstractRegistry;

public class StringSelectInteractionHandlerRegistry extends AbstractRegistry<StringSelectInteractionHandler> {
    @Override
    public void register(StringSelectInteractionHandler stringSelectInteractionHandler) {
        handlers.put(stringSelectInteractionHandler.getIdPrefix(), stringSelectInteractionHandler);
    }
}
