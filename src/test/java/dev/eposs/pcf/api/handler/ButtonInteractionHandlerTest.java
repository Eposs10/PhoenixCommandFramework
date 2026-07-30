package dev.eposs.pcf.api.handler;

import net.dv8tion.jda.api.components.buttons.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ButtonInteractionHandlerTest {

    private ButtonInteractionHandler handler;

    @BeforeEach
    void setUp() {
        ButtonInteractionHandler mock = mock(ButtonInteractionHandler.class);
        when(mock.getIdPrefix()).thenReturn(PREFIX);
        when(mock.withPrefixedId(any())).thenCallRealMethod();
        handler = mock;
    }

    @Test
    void givenButtonWithSuffix_whenWithPrefixedId_thenReturnsPrefixedButton() {
        // Arrange
        Button button = mock(Button.class);
        Button prefixedButton = mock(Button.class);

        when(button.getCustomId()).thenReturn(SUFFIX);
        when(button.withCustomId(ID)).thenReturn(prefixedButton);
        when(prefixedButton.getCustomId()).thenReturn(ID);

        // Act
        Button result = handler.withPrefixedId(button);

        // Assert
        assertEquals(prefixedButton, result);
        verify(button).withCustomId(ID);
    }

    @Test
    void givenButtonWithEmptySuffix_whenWithPrefixedId_thenReturnsPrefixedButton() {
        // Arrange
        Button button = mock(Button.class);
        Button prefixedButton = mock(Button.class);

        when(button.getCustomId()).thenReturn(EMPTY_ID);
        when(button.withCustomId(PREFIX)).thenReturn(prefixedButton);

        // Act
        Button result = handler.withPrefixedId(button);

        // Assert
        assertEquals(prefixedButton, result);
        verify(button).withCustomId(PREFIX);
    }
}
