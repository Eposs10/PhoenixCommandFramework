package dev.eposs.pcf.event;

import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;
import org.jetbrains.annotations.NotNull;

public class PCFDefaultExceptionHandler implements IExceptionHandler {
    @Override
    public void handleException(Exception e, @NotNull IReplyCallback event) {
        IExceptionHandler.super.handleException(e, event);
    }
}
