package studio.forface.headsdown.viewmodel

import studio.forface.headsdown.model.AppWithSettings

data class AppState(

    val hasNotificationAccess: Boolean,
    val generalHeadsUpBlockEnabled: Boolean,
    val appsWithSettingsState: AppsWithSettingsState
)

val InitialState = AppState(
    hasNotificationAccess = false,
    generalHeadsUpBlockEnabled = true,
    AppsWithSettingsState.Loading
)

sealed class AppsWithSettingsState {

    object Loading: AppsWithSettingsState()
    data class Data(val appsWithSettings: List<AppWithSettings>): AppsWithSettingsState()
}
