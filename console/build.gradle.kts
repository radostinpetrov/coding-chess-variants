import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project

plugins {
    application
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

application {
    mainClassName = "ChessKt"
}

tasks.withType<ShadowJar>() {
    archiveBaseName.set("consoleclient")
    archiveClassifier.set("")
    archiveVersion.set("")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "ChessKt"
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":engine"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/main")