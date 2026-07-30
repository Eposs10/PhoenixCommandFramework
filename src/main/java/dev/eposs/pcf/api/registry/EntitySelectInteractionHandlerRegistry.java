package dev.eposs.pcf.api.registry;

import dev.eposs.pcf.api.handler.EntitySelectInteractionHandler;
import dev.eposs.pcf.internal.AbstractRegistry;

public class EntitySelectInteractionHandlerRegistry extends AbstractRegistry<EntitySelectInteractionHandler> {
    @Override
    public void register(EntitySelectInteractionHandler entitySelectInteractionHandler) {
        handlers.put(entitySelectInteractionHandler.getIdPrefix(), entitySelectInteractionHandler);
    }
}
