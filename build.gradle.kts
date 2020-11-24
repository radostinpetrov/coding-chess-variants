import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}
group = "me.lukyxu"
version = "1.0-SNAPSHOT"

val ktxVersion = "1.9.10-b2"
val gdxVersion = "1.9.11"
val kotlinVersion = "1.4.10"

repositories {
    mavenCentral()
}

allprojects {
    apply(plugin = "kotlin")
    version = "1.0"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/releases/") }
        jcenter()
    }
}

project(":engine") {
    apply(plugin = "kotlin")
    repositories {
        mavenCentral()
    }

    dependencies {

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")

        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
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

    tasks {
        test {
            useJUnitPlatform()
        }
    }

//    tasks.withType<Test> {
//        useJUnitPlatform()
//    }
}

project(":desktop") {
    dependencies {
        // implementation(":core")
        implementation(project(":core"))
        implementation("com.badlogicgames.gdx:gdx-backend-lwjgl:$gdxVersion")
        implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
        implementation("com.badlogicgames.gdx:gdx-box2d-platform:$gdxVersion:natives-desktop")
        implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    }
}

project(":core") {
    dependencies {
        implementation(project(":engine"))
        implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
        implementation("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
        implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

        implementation("org.java-websocket:Java-WebSocket:1.5.1")
        implementation("org.slf4j:slf4j-nop:1.7.30")
        implementation("org.json:json:20200518")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.4.2")

        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
        testImplementation("io.mockk:mockk:1.9.3")
        api("com.badlogicgames.gdx:gdx:$gdxVersion")
        api("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
        api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
        api("io.github.libktx:ktx-app:$ktxVersion")
        api("io.github.libktx:ktx-collections:$ktxVersion")
        api("io.github.libktx:ktx-graphics:$ktxVersion")
        api("io.github.libktx:ktx-assets:$ktxVersion")
    }
}
