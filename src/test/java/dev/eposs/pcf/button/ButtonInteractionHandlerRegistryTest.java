package dev.eposs.pcf.button;

import dev.eposs.pcf.api.handler.ButtonInteractionHandler;
import dev.eposs.pcf.api.registry.ButtonInteractionHandlerRegistry;
import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.ID;
import static dev.eposs.pcf.TestUtil.PREFIX;
import static org.mockito.Mockito.*;

class ButtonInteractionHandlerRegistryTest {

    private ButtonInteractionHandler createMockButtonHandler(String idPrefix) {
        ButtonInteractionHandler mock = mock(ButtonInteractionHandler.class);
        when(mock.getIdPrefix()).thenReturn(idPrefix);
        return mock;
    }

    @Test
    void givenButtonHandler_whenRegisterSingleButton_thenButtonIsRetrievableByPrefix() {
        // Arrange
        ButtonInteractionHandler mockButton = createMockButtonHandler(PREFIX);

        // Act
        ButtonInteractionHandlerRegistry.register(mockButton);

        // Assert
        assertTrue(ButtonInteractionHandlerRegistry.getButton(ID).isPresent());
        assertEquals(mockButton, ButtonInteractionHandlerRegistry.getButton(ID).get());
    }

    @Test
    void givenMultipleButtonHandlers_whenRegisterMultipleButtons_thenAllButtonsAreRetrievable() {
        // Arrange
        ButtonInteractionHandler mockButton1 = createMockButtonHandler("button1:");
        ButtonInteractionHandler mockButton2 = createMockButtonHandler("button2:");

        // Act
        ButtonInteractionHandlerRegistry.register(mockButton1, mockButton2);

        // Assert
        assertTrue(ButtonInteractionHandlerRegistry.getButton("button1:action").isPresent());
        assertTrue(ButtonInteractionHandlerRegistry.getButton("button2:action").isPresent());
        assertEquals(mockButton1, ButtonInteractionHandlerRegistry.getButton("button1:action").get());
        assertEquals(mockButton2, ButtonInteractionHandlerRegistry.getButton("button2:action").get());
    }

    @Test
    void givenButtonHandlerWithPrefix_whenRegistered_thenExactPrefixMatchWorks() {
        // Arrange
        ButtonInteractionHandler mockButton = createMockButtonHandler(PREFIX);

        // Act
        ButtonInteractionHandlerRegistry.register(mockButton);

        // Assert
        assertTrue(ButtonInteractionHandlerRegistry.getButton(PREFIX).isPresent());
        assertEquals(mockButton, ButtonInteractionHandlerRegistry.getButton(PREFIX).get());
    }

    @Test
    void givenNonExistentButtonPrefix_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ButtonInteractionHandlerRegistry.getButton("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCustomId_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ButtonInteractionHandlerRegistry.getButton(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenEmptyCustomId_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ButtonInteractionHandlerRegistry.getButton("");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenMultipleButtonsWithDifferentPrefixes_whenRegistered_thenEachPrefixMatchesCorrectly() {
        // Arrange
        ButtonInteractionHandler mockButton1 = createMockButtonHandler("prefix1:");
        ButtonInteractionHandler mockButton2 = createMockButtonHandler("prefix2:");

        // Act
        ButtonInteractionHandlerRegistry.register(mockButton1, mockButton2);

        // Assert
        assertEquals(mockButton1, ButtonInteractionHandlerRegistry.getButton("prefix1:action").get());
        assertEquals(mockButton2, ButtonInteractionHandlerRegistry.getButton("prefix2:action").get());
    }

    @Test
    void givenNonMatchingPrefixes_whenGetButton_thenReturnsEmptyOptional() {
        // Arrange
        ButtonInteractionHandler mockButton = createMockButtonHandler("test:");

        // Act
        ButtonInteractionHandlerRegistry.register(mockButton);

        // Assert
        // Should not match if the prefix doesn't match
        assertFalse(ButtonInteractionHandlerRegistry.getButton("other:test:action").isPresent());
        assertFalse(ButtonInteractionHandlerRegistry.getButton("testaction").isPresent());
        assertFalse(ButtonInteractionHandlerRegistry.getButton("tes:").isPresent());
    }
}