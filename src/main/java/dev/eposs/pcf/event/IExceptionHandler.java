package dev.eposs.pcf.event;

import dev.eposs.pcf.PhoenixCommandFramework;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.jetbrains.annotations.NotNull;

/**
 * Interface defining a mechanism for handling exceptions that occur while processing events.
 * Provides a default implementation for handling exceptions in a standardized manner, ensuring
 * proper logging and user notification through the callback mechanism.
 */
public interface IExceptionHandler {
    /**
     * Handles exceptions that occur during event processing by logging the error and
     * notifying the user through an {@link IReplyCallback}.
     *
     * @param e     the exception that occurred during event processing, must not be null
     * @param event the reply callback associated with the event, used to notify the user, must not be null
     */
    default void handleException(Exception e, @NotNull IReplyCallback event) {
        PhoenixCommandFramework.LOGGER.error(e.getMessage(), e);

        String msg = """
                There was an %s handling your request.
                %s
                """.formatted(e.getClass().getSimpleName(), e.getMessage());

        if (event.isAcknowledged()) {
            InteractionHook hook = event.getHook();
            hook.setEphemeral(true).sendMessage(msg).queue();
        } else {
            event.reply(msg).setEphemeral(true).queue();
        }
    }
}
