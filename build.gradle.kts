import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}

val ktxVersion: String by project
val gdxVersion: String by project
val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "kotlin")
    group = "me.lukyxu"
    version = "1.0-SNAPSHOT"

    tasks.withType<KotlinCompile>() {
        kotlinOptions.jvmTarget = "1.8"
    }

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
        jcenter()
    }
}
