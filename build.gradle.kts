import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}
group = "me.lukyxu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    // testImplementation(kotlin("test-junit5"))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("io.mockk:mockk:1.9.3")
}
tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<ShadowJar>() {
    manifest {
        attributes["Main-Class"] = "ChessKt"
    }
}

tasks.test() {
    useJUnitPlatform()
}
