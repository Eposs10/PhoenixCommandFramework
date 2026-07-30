package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.internal.InteractionHandler;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface ModalInteractionHandler extends InteractionHandler<ModalInteractionEvent> {

    String getCustomId();

    void execute(PCF pcf, @NotNull ModalInteractionEvent event) throws Exception;
}
