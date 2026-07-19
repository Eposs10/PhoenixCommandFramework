package dev.eposs.pcf.command;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandRegistryTest {

    private CommandHandler mockGlobalCommand;
    private CommandHandler mockGuildCommand;

    @BeforeEach
    void setUp() {
        mockGlobalCommand = createMockCommand("global-command");
        mockGuildCommand = createMockCommand("guild-command");
    }

    private CommandHandler createMockCommand(String name) {
        CommandHandler mock = mock(CommandHandler.class);
        CommandData commandData = mock(CommandData.class);
        when(commandData.getName()).thenReturn(name);
        when(mock.getCommandData()).thenReturn(commandData);
        when(mock.getTargetGuildIDs()).thenReturn(Set.of());
        return mock;
    }

    @Test
    void givenGlobalCommandHandler_whenRegisterSingleGlobalCommand_thenCommandIsRetrievable() {
        // Arrange // Act
        CommandRegistry.register(CommandRegistry.Type.GLOBAL, mockGlobalCommand);

        // Assert
        assertTrue(CommandRegistry.getCommand("global-command").isPresent());
        assertEquals(mockGlobalCommand, CommandRegistry.getCommand("global-command").get());
    }

    @Test
    void givenGuildCommandHandler_whenRegisterSingleGuildCommand_thenCommandIsRetrievable() {
        // Arrange // Act
        CommandRegistry.register(CommandRegistry.Type.GUILD, mockGuildCommand);

        // Assert
        assertTrue(CommandRegistry.getCommand("guild-command").isPresent());
        assertEquals(mockGuildCommand, CommandRegistry.getCommand("guild-command").get());
    }

    @Test
    void givenMultipleCommandHandlers_whenRegisterMultipleCommands_thenAllCommandsAreRetrievable() {
        // Arrange
        CommandHandler mockCommand1 = createMockCommand("command1");
        CommandHandler mockCommand2 = createMockCommand("command2");

        // Act
        CommandRegistry.register(CommandRegistry.Type.GLOBAL, mockCommand1, mockCommand2);

        // Assert
        assertTrue(CommandRegistry.getCommand("command1").isPresent());
        assertTrue(CommandRegistry.getCommand("command2").isPresent());
        assertEquals(mockCommand1, CommandRegistry.getCommand("command1").get());
        assertEquals(mockCommand2, CommandRegistry.getCommand("command2").get());
    }

    @Test
    void givenNonExistentCommandName_whenGetCommand_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = CommandRegistry.getCommand("non-existent");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenNullCommandName_whenGetCommand_thenReturnsEmptyOptional() {
        // Arrange // Act
        var result = CommandRegistry.getCommand(null);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void givenMultipleCommandHandlersOfSameType_whenRegisterMultipleCommands_thenAllCommandsAreRetrievable() {
        // Arrange
        CommandHandler mockCommand1 = createMockCommand("cmd1");
        CommandHandler mockCommand2 = createMockCommand("cmd2");
        CommandHandler mockCommand3 = createMockCommand("cmd3");

        // Act
        CommandRegistry.register(CommandRegistry.Type.GLOBAL, mockCommand1, mockCommand2, mockCommand3);

        // Assert
        assertEquals(mockCommand1, CommandRegistry.getCommand("cmd1").get());
        assertEquals(mockCommand2, CommandRegistry.getCommand("cmd2").get());
        assertEquals(mockCommand3, CommandRegistry.getCommand("cmd3").get());
    }
}