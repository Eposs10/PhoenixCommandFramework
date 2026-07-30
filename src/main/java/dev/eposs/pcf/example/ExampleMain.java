package dev.eposs.pcf.example;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.api.PCFEventListener;
import dev.eposs.pcf.api.registry.CommandInteractionHandlerRegistry;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import java.util.List;

public class ExampleMain {
    public static void main(String[] args) {
        PCF pcf = new PCF("ownerID");
        pcf.getCommandInteractionHandlerRegistry().registerAll(
                CommandInteractionHandlerRegistry.Type.GLOBAL,
                List.of(
                        new ExampleCommand()
                )
        );
        pcf.getButtonInteractionHandlerRegistry().registerAll(List.of(
                new ExampleButton()
        ));

        JDA jda = JDABuilder.createDefault("token")
                .addEventListeners(new PCFEventListener(pcf))
                .build();
    }
}
