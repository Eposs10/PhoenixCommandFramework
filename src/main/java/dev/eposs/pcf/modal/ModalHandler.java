package dev.eposs.pcf.modal;

import dev.eposs.pcf.PCF;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface ModalHandler {
    
    String getCustomId();

    void execute(PCF pcf, @NotNull ModalInteractionEvent event) throws Exception;
}
