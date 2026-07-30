package dev.eposs.pcf.command;

import dev.eposs.pcf.internal.CommandInteractionHandler;
import dev.eposs.pcf.api.registry.CommandInteractionHandlerRegistry;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.mockito.Mockito.*;

class CommandInteractionHandlerRegistryTest {

    private CommandInteractionHandler mockGlobalCommand;
    private CommandInteractionHandler mockGuildCommand;

    @BeforeEach
    void setUp() {
        mockGlobalCommand = createMockCommand("global-command");
        mockGuildCommand = createMockCommand("guild-command");
    }

    private CommandInteractionHandler createMockCommand(String name) {
        CommandInteractionHandler mock = mock(CommandInteractionHandler.class);
        CommandData commandData = mock(CommandData.class);
        when(commandData.getName()).thenReturn(name);
        when(mock.getCommandData()).thenReturn(commandData);
        when(mock.getTargetGuildIDs()).thenReturn(Set.of());
        return mock;
    }

    @Test
    void givenGlobalCommandHandler_whenRegisterSingleGlobalCommand_thenCommandIsRetrievable() {
        // Arrange // Act
        CommandInteractionHandlerRegistry.register(CommandInteractionHandlerRegistry.Type.GLOBAL, mockGlobalCommand);

        // Assert
        assertTrue(CommandInteractionHandlerRegistry.getCommand("global-command").isPresent());
        assertEquals(mockGlobalCommand, CommandInteractionHandlerRegistry.getCommand("global-command").get());
    }

    @Test
    void givenGuildCommandHandler_whenRegisterSingleGuildCommand_thenCommandIsRetrievable() {
        // Arrange // Act
        CommandInteractionHandlerRegistry.register(CommandInteractionHandlerRegistry.Type.GUILD, mockGuildCommand);

        // Assert
        assertTrue(CommandInteractionHandlerRegistry.getCommand("guild-command").isPresent());
        assertEquals(mockGuildCommand, CommandInteractionHandlerRegistry.getCommand("guild-command").get());
    }

    @Test
    void givenMultipleCommandHandlers_whenRegisterMultipleCommands_thenAllCommandsAreRetrievable() {
        // Arrange
        CommandInteractionHandler mockCommand1 = createMockCommand("command1");
        CommandInteractionHandler mockCommand2 = createMockCommand("command2");

        // Act
        CommandInteractionHandlerRegistry.register(CommandInteractionHandlerRegistry.Type.GLOBAL, mockCommand1, mockCommand2);

        // Assert
        assertTrue(CommandInteractionHandlerRegistry.getCommand("command1").isPresent());
        assertTrue(CommandInteractionHandlerRegistry.getCommand("command2").isPresent());
        assertEquals(mockCommand1, CommandInteractionHandlerRegistry.getCommand("command1").get());
        assertEquals(mockCommand2, CommandInteractionHandlerRegistry.getCommand("command2").get());
    }

    @Test
    void givenNonExistentCommandName_whenGetCommand_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = CommandInteractionHandlerRegistry.getCommand("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCommandName_whenGetCommand_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = CommandInteractionHandlerRegistry.getCommand(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenMultipleCommandHandlersOfSameType_whenRegisterMultipleCommands_thenAllCommandsAreRetrievable() {
        // Arrange
        CommandInteractionHandler mockCommand1 = createMockCommand("cmd1");
        CommandInteractionHandler mockCommand2 = createMockCommand("cmd2");
        CommandInteractionHandler mockCommand3 = createMockCommand("cmd3");

        // Act
        CommandInteractionHandlerRegistry.register(CommandInteractionHandlerRegistry.Type.GLOBAL, mockCommand1, mockCommand2, mockCommand3);

        // Assert
        assertEquals(mockCommand1, CommandInteractionHandlerRegistry.getCommand("cmd1").get());
        assertEquals(mockCommand2, CommandInteractionHandlerRegistry.getCommand("cmd2").get());
        assertEquals(mockCommand3, CommandInteractionHandlerRegistry.getCommand("cmd3").get());
    }
}