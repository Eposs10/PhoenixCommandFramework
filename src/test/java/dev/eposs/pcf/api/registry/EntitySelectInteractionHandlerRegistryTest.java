// package dev.eposs.pcf.api.registry;
//
// import dev.eposs.pcf.api.handler.EntitySelectInteractionHandler;
// import org.junit.jupiter.api.Test;
//
// import static dev.eposs.pcf.TestUtil.*;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;
//
// class EntitySelectInteractionHandlerRegistryTest {
//
//     private EntitySelectInteractionHandler createMockEntitySelectHandler(String idPrefix) {
//         EntitySelectInteractionHandler mock = mock(EntitySelectInteractionHandler.class);
//         when(mock.getIdPrefix()).thenReturn(idPrefix);
//         return mock;
//     }
//    
//     @Test
//     void givenEntitySelectHandler_whenRegisterSingleHandler_thenHandlerIsRetrievableByPrefix() {
//         // Arrange
//         EntitySelectInteractionHandler mockHandler = createMockEntitySelectHandler(PREFIX);
//
//         // Act
//         EntitySelectInteractionHandlerRegistry.register(mockHandler);
//
//         // Assert
//         assertTrue(EntitySelectInteractionHandlerRegistry.getEntitySelect(ID).isPresent());
//         assertEquals(mockHandler, EntitySelectInteractionHandlerRegistry.getEntitySelect(ID).get());
//     }
//
//     @Test
//     void givenMultipleEntitySelectHandlers_whenRegisterMultipleHandlers_thenAllHandlersAreRetrievable() {
//         // Arrange
//         EntitySelectInteractionHandler mockHandler1 = createMockEntitySelectHandler("entity1:");
//         EntitySelectInteractionHandler mockHandler2 = createMockEntitySelectHandler("entity2:");
//
//         // Act
//         EntitySelectInteractionHandlerRegistry.register(mockHandler1, mockHandler2);
//
//         // Assert
//         assertTrue(EntitySelectInteractionHandlerRegistry.getEntitySelect("entity1:action").isPresent());
//         assertTrue(EntitySelectInteractionHandlerRegistry.getEntitySelect("entity2:action").isPresent());
//         assertEquals(mockHandler1, EntitySelectInteractionHandlerRegistry.getEntitySelect("entity1:action").get());
//         assertEquals(mockHandler2, EntitySelectInteractionHandlerRegistry.getEntitySelect("entity2:action").get());
//     }
//
//     @Test
//     void givenEntitySelectHandlerWithPrefix_whenRegistered_thenExactPrefixMatchWorks() {
//         // Arrange
//         EntitySelectInteractionHandler mockHandler = createMockEntitySelectHandler(PREFIX);
//
//         // Act
//         EntitySelectInteractionHandlerRegistry.register(mockHandler);
//
//         // Assert
//         assertTrue(EntitySelectInteractionHandlerRegistry.getEntitySelect(PREFIX).isPresent());
//         assertEquals(mockHandler, EntitySelectInteractionHandlerRegistry.getEntitySelect(PREFIX).get());
//     }
//
//     @Test
//     void givenNonExistentEntitySelectPrefix_whenGetEntitySelect_thenReturnsEmptyOptional() {
//         // Arrange // Act
//         var result = EntitySelectInteractionHandlerRegistry.getEntitySelect("non-existent");
//
//         // Assert
//         assertFalse(result.isPresent());
//     }
//
//     @Test
//     void givenNullCustomId_whenGetEntitySelect_thenReturnsEmptyOptional() {
//         // Arrange // Act
//         var result = EntitySelectInteractionHandlerRegistry.getEntitySelect(null);
//
//         // Assert
//         assertFalse(result.isPresent());
//     }
//
//     @Test
//     void givenEmptyCustomId_whenGetEntitySelect_thenReturnsEmptyOptional() {
//         // Arrange // Act
//         var result = EntitySelectInteractionHandlerRegistry.getEntitySelect(EMPTY_ID);
//
//         // Assert
//         assertFalse(result.isPresent());
//     }
//
//     @Test
//     void givenNonMatchingPrefixes_whenGetEntitySelect_thenReturnsEmptyOptional() {
//         // Arrange
//         EntitySelectInteractionHandler mockHandler = createMockEntitySelectHandler("test:");
//
//         // Act
//         EntitySelectInteractionHandlerRegistry.register(mockHandler);
//
//         // Assert
//         // Should not match if the prefix doesn't match
//         assertFalse(EntitySelectInteractionHandlerRegistry.getEntitySelect("other:test:action").isPresent());
//         assertFalse(EntitySelectInteractionHandlerRegistry.getEntitySelect("testaction").isPresent());
//         assertFalse(EntitySelectInteractionHandlerRegistry.getEntitySelect("tes:action").isPresent());
//     }
// }