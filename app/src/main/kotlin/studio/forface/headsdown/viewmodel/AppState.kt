package studio.forface.headsdown.viewmodel

import studio.forface.headsdown.model.AppWithSettings

data class AppState(

    val permissionsState: GrantedPermissionsState,
    val generalHeadsUpBlockEnabled: Boolean,
    val appsWithSettingsState: AppsWithSettingsState
)

val InitialState = AppState(
    permissionsState = GrantedPermissionsState.None,
    generalHeadsUpBlockEnabled = true,
    AppsWithSettingsState.Loading
)

sealed class GrantedPermissionsState {
    object None : GrantedPermissionsState()
    object OnlyNotificationAccess : GrantedPermissionsState()
    object OnlyUsageStatsAccess : GrantedPermissionsState()
    object All : GrantedPermissionsState()

    companion object {
        operator fun invoke(
            hasNotificationAccess: Boolean,
            hasUsageStatsAccess: Boolean
        ): GrantedPermissionsState = when {
            hasNotificationAccess && hasUsageStatsAccess -> All
            hasNotificationAccess -> OnlyNotificationAccess
            hasUsageStatsAccess -> OnlyUsageStatsAccess
            else -> None
        }
    }
}

sealed class AppsWithSettingsState {

    object Loading: AppsWithSettingsState()
    data class Data(val appsWithSettings: List<AppWithSettings>): AppsWithSettingsState()
}
