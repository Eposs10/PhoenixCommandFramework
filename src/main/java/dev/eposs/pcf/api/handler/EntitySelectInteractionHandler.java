package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.api.PCF;
import net.dv8tion.jda.api.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface EntitySelectInteractionHandler {

    String getIdPrefix();

    void execute(PCF pcf, @NotNull EntitySelectInteractionEvent event);

    default EntitySelectMenu withPrefixedId(@NotNull EntitySelectMenu menuWithSuffix) {
        return menuWithSuffix.createCopy().setCustomId(getIdPrefix() + menuWithSuffix.getCustomId()).build();
    }
}
