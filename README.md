[![build](https://github.com/Eposs10/PhoenixCommandFramework/actions/workflows/ci.yml/badge.svg)](https://github.com/Eposs10/PhoenixCommandFramework/actions/workflows/ci.yml)
[![maven-central](https://img.shields.io/maven-metadata/v.svg?metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fdev%2Feposs%2FPCF%2Fmaven-metadata.xml&label=maven-central&filter=%21%2A-preview%2A&logo=apachemaven&color=blue)](https://github.com/Eposs10/PhoenixCommandFramework/releases)
[![License](https://img.shields.io/badge/License-MIT-white.svg)](https://license.eposs.dev/MIT)

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
public static void main(String[] args) {
    new PCF("YOUR_DISCORD_USER_ID", Set.of());

    CommandRegistry.register(CommandRegistry.Type.GLOBAL, new MySlashCommand());
    ButtonRegistry.register(new MyButtonHandler());

    JDABuilder.createDefault(token)
            .addEventListeners(new PCFEventListener(pcf)) // Optional: Add custom implementation of IExceptionHandler
            .build();
}
```
