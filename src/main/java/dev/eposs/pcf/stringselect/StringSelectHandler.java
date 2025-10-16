package dev.eposs.pcf.stringselect;

import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface StringSelectHandler {

    String getIdPrefix();

    void execute(@NotNull StringSelectInteractionEvent event);

    default StringSelectMenu withPrefixedId(@NotNull StringSelectMenu menuWithSuffix) {
        return menuWithSuffix.createCopy().setCustomId(getIdPrefix() + menuWithSuffix.getCustomId()).build();
    }
}
