package studio.forface.headsdown.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import studio.forface.headsdown.data.AppRepository
import studio.forface.headsdown.model.App
import studio.forface.headsdown.model.AppSettings
import studio.forface.headsdown.utils.v

class AppViewModel(
    private val logger: Logger,
    private val repository: AppRepository
) : ViewModel() {

    val state: StateFlow<AppState> = repository.allApps()
        .map { AppState(generalHeadsUpBlockEnabled = true, AppsWithSettingsState.Data(it)) }
        .onStart { logger v "start loading data" }
        .stateIn(viewModelScope, SharingStarted.Eagerly, InitialState)

    fun addToBlockHeadsUp(app: App) {
        logger v "blocking heads up for ${app.appName}"
        viewModelScope.launch {
            repository.updateAppSettings(app, AppSettings(shouldBlockHeadsUp = true))
        }
    }

    fun removeFromBlockHeadsUp(app: App) {
        logger v "un-blocking heads up for ${app.appName}"
        viewModelScope.launch {
            repository.updateAppSettings(app, AppSettings(shouldBlockHeadsUp = false))
        }
    }
}
