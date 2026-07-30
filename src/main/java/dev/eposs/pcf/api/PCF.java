package dev.eposs.pcf.api;

import dev.eposs.pcf.api.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PCF {
    public static final Logger LOGGER = LoggerFactory.getLogger("PCF");

    private final String botOwnerID;
    private final Set<String> trustedUsersIDs = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public final ButtonInteractionHandlerRegistry BUTTONS = new ButtonInteractionHandlerRegistry();
    public final CommandInteractionHandlerRegistry COMMANDS = new CommandInteractionHandlerRegistry();
    public final EntitySelectInteractionHandlerRegistry ENTITY_SELECT_MENUS = new EntitySelectInteractionHandlerRegistry();
    public final ModalInteractionHandlerRegistry MODALS = new ModalInteractionHandlerRegistry();
    public final StringSelectInteractionHandlerRegistry STRING_SELECT_MENUS = new StringSelectInteractionHandlerRegistry();

    /**
     * Initializes the PhoenixCommandFramework with the given bot owner ID and an initial set of trusted user IDs.
     *
     * @param botOwnerID          the Discord user ID of the bot owner, must not be null or blank
     * @param initialTrustedUsers a set of Discord user IDs to initialize as trusted users, can be null
     * @throws IllegalArgumentException if the bot owner ID is null or blank
     */
    public PCF(String botOwnerID, Set<String> initialTrustedUsers) throws IllegalArgumentException {
        if (botOwnerID == null || botOwnerID.isBlank()) {
            throw new IllegalArgumentException("Owner ID cannot be null or blank.");
        }

        this.botOwnerID = botOwnerID;
        if (initialTrustedUsers != null) {
            trustedUsersIDs.addAll(initialTrustedUsers);
        }
    }

    /**
     * Initializes the PhoenixCommandFramework with the given bot owner ID and no initial trusted users.
     *
     * @param botOwnerID the Discord user ID of the bot owner, must not be null or blank
     * @throws IllegalArgumentException if the bot owner ID is null or blank
     */
    public PCF(String botOwnerID) {
        this(botOwnerID, null);
    }

    /**
     * Retrieves the Discord user ID of the bot owner.
     *
     * @return the Discord user ID of the bot owner
     */
    public String getBotOwnerID() {
        return botOwnerID;
    }

    /**
     * Retrieves the set of trusted user IDs configured in the framework.
     *
     * @return a {@code Set} containing the Discord user IDs of trusted users
     */
    public Set<String> getTrustedUsers() {
        return trustedUsersIDs;
    }

    /**
     * Adds a user ID to the set of trusted user IDs.
     *
     * @param userID the Discord user ID to add, must not be null
     * @return true if the user ID was successfully added to the trusted users set, false if it was already present
     */
    public boolean addTrustedUser(String userID) {
        return trustedUsersIDs.add(userID);
    }

    /**
     * Removes a user ID from the set of trusted user IDs.
     *
     * @param userID the Discord user ID to remove, must not be null
     * @return true if the user ID was successfully removed from the trusted users set, false if the user ID was not present in the set
     */
    public boolean removeTrustedUser(String userID) {
        return trustedUsersIDs.remove(userID);
    }

    /**
     * Checks if the given user ID is in the set of trusted user IDs.
     *
     * @param userID the Discord user ID to check, must not be null
     * @return true if the user ID is in the trusted users set, false otherwise
     */
    public boolean isTrustedUser(String userID) {
        return trustedUsersIDs.contains(userID);
    }
}
