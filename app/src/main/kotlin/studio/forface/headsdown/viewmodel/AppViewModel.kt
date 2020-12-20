package studio.forface.headsdown.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import studio.forface.headsdown.model.App
import studio.forface.headsdown.notifications.NotificationAccessVerifier
import studio.forface.headsdown.usecase.AddToShouldBlockHeadsUp
import studio.forface.headsdown.usecase.GetAllNonSystemApps
import studio.forface.headsdown.usecase.RemoveFromShouldBlockHeadsUp
import studio.forface.headsdown.utils.v

class AppViewModel(
    private val logger: Logger,
    getAllNonSystemApps: GetAllNonSystemApps,
    private val addToShouldBlockHeadsUp: AddToShouldBlockHeadsUp,
    private val removeFromShouldBlockHeadsUp: RemoveFromShouldBlockHeadsUp,
    notificationAccessVerifier: NotificationAccessVerifier
) : ViewModel() {

    val state: StateFlow<AppState> =
        combine(
            getAllNonSystemApps(),
            notificationAccessVerifier.hasNotificationAccess
        ) { allApps, hasNotificationAccess ->
            AppState(
                hasNotificationAccess = hasNotificationAccess,
                generalHeadsUpBlockEnabled = true,
                AppsWithSettingsState.Data(allApps)
            )
        }
        .onStart { logger v "start loading data" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, InitialState)

    fun addToBlockHeadsUp(app: App) {
        logger v "blocking heads up for ${app.appName}"
        viewModelScope.launch {
            addToShouldBlockHeadsUp(app)
        }
    }

    fun removeFromBlockHeadsUp(app: App) {
        logger v "un-blocking heads up for ${app.appName}"
        viewModelScope.launch {
            removeFromShouldBlockHeadsUp(app)
        }
    }
}
