package dev.eposs.pcf.modal;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface ModalHandler {
    
    String getCustomId();

    void execute(@NotNull ModalInteractionEvent event) throws Exception;
}
