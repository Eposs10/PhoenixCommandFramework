package dev.eposs.pcf.modal;

import dev.eposs.pcf.api.handler.ModalInteractionHandler;
import dev.eposs.pcf.api.registry.ModalInteractionHandlerRegistry;
import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.EMPTY_ID;
import static dev.eposs.pcf.TestUtil.ID;
import static org.mockito.Mockito.*;

class ModalInteractionHandlerRegistryTest {

    private ModalInteractionHandler createMockModalHandler(String customId) {
        ModalInteractionHandler mock = mock(ModalInteractionHandler.class);
        when(mock.getCustomId()).thenReturn(customId);
        return mock;
    }

    @Test
    void givenModalHandler_whenRegisterSingleModal_thenHandlerIsRetrievable() {
        // Arrange
        ModalInteractionHandler mockModal = createMockModalHandler(ID);

        // Act
        ModalInteractionHandlerRegistry.register(mockModal);

        // Assert
        assertTrue(ModalInteractionHandlerRegistry.getModal(ID).isPresent());
        assertEquals(mockModal, ModalInteractionHandlerRegistry.getModal(ID).get());
    }

    @Test
    void givenMultipleModalHandlers_whenRegisterMultipleModals_thenAllHandlersAreRetrievable() {
        // Arrange
        ModalInteractionHandler mockModal1 = createMockModalHandler("modal1");
        ModalInteractionHandler mockModal2 = createMockModalHandler("modal2");

        // Act
        ModalInteractionHandlerRegistry.register(mockModal1, mockModal2);

        // Assert
        assertTrue(ModalInteractionHandlerRegistry.getModal("modal1").isPresent());
        assertTrue(ModalInteractionHandlerRegistry.getModal("modal2").isPresent());
        assertEquals(mockModal1, ModalInteractionHandlerRegistry.getModal("modal1").get());
        assertEquals(mockModal2, ModalInteractionHandlerRegistry.getModal("modal2").get());
    }

    @Test
    void givenNonExistentModalId_whenGetModal_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ModalInteractionHandlerRegistry.getModal("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCustomId_whenGetModal_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ModalInteractionHandlerRegistry.getModal(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenEmptyCustomId_whenGetModal_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ModalInteractionHandlerRegistry.getModal(EMPTY_ID);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenModalHandler_whenRegistered_thenOnlyExactIdMatches() {
        // Arrange
        ModalInteractionHandler mockModal = createMockModalHandler("exact-id");

        // Act
        ModalInteractionHandlerRegistry.register(mockModal);

        // Assert
        // Should only match exact ID, not partial
        assertTrue(ModalInteractionHandlerRegistry.getModal("exact-id").isPresent());
        assertFalse(ModalInteractionHandlerRegistry.getModal("exact-id-suffix").isPresent());
        assertFalse(ModalInteractionHandlerRegistry.getModal("prefix-exact-id").isPresent());
    }
}