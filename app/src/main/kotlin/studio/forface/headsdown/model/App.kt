package studio.forface.headsdown.model

import android.graphics.drawable.Drawable
import kotlinx.serialization.Serializable

data class App(
    val appName: String,
    val packageName: PackageName,
    val icon: Drawable,
    val isSystemApp: Boolean
)

data class PackageName(val value: String)

data class AppWithSettings(
    val app: App,
    val settings: AppSettings
)

infix fun App.with(appSettings: AppSettings) =
    AppWithSettings(this, appSettings)

@Serializable
data class AppSettings(
    val shouldBlockHeadsUp: Boolean
)
