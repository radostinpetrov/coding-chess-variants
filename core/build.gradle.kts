//apply(plugin = "kotlin")
//sourceCompatibility = JavaVersion.VERSION_1_8
//listOf(implementation(Java, implementationTestJava)*.options*.encoding = "UTF-8")

val kotlinVersion: String by project
val gdxVersion: String by project
val ktxVersion: String by project

project(":core") {
    dependencies {
        implementation(project(":engine"))
        implementation(project(":tutorial"))
        implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
        implementation("com.badlogicgames.gdx:gdx-box2d:$gdxVersion")
        implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
        implementation("org.java-websocket:Java-WebSocket:1.5.1")
        implementation("org.slf4j:slf4j-nop:1.7.30")
        implementation("org.json:json:20200518")
        implementation("org.apache.commons:commons-lang3:3.11")

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

kotlin.sourceSets["main"].kotlin.srcDirs("src")