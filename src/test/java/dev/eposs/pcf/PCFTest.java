package dev.eposs.pcf;

import dev.eposs.pcf.api.PCF;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PCFTest {

    @Test
    void givenValidBotOwnerIdAndTrustedUsers_whenConstruct_thenFrameworkIsInitializedCorrectly() {
        // Arrange
        String botOwnerId = "123456789";
        Set<String> initialTrustedUsers = Set.of("user1", "user2");

        // Act
        PCF pcf = new PCF(botOwnerId, initialTrustedUsers);

        // Assert
        assertEquals(botOwnerId, pcf.getBotOwnerID());
        assertEquals(initialTrustedUsers, pcf.getTrustedUsers());
    }

    @Test
    void givenNullBotOwnerId_whenConstruct_thenThrowsIllegalArgumentException() {
        // Arrange // Act // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new PCF(null, Set.of("user1"));
        });
    }

    @Test
    void givenBlankBotOwnerId_whenConstruct_thenThrowsIllegalArgumentException() {
        // Arrange // Act // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new PCF("   ", Set.of("user1"));
        });
    }

    @Test
    void givenEmptyBotOwnerId_whenConstruct_thenThrowsIllegalArgumentException() {
        // Arrange // Act // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new PCF("", Set.of("user1"));
        });
    }

    @Test
    void givenValidBotOwnerIdAndNullTrustedUsers_whenConstruct_thenFrameworkIsInitializedWithEmptyTrustedUsers() {
        // Arrange
        String botOwnerId = "123456789";

        // Act
        PCF pcf = new PCF(botOwnerId, null);

        // Assert
        assertEquals(botOwnerId, pcf.getBotOwnerID());
        assertTrue(pcf.getTrustedUsers().isEmpty());
    }

    @Test
    void givenValidBotOwnerIdAndEmptyTrustedUsers_whenConstruct_thenFrameworkIsInitializedWithEmptyTrustedUsers() {
        // Arrange
        String botOwnerId = "123456789";

        // Act
        PCF pcf = new PCF(botOwnerId, Set.of());

        // Assert
        assertEquals(botOwnerId, pcf.getBotOwnerID());
        assertTrue(pcf.getTrustedUsers().isEmpty());
    }

    @Test
    void givenFrameworkInstanceAndNewUser_whenAddTrustedUser_thenUserIsAddedToTrustedUsers() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of());

        // Act
        pcf.addTrustedUser("newUser");

        // Assert
        assertTrue(pcf.isTrustedUser("newUser"));
        assertEquals(Set.of("newUser"), pcf.getTrustedUsers());
    }

    @Test
    void givenFrameworkInstanceWithUsers_whenRemoveTrustedUser_thenUserIsRemovedFromTrustedUsers() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of("user1", "user2"));

        // Act
        pcf.removeTrustedUser("user1");

        // Assert
        assertFalse(pcf.isTrustedUser("user1"));
        assertTrue(pcf.isTrustedUser("user2"));
        assertEquals(Set.of("user2"), pcf.getTrustedUsers());
    }

    @Test
    void givenFrameworkInstanceWithUser_whenRemoveNonExistentTrustedUser_thenTrustedUsersUnchanged() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of("user1"));

        // Act
        pcf.removeTrustedUser("nonExistent");

        // Assert
        assertEquals(Set.of("user1"), pcf.getTrustedUsers());
    }

    @Test
    void givenFrameworkInstanceWithMultipleUsers_whenIsTrustedUser_thenReturnsCorrectResult() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of("user1", "user2", "user3"));

        // Act // Assert
        assertTrue(pcf.isTrustedUser("user1"));
        assertTrue(pcf.isTrustedUser("user2"));
        assertTrue(pcf.isTrustedUser("user3"));
        assertFalse(pcf.isTrustedUser("user4"));
    }

    @Test
    void givenFrameworkInstance_whenGetTrustedUsersReturnsLiveSet_thenModifyingSetAffectsFramework() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of("user1"));

        // Act
        Set<String> trustedUsers = pcf.getTrustedUsers();
        trustedUsers.add("user2");

        // Assert
        assertTrue(pcf.isTrustedUser("user2"));
        assertEquals(Set.of("user1", "user2"), pcf.getTrustedUsers());
    }

    @Test
    void givenFrameworkInstance_whenAddingExistingTrustedUser_thenReturnsFalse() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of("user1"));

        // Act
        boolean result = pcf.addTrustedUser("user1");

        // Assert
        assertFalse(result);
        assertEquals(Set.of("user1"), pcf.getTrustedUsers());
    }

    @Test
    void givenFrameworkInstance_whenRemovingExistingTrustedUser_thenReturnsTrue() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of("user1"));

        // Act
        boolean result = pcf.removeTrustedUser("user1");

        // Assert
        assertTrue(result);
        assertTrue(pcf.getTrustedUsers().isEmpty());
    }

    @Test
    void givenFrameworkInstance_whenRemovingNonExistingTrustedUser_thenReturnsFalse() {
        // Arrange
        String botOwnerId = "123456789";
        PCF pcf = new PCF(botOwnerId, Set.of("user1"));

        // Act
        boolean result = pcf.removeTrustedUser("user2");

        // Assert
        assertFalse(result);
        assertEquals(Set.of("user1"), pcf.getTrustedUsers());
    }
}