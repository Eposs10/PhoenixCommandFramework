// package dev.eposs.pcf.api.registry;
//
// import dev.eposs.pcf.api.handler.StringSelectInteractionHandler;
// import org.junit.jupiter.api.Test;
//
// import static dev.eposs.pcf.TestUtil.*;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;
//
// class StringSelectInteractionHandlerRegistryTest {
//
//     @Test
//     void givenStringSelectHandler_whenRegisterSingleHandler_thenHandlerIsRetrievableByPrefix() {
//         // Arrange
//         StringSelectInteractionHandler mockHandler = createMockStringSelectHandler(PREFIX);
//
//         // Act
//         StringSelectInteractionHandlerRegistry.register(mockHandler);
//
//         // Assert
//         assertTrue(StringSelectInteractionHandlerRegistry.getStringSelect(ID).isPresent());
//         assertEquals(mockHandler, StringSelectInteractionHandlerRegistry.getStringSelect(ID).get());
//     }
//
//     @Test
//     void givenMultipleStringSelectHandlers_whenRegisterMultipleHandlers_thenAllHandlersAreRetrievable() {
//         // Arrange
//         StringSelectInteractionHandler mockHandler1 = createMockStringSelectHandler("select1:");
//         StringSelectInteractionHandler mockHandler2 = createMockStringSelectHandler("select2:");
//
//         // Act
//         StringSelectInteractionHandlerRegistry.register(mockHandler1, mockHandler2);
//
//         // Assert
//         assertTrue(StringSelectInteractionHandlerRegistry.getStringSelect("select1:action").isPresent());
//         assertTrue(StringSelectInteractionHandlerRegistry.getStringSelect("select2:action").isPresent());
//         assertEquals(mockHandler1, StringSelectInteractionHandlerRegistry.getStringSelect("select1:action").get());
//         assertEquals(mockHandler2, StringSelectInteractionHandlerRegistry.getStringSelect("select2:action").get());
//     }
//
//     @Test
//     void givenStringSelectHandlerWithPrefix_whenRegistered_thenExactPrefixMatchWorks() {
//         // Arrange
//         StringSelectInteractionHandler mockHandler = createMockStringSelectHandler(PREFIX);
//
//         // Act
//         StringSelectInteractionHandlerRegistry.register(mockHandler);
//
//         // Assert
//         assertTrue(StringSelectInteractionHandlerRegistry.getStringSelect(PREFIX).isPresent());
//         assertEquals(mockHandler, StringSelectInteractionHandlerRegistry.getStringSelect(PREFIX).get());
//     }
//
//     @Test
//     void givenStringSelectHandlerWithPrefix_whenRegistered_thenLongerCustomIdMatches() {
//         // Arrange
//         StringSelectInteractionHandler mockHandler = createMockStringSelectHandler(PREFIX);
//
//         // Act
//         StringSelectInteractionHandlerRegistry.register(mockHandler);
//
//         // Assert
//         assertTrue(StringSelectInteractionHandlerRegistry.getStringSelect("test:complex-action-123").isPresent());
//         assertEquals(mockHandler, StringSelectInteractionHandlerRegistry.getStringSelect("test:complex-action-123").get());
//     }
//
//     @Test
//     void givenNonExistentStringSelectPrefix_whenGetStringSelect_thenReturnsEmptyOptional() {
//         // Arrange // Act
//         var result = StringSelectInteractionHandlerRegistry.getStringSelect("non-existent");
//
//         // Assert
//         assertFalse(result.isPresent());
//     }
//
//     @Test
//     void givenNullCustomId_whenGetStringSelect_thenReturnsEmptyOptional() {
//         // Arrange // Act
//         var result = StringSelectInteractionHandlerRegistry.getStringSelect(null);
//
//         // Assert
//         assertFalse(result.isPresent());
//     }
//
//     @Test
//     void givenEmptyCustomId_whenGetStringSelect_thenReturnsEmptyOptional() {
//         // Arrange // Act
//         var result = StringSelectInteractionHandlerRegistry.getStringSelect(EMPTY_ID);
//
//         // Assert
//         assertFalse(result.isPresent());
//     }
//
//     @Test
//     void givenNonMatchingPrefixes_whenGetStringSelect_thenReturnsEmptyOptional() {
//         // Arrange
//         StringSelectInteractionHandler mockHandler = createMockStringSelectHandler("test:");
//
//         // Act
//         StringSelectInteractionHandlerRegistry.register(mockHandler);
//
//         // Assert
//         // Should not match if the prefix doesn't match
//         assertFalse(StringSelectInteractionHandlerRegistry.getStringSelect("other:test:action").isPresent());
//         assertFalse(StringSelectInteractionHandlerRegistry.getStringSelect("testaction").isPresent());
//         assertFalse(StringSelectInteractionHandlerRegistry.getStringSelect("tes:action").isPresent());
//     }
//
//     private StringSelectInteractionHandler createMockStringSelectHandler(String idPrefix) {
//         StringSelectInteractionHandler mock = mock(StringSelectInteractionHandler.class);
//         when(mock.getIdPrefix()).thenReturn(idPrefix);
//         return mock;
//     }
// }