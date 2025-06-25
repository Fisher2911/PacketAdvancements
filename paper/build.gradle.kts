import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.17"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("de.eldoria.plugin-yml.paper") version "0.7.1"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.fisherl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
}

dependencies {
    paperweight.paperDevBundle("1.21.6-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.21.6-R0.1-SNAPSHOT")
    compileOnly("com.github.retrooper:packetevents-api:2.8.0")
    implementation(project(":common"))
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
    dependsOn(tasks.reobfJar)
}

tasks {
    runServer {
        minecraftVersion("1.21.6")
    }
}

paper {
    name = "CustomAdvancements"
    version = "1.0"
    description = "CustomAdvancements Plugin"
    author = "Fisher"

    main = "com.fisherl.customadvancements.paper.CustomAdvancements"

    apiVersion = "1.21"

//    load = BukkitPluginDescription.PluginLoadOrder.STARTUP

    prefix = "CustomAdvancements"
    defaultPermission = BukkitPluginDescription.Permission.Default.OP

    serverDependencies{
        register("packetevents")
    }

}