package dev.eposs.pcf.modal;

import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.EMPTY_ID;
import static dev.eposs.pcf.TestUtil.ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ModalRegistryTest {

    private ModalHandler createMockModalHandler(String customId) {
        ModalHandler mock = mock(ModalHandler.class);
        when(mock.getCustomId()).thenReturn(customId);
        return mock;
    }

    @Test
    void givenModalHandler_whenRegisterSingleModal_thenHandlerIsRetrievable() {
        // Arrange
        ModalHandler mockModal = createMockModalHandler(ID);

        // Act
        ModalRegistry.register(mockModal);

        // Assert
        assertTrue(ModalRegistry.getModal(ID).isPresent());
        assertEquals(mockModal, ModalRegistry.getModal(ID).get());
    }

    @Test
    void givenMultipleModalHandlers_whenRegisterMultipleModals_thenAllHandlersAreRetrievable() {
        // Arrange
        ModalHandler mockModal1 = createMockModalHandler("modal1");
        ModalHandler mockModal2 = createMockModalHandler("modal2");

        // Act
        ModalRegistry.register(mockModal1, mockModal2);

        // Assert
        assertTrue(ModalRegistry.getModal("modal1").isPresent());
        assertTrue(ModalRegistry.getModal("modal2").isPresent());
        assertEquals(mockModal1, ModalRegistry.getModal("modal1").get());
        assertEquals(mockModal2, ModalRegistry.getModal("modal2").get());
    }

    @Test
    void givenNonExistentModalId_whenGetModal_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ModalRegistry.getModal("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCustomId_whenGetModal_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ModalRegistry.getModal(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenEmptyCustomId_whenGetModal_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = ModalRegistry.getModal(EMPTY_ID);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenModalHandler_whenRegistered_thenOnlyExactIdMatches() {
        // Arrange
        ModalHandler mockModal = createMockModalHandler("exact-id");

        // Act
        ModalRegistry.register(mockModal);

        // Assert
        // Should only match exact ID, not partial
        assertTrue(ModalRegistry.getModal("exact-id").isPresent());
        assertFalse(ModalRegistry.getModal("exact-id-suffix").isPresent());
        assertFalse(ModalRegistry.getModal("prefix-exact-id").isPresent());
    }
}