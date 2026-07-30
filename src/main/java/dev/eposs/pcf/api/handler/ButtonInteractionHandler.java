package dev.eposs.pcf.api.handler;

import dev.eposs.pcf.internal.InteractionHandler;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * Represents a handler for a Discord button interaction.
 * <p>
 * Implementations define an {@link #getIdPrefix()} used both for registration and for matching incoming
 * button custom ids. The registry can then route interactions to the appropriate handler when the
 * incoming custom id starts with the given prefix.
 * </p>
 */
public interface ButtonInteractionHandler extends InteractionHandler<ButtonInteractionEvent> {

    /**
     * The unique prefix used to identify and route button interactions for this action.
     * For example, if this returns "event-role:", a button with custom id "event-role:join" will
     * be routed to this action.
     *
     * @return the non-empty custom id prefix used for registry lookup
     */
    String getIdPrefix();

    /**
     * Utility to add this action's {@link #getIdPrefix()} to the provided button's custom id.
     * This is useful when creating components so their interactions can be routed back here.
     *
     * @param buttonWithSuffix a button whose custom id contains only the suffix part
     * @return a new {@link Button} instance with the full custom id consisting of prefix + suffix
     */
    default Button withPrefixedId(Button buttonWithSuffix) {
        return buttonWithSuffix.withCustomId(getIdPrefix() + buttonWithSuffix.getCustomId());
    }
}
