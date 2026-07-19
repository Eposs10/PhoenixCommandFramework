# Phoenix Command Framework

A small Discord bot command utility for JDA bots. PCF provides registries and event routing for slash commands, context commands, buttons, modals, string selects, and entity selects.

## Requirements

- Java 21+
- JDA 6
- Maven / Gradle

## Installation

This library is available on maven central. The latest version is always shown in the [GitHub Release](https://github.com/Eposs10/PhoenixCommandFramework/releases).

### Maven:

```xml

<dependency>
    <groupId>dev.eposs</groupId>
    <artifactId>PCF</artifactId>
    <version>$version</version> <!-- replace $version with the latest version -->
</dependency>
```

### Gradle:

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.eposs:PCF:$version") // replace $version with the latest version
}
```

## Basic Usage

Commands are published when JDA becomes ready. Guild commands are updated when guilds become ready or when the bot joins a guild.

```java
new PCF("YOUR_DISCORD_USER_ID", Set.of());

CommandRegistry.register(CommandRegistry.Type.GLOBAL, new MySlashCommand());
ButtonRegistry.register(new MyButtonHandler());

JDABuilder.createDefault(token)
        .addEventListeners(new PCFEventListener(pcf, new PCFDefaultExceptionHandler())
        .build();
```
