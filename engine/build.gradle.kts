import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("jacoco")
    id("org.jetbrains.dokka") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

tasks.withType<ShadowJar>() {
    archiveBaseName.set("chessengine")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")
    testImplementation("io.mockk:mockk:1.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/main")
kotlin.sourceSets["test"].kotlin.srcDirs("src/test")
