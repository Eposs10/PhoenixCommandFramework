package dev.eposs.pcf.button;

import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.ID;
import static dev.eposs.pcf.TestUtil.PREFIX;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ButtonRegistryTest {

    private ButtonHandler createMockButtonHandler(String idPrefix) {
        ButtonHandler mock = mock(ButtonHandler.class);
        when(mock.getIdPrefix()).thenReturn(idPrefix);
        return mock;
    }

    @Test
    void givenButtonHandler_whenRegisterSingleButton_thenButtonIsRetrievableByPrefix() {
        // Arrange
        ButtonHandler mockButton = createMockButtonHandler(PREFIX);

        // Act
        ButtonRegistry.register(mockButton);

        // Assert
        assertTrue(ButtonRegistry.getButton(ID).isPresent());
        assertEquals(mockButton, ButtonRegistry.getButton(ID).get());
    }

    @Test
    void givenMultipleButtonHandlers_whenRegisterMultipleButtons_thenAllButtonsAreRetrievable() {
        // Arrange
        ButtonHandler mockButton1 = createMockButtonHandler("button1:");
        ButtonHandler mockButton2 = createMockButtonHandler("button2:");

        // Act
        ButtonRegistry.register(mockButton1, mockButton2);

        // Assert
        assertTrue(ButtonRegistry.getButton("button1:action").isPresent());
        assertTrue(ButtonRegistry.getButton("button2:action").isPresent());
        assertEquals(mockButton1, ButtonRegistry.getButton("button1:action").get());
        assertEquals(mockButton2, ButtonRegistry.getButton("button2:action").get());
    }

    @Test
    void givenButtonHandlerWithPrefix_whenRegistered_thenExactPrefixMatchWorks() {
        // Arrange
        ButtonHandler mockButton = createMockButtonHandler(PREFIX);

        // Act
        ButtonRegistry.register(mockButton);

        // Assert
        assertTrue(ButtonRegistry.getButton(PREFIX).isPresent());
        assertEquals(mockButton, ButtonRegistry.getButton(PREFIX).get());
    }

    @Test
    void givenNonExistentButtonPrefix_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ButtonRegistry.getButton("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCustomId_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ButtonRegistry.getButton(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenEmptyCustomId_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ButtonRegistry.getButton("");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenMultipleButtonsWithDifferentPrefixes_whenRegistered_thenEachPrefixMatchesCorrectly() {
        // Arrange
        ButtonHandler mockButton1 = createMockButtonHandler("prefix1:");
        ButtonHandler mockButton2 = createMockButtonHandler("prefix2:");

        // Act
        ButtonRegistry.register(mockButton1, mockButton2);

        // Assert
        assertEquals(mockButton1, ButtonRegistry.getButton("prefix1:action").get());
        assertEquals(mockButton2, ButtonRegistry.getButton("prefix2:action").get());
    }

    @Test
    void givenNonMatchingPrefixes_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange
        ButtonHandler mockButton = createMockButtonHandler("test:");

        // Act
        ButtonRegistry.register(mockButton);

        // Assert
        // Should not match if the prefix doesn't match
        assertFalse(ButtonRegistry.getButton("other:test:action").isPresent());
        assertFalse(ButtonRegistry.getButton("testaction").isPresent());
        assertFalse(ButtonRegistry.getButton("tes:").isPresent());
    }
}