package dev.eposs.pcf.entityselect;

import net.dv8tion.jda.api.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface EntitySelectHandler {

    String getIdPrefix();

    void execute(@NotNull EntitySelectInteractionEvent event);

    default EntitySelectMenu withPrefixedId(@NotNull EntitySelectMenu menuWithSuffix) {
        return menuWithSuffix.createCopy().setCustomId(getIdPrefix() + menuWithSuffix.getCustomId()).build();
    }
}
