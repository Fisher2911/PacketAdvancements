plugins {
    id("java")
}

group = "com.fisherl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.codemc.io/repository/maven-releases/") }
    maven { url = uri("https://repo.codemc.io/repository/maven-snapshots/") }
}

dependencies {
    compileOnly("com.github.retrooper:packetevents-api:2.8.0")
    implementation("net.kyori:adventure-api:4.22.0")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
