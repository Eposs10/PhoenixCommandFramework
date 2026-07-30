package dev.eposs.pcf.example;

import dev.eposs.pcf.api.PCF;
import dev.eposs.pcf.api.handler.ButtonInteractionHandler;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ExampleButton implements ButtonInteractionHandler {

    @Override
    public String getIdPrefix() {
        return "";
    }

    @Override
    public void execute(PCF pcf, ButtonInteractionEvent event) throws Exception {

    }
}
