package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.internal.InteractionHandler;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

public interface ModalInteractionHandler extends InteractionHandler<ModalInteractionEvent> {

    String getCustomId();
}
