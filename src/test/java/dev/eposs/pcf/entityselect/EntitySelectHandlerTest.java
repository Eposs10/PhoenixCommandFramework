package dev.eposs.pcf.entityselect;

import net.dv8tion.jda.api.components.selections.EntitySelectMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dev.eposs.pcf.TestUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EntitySelectHandlerTest {

    private EntitySelectHandler handler;

    @BeforeEach
    void setUp() {
        EntitySelectHandler mock = mock(EntitySelectHandler.class);
        when(mock.getIdPrefix()).thenReturn(PREFIX);
        when(mock.withPrefixedId(any())).thenCallRealMethod();
        handler = mock;
    }

    @Test
    void givenMenuWithSuffix_whenWithPrefixedId_thenReturnsPrefixedMenu() {
        // Arrange
        EntitySelectMenu menu = mock(EntitySelectMenu.class);
        EntitySelectMenu.Builder builder = mock(EntitySelectMenu.Builder.class);
        EntitySelectMenu prefixedMenu = mock(EntitySelectMenu.class);

        when(menu.createCopy()).thenReturn(builder);
        when(builder.setCustomId(ID)).thenReturn(builder);
        when(builder.build()).thenReturn(prefixedMenu);
        when(menu.getCustomId()).thenReturn(SUFFIX);
        when(prefixedMenu.getCustomId()).thenReturn(ID);

        // Act
        EntitySelectMenu result = handler.withPrefixedId(menu);

        // Assert
        assertEquals(prefixedMenu, result);
        verify(builder).setCustomId(ID);
    }

    @Test
    void givenMenuWithEmptySuffix_whenWithPrefixedId_thenReturnsPrefixedMenu() {
        // Arrange
        EntitySelectMenu menu = mock(EntitySelectMenu.class);
        EntitySelectMenu.Builder builder = mock(EntitySelectMenu.Builder.class);
        EntitySelectMenu prefixedMenu = mock(EntitySelectMenu.class);

        when(menu.createCopy()).thenReturn(builder);
        when(builder.setCustomId(PREFIX)).thenReturn(builder);
        when(builder.build()).thenReturn(prefixedMenu);
        when(menu.getCustomId()).thenReturn(EMPTY_ID);

        // Act
        EntitySelectMenu result = handler.withPrefixedId(menu);

        // Assert
        assertEquals(prefixedMenu, result);
        verify(builder).setCustomId(PREFIX);
    }
}
