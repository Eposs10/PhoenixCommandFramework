package dev.eposs.pcf.stringselect;

import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StringSelectRegistryTest {

    @Test
    void givenStringSelectHandler_whenRegisterSingleHandler_thenHandlerIsRetrievableByPrefix() {
        // Arrange
        StringSelectHandler mockHandler = createMockStringSelectHandler(PREFIX);

        // Act
        StringSelectRegistry.register(mockHandler);

        // Assert
        assertTrue(StringSelectRegistry.getStringSelect(ID).isPresent());
        assertEquals(mockHandler, StringSelectRegistry.getStringSelect(ID).get());
    }

    @Test
    void givenMultipleStringSelectHandlers_whenRegisterMultipleHandlers_thenAllHandlersAreRetrievable() {
        // Arrange
        StringSelectHandler mockHandler1 = createMockStringSelectHandler("select1:");
        StringSelectHandler mockHandler2 = createMockStringSelectHandler("select2:");

        // Act
        StringSelectRegistry.register(mockHandler1, mockHandler2);

        // Assert
        assertTrue(StringSelectRegistry.getStringSelect("select1:action").isPresent());
        assertTrue(StringSelectRegistry.getStringSelect("select2:action").isPresent());
        assertEquals(mockHandler1, StringSelectRegistry.getStringSelect("select1:action").get());
        assertEquals(mockHandler2, StringSelectRegistry.getStringSelect("select2:action").get());
    }

    @Test
    void givenStringSelectHandlerWithPrefix_whenRegistered_thenExactPrefixMatchWorks() {
        // Arrange
        StringSelectHandler mockHandler = createMockStringSelectHandler(PREFIX);

        // Act
        StringSelectRegistry.register(mockHandler);

        // Assert
        assertTrue(StringSelectRegistry.getStringSelect(PREFIX).isPresent());
        assertEquals(mockHandler, StringSelectRegistry.getStringSelect(PREFIX).get());
    }

    @Test
    void givenStringSelectHandlerWithPrefix_whenRegistered_thenLongerCustomIdMatches() {
        // Arrange
        StringSelectHandler mockHandler = createMockStringSelectHandler(PREFIX);

        // Act
        StringSelectRegistry.register(mockHandler);

        // Assert
        assertTrue(StringSelectRegistry.getStringSelect("test:complex-action-123").isPresent());
        assertEquals(mockHandler, StringSelectRegistry.getStringSelect("test:complex-action-123").get());
    }

    @Test
    void givenNonExistentStringSelectPrefix_whenGetStringSelect_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = StringSelectRegistry.getStringSelect("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCustomId_whenGetStringSelect_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = StringSelectRegistry.getStringSelect(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenEmptyCustomId_whenGetStringSelect_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = StringSelectRegistry.getStringSelect(EMPTY_ID);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNonMatchingPrefixes_whenGetStringSelect_thenReturnsEmptyOptional() {
        // Arrange
        StringSelectHandler mockHandler = createMockStringSelectHandler("test:");

        // Act
        StringSelectRegistry.register(mockHandler);

        // Assert
        // Should not match if the prefix doesn't match
        assertFalse(StringSelectRegistry.getStringSelect("other:test:action").isPresent());
        assertFalse(StringSelectRegistry.getStringSelect("testaction").isPresent());
        assertFalse(StringSelectRegistry.getStringSelect("tes:action").isPresent());
    }

    private StringSelectHandler createMockStringSelectHandler(String idPrefix) {
        StringSelectHandler mock = mock(StringSelectHandler.class);
        when(mock.getIdPrefix()).thenReturn(idPrefix);
        return mock;
    }
}