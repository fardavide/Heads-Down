include("plugin")
include("publishPlugin")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://plugins.gradle.org/m2/")
    }
}
