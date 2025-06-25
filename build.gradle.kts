plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.fisherl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.assemble {
    dependsOn(tasks.shadowJar)
}

