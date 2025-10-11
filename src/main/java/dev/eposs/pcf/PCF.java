package dev.eposs.pcf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central static holder for global configuration used by the Phoenix Command Framework.
 * <p>
 * This class manages the bot owner ID and a concurrent set of trusted user IDs that can be
 * leveraged by commands, button handlers, etc. All state is stored in static fields and is
 * therefore process-wide. Initialize it once during application bootstrap via {@link #init(String, Set)}.
 * </p>
 * <p>
 * Thread-safety: the internal set of trusted users is backed by a {@link ConcurrentHashMap}
 * and is safe for concurrent access and modification. The {@code botOwnerID} is written once
 * during initialization and then read concurrently.
 * </p>
 */
public class PCF {
    private PCF() {
    }
    
    public static final Logger LOGGER = LoggerFactory.getLogger("PCF");

    /**
     * A flag indicating whether the Phoenix Command Framework has been initialized.
     */
    private static volatile boolean initialized = false;

    /**
     * Discord user ID of the bot owner. Must be set via {@link #init(String, Set)} before use.
     */
    private static String botOwnerID;

    /**
     * A live, thread-safe set of trusted user IDs. The content is cleared and optionally
     * repopulated on each call to {@link #init(String, Set)}.
     */
    private static final Set<String> trustedUsersIDs = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * Initializes the PhoenixCommandFramework with the given bot owner ID and an initial set of trusted user IDs.
     * This method must be called before using any other functionality of the framework.
     *
     * @param botOwnerID          the Discord user ID of the bot owner, must not be null or blank
     * @param initialTrustedUsers a set of Discord user IDs to initialize as trusted users, can be null
     * @throws IllegalStateException    if the framework is already initialized
     * @throws IllegalArgumentException if the bot owner ID is null or blank
     */
    public static synchronized void init(String botOwnerID, Set<String> initialTrustedUsers) throws IllegalStateException, IllegalArgumentException {
        if (initialized) {
            throw new IllegalStateException("PhoenixCommandFramework has already been initialized.");
        }

        if (botOwnerID == null || botOwnerID.isBlank()) {
            throw new IllegalArgumentException("Owner ID cannot be null or blank.");
        }

        PCF.botOwnerID = botOwnerID;
        trustedUsersIDs.clear();
        if (initialTrustedUsers != null) {
            trustedUsersIDs.addAll(initialTrustedUsers);
        }

        initialized = true;
    }

    /**
     * Checks if the PhoenixCommandFramework has been initialized.
     *
     * @return true if the framework has been initialized, false otherwise
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Retrieves the Discord user ID of the bot owner.
     * This method can only be used after the framework has been initialized.
     *
     * @return the Discord user ID of the bot owner
     * @throws IllegalStateException if the framework has not been initialized
     */
    public static String getBotOwnerID() throws IllegalStateException {
        checkInitialized();
        return botOwnerID;
    }

    /**
     * Retrieves the set of trusted user IDs configured in the framework.
     * This method can only be used if the framework has been initialized.
     *
     * @return a {@code Set} containing the Discord user IDs of trusted users
     * @throws IllegalStateException if the framework has not been initialized
     */
    public static Set<String> getTrustedUsers() throws IllegalStateException {
        checkInitialized();
        return trustedUsersIDs;
    }

    /**
     * Adds a user ID to the set of trusted user IDs.
     * This method can only be used after the framework has been initialized.
     *
     * @param userID the Discord user ID to add to the trusted users set, must not be null
     * @throws IllegalStateException if the framework has not been initialized
     */
    public static void addTrustedUser(String userID) throws IllegalStateException {
        checkInitialized();
        trustedUsersIDs.add(userID);
    }

    /**
     * Removes a user ID from the set of trusted user IDs.
     * This method can only be used after the framework has been initialized.
     *
     * @param userID the Discord user ID to remove, must not be null
     * @throws IllegalStateException if the framework has not been initialized
     */
    public static void removeTrustedUser(String userID) throws IllegalStateException {
        checkInitialized();
        trustedUsersIDs.remove(userID);
    }

    /**
     * Checks if the given user ID is in the set of trusted user IDs.
     * This method can only be used after the framework has been initialized.
     *
     * @param userID the Discord user ID to check, must not be null
     * @return true if the user ID is in the trusted users set, false otherwise
     * @throws IllegalStateException if the framework has not been initialized
     */
    public static boolean isTrustedUser(String userID) throws IllegalStateException {
        checkInitialized();
        return trustedUsersIDs.contains(userID);
    }

    /**
     * Ensures that the PhoenixCommandFramework has been properly initialized
     * prior to any operation that depends on its state.
     * <br>
     * This method throws an {@code IllegalStateException} if the framework is not yet initialized.
     * It is a safeguard to prevent interactions with the framework before calling {@link #init(String, Set)}.
     *
     * @throws IllegalStateException if {@link #init(String, Set)} has not been called beforehand
     */
    private static void checkInitialized() throws IllegalStateException {
        if (!initialized) throw new IllegalStateException("PhoenixCommandFramework.init() must be called before use.");
    }
}
