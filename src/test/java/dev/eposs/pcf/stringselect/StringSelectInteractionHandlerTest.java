package dev.eposs.pcf.stringselect;

import dev.eposs.pcf.api.handler.StringSelectInteractionHandler;
import net.dv8tion.jda.api.components.selections.StringSelectMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StringSelectInteractionHandlerTest {

    private StringSelectInteractionHandler handler;

    @BeforeEach
    void setUp() {
        StringSelectInteractionHandler mock = mock(StringSelectInteractionHandler.class);
        when(mock.getIdPrefix()).thenReturn(PREFIX);
        when(mock.withPrefixedId(any())).thenCallRealMethod();
        handler = mock;
    }

    @Test
    void givenMenuWithSuffix_whenWithPrefixedId_thenReturnsPrefixedMenu() {
        // Arrange
        StringSelectMenu menu = mock(StringSelectMenu.class);
        StringSelectMenu.Builder builder = mock(StringSelectMenu.Builder.class);
        StringSelectMenu prefixedMenu = mock(StringSelectMenu.class);

        when(menu.createCopy()).thenReturn(builder);
        when(builder.setCustomId(ID)).thenReturn(builder);
        when(builder.build()).thenReturn(prefixedMenu);
        when(menu.getCustomId()).thenReturn(SUFFIX);
        when(prefixedMenu.getCustomId()).thenReturn(ID);

        // Act
        StringSelectMenu result = handler.withPrefixedId(menu);

        // Assert
        assertEquals(prefixedMenu, result);
        verify(builder).setCustomId(ID);
    }

    @Test
    void givenMenuWithEmptySuffix_whenWithPrefixedId_thenReturnsPrefixedMenu() {
        // Arrange
        StringSelectMenu menu = mock(StringSelectMenu.class);
        StringSelectMenu.Builder builder = mock(StringSelectMenu.Builder.class);
        StringSelectMenu prefixedMenu = mock(StringSelectMenu.class);

        when(menu.createCopy()).thenReturn(builder);
        when(builder.setCustomId(PREFIX)).thenReturn(builder);
        when(builder.build()).thenReturn(prefixedMenu);
        when(menu.getCustomId()).thenReturn(EMPTY_ID);

        // Act
        StringSelectMenu result = handler.withPrefixedId(menu);

        // Assert
        assertEquals(prefixedMenu, result);
        verify(builder).setCustomId(PREFIX);
    }
}
