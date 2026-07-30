package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.api.PCF;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import org.jetbrains.annotations.NotNull;

public interface StringSelectInteractionHandler {

    String getIdPrefix();

    void execute(PCF pcf, @NotNull StringSelectInteractionEvent event);

    default StringSelectMenu withPrefixedId(@NotNull StringSelectMenu menuWithSuffix) {
        return menuWithSuffix.createCopy().setCustomId(getIdPrefix() + menuWithSuffix.getCustomId()).build();
    }
}
