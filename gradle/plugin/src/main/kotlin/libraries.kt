import org.gradle.api.Project
import studio.forface.easygradle.dsl.version

fun arrow(moduleName: String) = "io.arrow-kt:arrow-$moduleName" version ARROW_VERSION
fun assert4k() = forface("assert4k") version ASSERT4K_VERSION

fun coroutines(moduleName: String) =
    kotlinx("coroutines-$moduleName") version COROUTINES_VERSION

fun forface(moduleName: String) = "studio.forface:$moduleName"

fun kermit() = "co.touchlab:kermit" version KERMIT_VERSION
fun koin(moduleName: String? = null) = "org.koin:koin${moduleName.module()}" version KOIN_VERSION
fun kotlin(moduleName: String) = "org.jetbrains.kotlin:kotlin-$moduleName" version KOTLIN_VERSION
fun kotlinx(moduleName: String) = "org.jetbrains.kotlinx:kotlinx-$moduleName"

fun mockk(moduleName: String? = null) = "io.mockk:mockk${moduleName.module()}" version MOCKK_VERSION

fun proton(moduleName: String) = "me.proton.core:$moduleName"

fun serialization(moduleName: String? = null) =
    kotlinx("serialization${moduleName.module()}") version SERIALIZATION_VERSION

fun sqlDelight(moduleName: String) =
    "com.squareup.sqldelight:$moduleName" version SQLDELIGHT_VERSION
fun sqlDelightDriver(platform: String) = sqlDelight("$platform-driver")

object Android {
    fun accompanist(moduleName: String) =
        "dev.chrisbanes.accompanist:accompanist-$moduleName" version ACCOMPANIST_VERSION
    fun activity() = ktx("activity") version ACTIVITY_VERSION
    fun appCompat() = "androidx.appcompat:appcompat" version APP_COMPAT_VERSION

    fun compose(moduleName: String) =
        "androidx.compose.${moduleName.substringBefore("-")}:$moduleName" version COMPOSE_VERSION

    fun dataStore(moduleName: String? = null) =
        "androidx.datastore:datastore${moduleName.module()}" version DATASTORE_VERSION

    fun fluentNotifications() =
        "studio.forface.fluentnotifications:fluentnotifications" version FLUENT_NOTIFICATIONS_VERSION

    fun ktx() = ktx("core") version KTX_VERSION
    fun ktx(moduleName: String) = "androidx.$moduleName:$moduleName-ktx"

    fun ui(moduleName: String) = compose("ui-$moduleName")

    fun viewModel() = "androidx.lifecycle:lifecycle-viewmodel-ktx" version VIEWMODEL_VERSION
}

// Groups
fun Project.testDependencies(): Array<Any> = arrayOf(
    kotlin("test-junit"),
    kotlin("test-annotations-common"),
    kotlinx("coroutines-test") version COROUTINES_VERSION,
    assert4k(),
    koin("test"),
    mockk()
    // testUtils()
)

fun Project.androidTestDependencies(): Array<Any> = arrayOf(
    kotlin("test-junit"),
    kotlin("test-annotations-common"),
    kotlinx("coroutines-test") version COROUTINES_VERSION,
    koin("test"),
    mockk("android")
    // testUtils()
)
