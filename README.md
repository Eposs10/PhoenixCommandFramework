# Phoenix Command Framework

A small Java command utility for JDA bots. PCF provides registries and event routing for slash commands, context commands, buttons, modals, string selects, and entity selects.

## Requirements

- Java 21+
- JDA 6
- Maven

## Installation

```xml
<dependency>
    <groupId>dev.eposs.pcf</groupId>
    <artifactId>PhoenixCommandFramework</artifactId>
    <version>0.4.3</version>
</dependency>
```

## Basic Usage

```java
PhoenixCommandFramework.init("YOUR_DISCORD_USER_ID", Set.of());

CommandRegistry.register(CommandRegistry.Type.GLOBAL, new MySlashCommand());
ButtonRegistry.register(new MyButtonHandler());

JDABuilder.createDefault(token)
        .addEventListeners(new PCFEventListener((exception, event) -> {
            exception.printStackTrace();
        }))
        .build();
```

Commands are published when JDA becomes ready. Guild commands are updated when guilds become ready or when the bot joins a guild.
