val kotlinVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
//    implementation(kotlin("stdlib"))
    implementation(project(":engine"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
}
