package dev.eposs.pcf.internal;

import dev.eposs.pcf.api.handler.ExceptionHandler;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.jetbrains.annotations.NotNull;

public class DefaultExceptionHandler implements ExceptionHandler {
    @Override
    public void handleException(Exception e, @NotNull IReplyCallback event) {
        ExceptionHandler.super.handleException(e, event);
    }
}
