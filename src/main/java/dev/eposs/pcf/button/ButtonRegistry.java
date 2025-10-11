package dev.eposs.pcf.button;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple registry for {@link ButtonHandler} implementations keyed by their {@link ButtonHandler#getIdPrefix()}.
 * <p>
 * It provides a lookup by a Discord component custom id by checking if the custom id starts with any
 * registered action's id prefix. Registration happens during class initialization and is not intended to be
 * modified at runtime.
 * </p>
 */
public class ButtonRegistry {
    private ButtonRegistry() {
    }

    private static final Map<String, ButtonHandler> BUTTONS = new ConcurrentHashMap<>();

    /**
     * Registers a single action in the registry using its {@link ButtonHandler#getIdPrefix()} as the key.
     *
     * @param action the action to register (must not be null)
     */
    public static void register(ButtonHandler action) {
        BUTTONS.put(action.getIdPrefix(), action);
    }

    /**
     * Registers multiple actions.
     *
     * @param actions the actions to register
     */
    public static void register(@NotNull ButtonHandler... actions) {
        for (ButtonHandler action : actions) register(action);
    }

    /**
     * Finds a registered {@link ButtonHandler} whose {@link ButtonHandler#getIdPrefix()} is a prefix of the given custom id.
     *
     * @param customId the custom id received from a {@code ButtonInteractionEvent}
     * @return an {@link Optional} containing the matching action if present; otherwise an empty optional
     */
    @NotNull
    public static Optional<ButtonHandler> getButton(String customId) {
        return BUTTONS.entrySet().stream()
                .filter(entry -> customId.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}
