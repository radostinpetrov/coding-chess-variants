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

buildscript {
    val kotlinVersion: String by project
    repositories {
        mavenLocal()
        mavenCentral()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        jcenter()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
//        classpath("com.mobidevelop.robovm:robovm-gradle-plugin:2.3.12")
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath(kotlin("gradle-plugin", kotlinVersion))
    }
}

allprojects {
//    if (name != "android") {
    apply(plugin = "kotlin")
//    }
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
