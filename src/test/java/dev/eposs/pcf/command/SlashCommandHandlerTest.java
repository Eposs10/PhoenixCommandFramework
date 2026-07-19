package dev.eposs.pcf.command;

import dev.eposs.pcf.PCF;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SlashCommandHandlerTest {

    private PCF pcf;
    private SlashCommandHandler handler;
    
    @BeforeEach
    void setUp() {
        pcf = new PCF("123456789", Set.of());
        handler = new AbstractSlashCommand() {
            @Override
            public CommandData getCommandData() {
                return null;
            }

            @Override
            public Set<String> getTargetGuildIDs() {
                return Set.of();
            }
        };
    }

    private SubCommandHandler createMockSubCommand(String name) {
        SubCommandHandler mock = mock(SubCommandHandler.class);
        SubcommandData data = mock(SubcommandData.class);
        when(data.getName()).thenReturn(name);
        when(mock.getSubCommandData()).thenReturn(data);
        return mock;
    }

    private SubCommandGroupHandler createMockSubCommandGroup(String name) {
        SubCommandGroupHandler mock = mock(SubCommandGroupHandler.class);
        SubcommandGroupData data = mock(SubcommandGroupData.class);
        when(data.getName()).thenReturn(name);
        when(mock.getSubCommandGroupData()).thenReturn(data);
        when(mock.getSubCommands()).thenReturn(new java.util.HashMap<>());
        return mock;
    }

    @Test
    void givenSubCommand_whenRegisterSingle_thenSubCommandIsRetrievable() {
        // Arrange
        SubCommandHandler subCommand = createMockSubCommand("test-subcommand");

        // Act
        handler.registerSubCommand(subCommand);

        // Assert
        Map<String, SubCommandHandler> subCommands = handler.getSubCommands();
        assertEquals(1, subCommands.size());
        assertTrue(subCommands.containsKey("test-subcommand"));
        assertEquals(subCommand, subCommands.get("test-subcommand"));
    }

    @Test
    void givenMultipleSubCommands_whenRegisterMultiple_thenAllSubCommandsAreRetrievable() {
        // Arrange
        SubCommandHandler subCommand1 = createMockSubCommand("subcommand1");
        SubCommandHandler subCommand2 = createMockSubCommand("subcommand2");
        SubCommandHandler subCommand3 = createMockSubCommand("subcommand3");

        // Act
        handler.registerSubCommands(subCommand1, subCommand2, subCommand3);

        // Assert
        Map<String, SubCommandHandler> subCommands = handler.getSubCommands();
        assertEquals(3, subCommands.size());
        assertEquals(subCommand1, subCommands.get("subcommand1"));
        assertEquals(subCommand2, subCommands.get("subcommand2"));
        assertEquals(subCommand3, subCommands.get("subcommand3"));
    }

    @Test
    void givenSubCommandGroup_whenRegisterSingle_thenGroupIsRetrievable() {
        // Arrange
        SubCommandGroupHandler group = createMockSubCommandGroup("test-group");

        // Act
        handler.registerSubCommandGroup(group);

        // Assert
        Map<String, SubCommandGroupHandler> groups = handler.getSubCommandGroups();
        assertEquals(1, groups.size());
        assertTrue(groups.containsKey("test-group"));
        assertEquals(group, groups.get("test-group"));
    }

    @Test
    void givenMultipleSubCommandGroups_whenRegisterMultiple_thenAllGroupsAreRetrievable() {
        // Arrange
        SubCommandGroupHandler group1 = createMockSubCommandGroup("group1");
        SubCommandGroupHandler group2 = createMockSubCommandGroup("group2");

        // Act
        handler.registerSubCommandGroups(group1, group2);

        // Assert
        Map<String, SubCommandGroupHandler> groups = handler.getSubCommandGroups();
        assertEquals(2, groups.size());
        assertEquals(group1, groups.get("group1"));
        assertEquals(group2, groups.get("group2"));
    }

    @Test
    void givenRegisteredSubCommands_whenGetSubCommandData_thenReturnsCorrectDataList() {
        // Arrange
        SubCommandHandler subCommand1 = createMockSubCommand("subcommand1");
        SubCommandHandler subCommand2 = createMockSubCommand("subcommand2");

        // Act
        handler.registerSubCommands(subCommand1, subCommand2);

        // Assert
        List<SubcommandData> dataList = handler.subCommandData();
        assertEquals(2, dataList.size());
    }

    @Test
    void givenRegisteredSubCommandGroups_whenGetSubCommandGroupData_thenReturnsCorrectDataList() {
        // Arrange
        SubCommandGroupHandler group1 = createMockSubCommandGroup("group1");
        SubCommandGroupHandler group2 = createMockSubCommandGroup("group2");

        // Act
        handler.registerSubCommandGroups(group1, group2);

        // Assert
        List<SubcommandGroupData> dataList = handler.subCommandGroupData();
        assertEquals(2, dataList.size());
    }

    @Test
    void givenEventWithEphemeralOptionFalse_whenIsEphemeral_thenReturnsFalse() {
        // Arrange
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        OptionMapping ephemeralOption = mock(OptionMapping.class);

        when(event.getOption("ephemeral")).thenReturn(ephemeralOption);
        when(ephemeralOption.getAsBoolean()).thenReturn(false);

        // Act
        boolean isEphemeral = handler.isEphemeral(event);

        // Assert
        assertFalse(isEphemeral);
    }

    @Test
    void givenEventWithoutEphemeralOption_whenIsEphemeral_thenReturnsTrue() {
        // Arrange
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);

        when(event.getOption("ephemeral")).thenReturn(null);

        // Act
        boolean isEphemeral = handler.isEphemeral(event);

        // Assert
        assertTrue(isEphemeral);
    }

    @Test
    void givenEventWithEphemeralOptionTrue_whenIsEphemeral_thenReturnsTrue() {
        // Arrange
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        OptionMapping ephemeralOption = mock(OptionMapping.class);

        when(event.getOption("ephemeral")).thenReturn(ephemeralOption);
        when(ephemeralOption.getAsBoolean()).thenReturn(true);

        // Act
        boolean isEphemeral = handler.isEphemeral(event);

        // Assert
        assertTrue(isEphemeral);
    }

    @Test
    void givenSubCommandInGroup_whenExecuteSubCommand_thenDoesNotThrow() {
        // Arrange
        SubCommandGroupHandler group = createMockSubCommandGroup("test-group");
        SubCommandHandler subCommand = createMockSubCommand("test-subcommand");

        group.registerSubCommand(subCommand);
        handler.registerSubCommandGroup(group);

        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        when(event.getSubcommandName()).thenReturn("test-subcommand");
        when(event.getSubcommandGroup()).thenReturn("test-group");

        // Act // Assert
        assertDoesNotThrow(() -> handler.executeSubCommand(pcf, event));
    }

    @Test
    void givenDirectSubCommand_whenExecuteSubCommand_thenDoesNotThrow() {
        // Arrange
        SubCommandHandler subCommand = createMockSubCommand("test-subcommand");

        handler.registerSubCommand(subCommand);

        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);
        when(event.getSubcommandName()).thenReturn("test-subcommand");
        when(event.getSubcommandGroup()).thenReturn(null);

        // Act // Assert
        assertDoesNotThrow(() -> handler.executeSubCommand(pcf, event));
    }

    @Test
    void givenNullSubcommandName_whenExecuteSubCommand_thenDoesNotThrow() {
        // Arrange
        SlashCommandInteractionEvent event = mock(SlashCommandInteractionEvent.class);

        when(event.getSubcommandName()).thenReturn(null);

        // Act // Assert
        // Should not throw, just return early
        assertDoesNotThrow(() -> handler.executeSubCommand(pcf, event));
    }
}