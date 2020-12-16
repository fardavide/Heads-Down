import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import studio.forface.easygradle.dsl.*
import studio.forface.easygradle.dsl.android.*

plugins {
    val kotlinVersion = "1.4.21" // Nov 19, 2020
    val sqlDelightVersion = "1.4.4" // Oct 08, 2020
    val easyGradleVersion = "2.8" // Nov 26, 2020

    id("HeadsDown")
    kotlin("jvm") version kotlinVersion apply false
    kotlin("plugin.serialization") version kotlinVersion apply false
    id("com.squareup.sqldelight") version sqlDelightVersion apply false
    id("studio.forface.easygradle") version easyGradleVersion apply false
    id("studio.forface.easygradle-android") version easyGradleVersion apply false
}

buildscript {

    val agpVersion = "7.0.0-alpha03" // Dec 03, 2020
    val koinVersion = "2.2.1" // Nov 16, 2020

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$agpVersion")
        classpath("org.koin:koin-gradle-plugin:$koinVersion")

        // Work around for java.lang.ClassNotFoundException: org.apache.batik.w3c.dom.ElementTraversal
        // classpath("org.apache.xmlgraphics:batik-ext:1.13")
    }
}

subprojects {

    apply(plugin = "koin")

    afterEvaluate {

        // Kotlin options
        tasks.withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs + listOf(
                    "-Xallow-jvm-ir-dependencies",
                    "-Xskip-prerelease-check",
                    "-Xopt-in=kotlin.time.ExperimentalTime",
                    "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-Xinline-classes"
                )
            }
        }

        // Easy Gradle
        val isAndroid = plugins.hasPlugin("com.android.library") ||
            plugins.hasPlugin("com.android.application")
        
        if (isAndroid) apply<EasyGradleAndroidPlugin>()
        else apply<EasyGradlePlugin>()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
