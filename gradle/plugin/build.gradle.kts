plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.4.21"
    id("java-gradle-plugin")
}

group = "studio.forface.headsdown"
version = "1.0"

gradlePlugin {
    plugins {
        create("gradlePlugin") {
            id = "HeadsDown"
            implementationClass = "GradlePlugin"
        }
    }
}

repositories {
    google()
    jcenter()
}

dependencies {
    val easyGradle = "2.8" // Nov 25, 2020
    val agpVersion = "4.2.0-alpha11"

    implementation(gradleApi())
    implementation("studio.forface.easygradle:dsl:$easyGradle")
    compileOnly("com.android.tools.build:gradle:$agpVersion")
}
