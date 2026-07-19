package dev.eposs.pcf.entityselect;

import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntitySelectRegistryTest {

    private EntitySelectHandler createMockEntitySelectHandler(String idPrefix) {
        EntitySelectHandler mock = mock(EntitySelectHandler.class);
        when(mock.getIdPrefix()).thenReturn(idPrefix);
        return mock;
    }
    
    @Test
    void givenEntitySelectHandler_whenRegisterSingleHandler_thenHandlerIsRetrievableByPrefix() {
        // Arrange
        EntitySelectHandler mockHandler = createMockEntitySelectHandler(PREFIX);

        // Act
        EntitySelectRegistry.register(mockHandler);

        // Assert
        assertTrue(EntitySelectRegistry.getEntitySelect(ID).isPresent());
        assertEquals(mockHandler, EntitySelectRegistry.getEntitySelect(ID).get());
    }

    @Test
    void givenMultipleEntitySelectHandlers_whenRegisterMultipleHandlers_thenAllHandlersAreRetrievable() {
        // Arrange
        EntitySelectHandler mockHandler1 = createMockEntitySelectHandler("entity1:");
        EntitySelectHandler mockHandler2 = createMockEntitySelectHandler("entity2:");

        // Act
        EntitySelectRegistry.register(mockHandler1, mockHandler2);

        // Assert
        assertTrue(EntitySelectRegistry.getEntitySelect("entity1:action").isPresent());
        assertTrue(EntitySelectRegistry.getEntitySelect("entity2:action").isPresent());
        assertEquals(mockHandler1, EntitySelectRegistry.getEntitySelect("entity1:action").get());
        assertEquals(mockHandler2, EntitySelectRegistry.getEntitySelect("entity2:action").get());
    }

    @Test
    void givenEntitySelectHandlerWithPrefix_whenRegistered_thenExactPrefixMatchWorks() {
        // Arrange
        EntitySelectHandler mockHandler = createMockEntitySelectHandler(PREFIX);

        // Act
        EntitySelectRegistry.register(mockHandler);

        // Assert
        assertTrue(EntitySelectRegistry.getEntitySelect(PREFIX).isPresent());
        assertEquals(mockHandler, EntitySelectRegistry.getEntitySelect(PREFIX).get());
    }

    @Test
    void givenNonExistentEntitySelectPrefix_whenGetEntitySelect_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = EntitySelectRegistry.getEntitySelect("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCustomId_whenGetEntitySelect_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = EntitySelectRegistry.getEntitySelect(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenEmptyCustomId_whenGetEntitySelect_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = EntitySelectRegistry.getEntitySelect(EMPTY_ID);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNonMatchingPrefixes_whenGetEntitySelect_thenReturnsEmptyOptional() {
        // Arrange
        EntitySelectHandler mockHandler = createMockEntitySelectHandler("test:");

        // Act
        EntitySelectRegistry.register(mockHandler);

        // Assert
        // Should not match if the prefix doesn't match
        assertFalse(EntitySelectRegistry.getEntitySelect("other:test:action").isPresent());
        assertFalse(EntitySelectRegistry.getEntitySelect("testaction").isPresent());
        assertFalse(EntitySelectRegistry.getEntitySelect("tes:action").isPresent());
    }
}