import studio.forface.easygradle.dsl.Version
import studio.forface.easygradle.dsl.archivesBaseName

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("plugin.serialization")
    id("publish")
}

version = Version(0, 1, 0)
archivesBaseName =
    "headsdown_${(version as Version).versionName.replace(".", "_")}"

dependencies {
    implementation(

        // Kotlin
        coroutines("android"),
        serialization("json"),

        // Arrow
        arrow("core"),

        // Koin
        koin("android"),
        koin("androidx-viewmodel"),

        // Log
        kermit(),

        // Android
        Android.activity(),
        Android.appCompat(),
        Android.dataStore("preferences"),
        Android.fluentNotifications(),
        Android.ktx(),
        Android.viewModel(),

        // Compose
        Android.accompanist("coil"),
        Android.compose("animation"),
        Android.compose("foundation"),
        Android.compose("foundation-layout"),
        Android.compose("material"),
        Android.compose("material-icons-extended"),
        Android.compose("runtime"),
        Android.compose("ui"),
        Android.compose("ui-tooling")
    )

    testImplementation(
        *testDependencies(),

        // Compose
        Android.ui("test-junit4")
    )

    androidTestImplementation(
        *androidTestDependencies(),

        // Compose
        Android.ui("test-junit4")
    )
}
