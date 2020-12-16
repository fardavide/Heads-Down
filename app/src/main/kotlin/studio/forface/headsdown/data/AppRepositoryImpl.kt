package studio.forface.headsdown.data

import kotlinx.coroutines.flow.*
import studio.forface.headsdown.model.App
import studio.forface.headsdown.model.AppSettings
import studio.forface.headsdown.model.AppWithSettings
import studio.forface.headsdown.model.PackageName

class AppRepositoryImpl(
    private val androidAppSource: AndroidAppSource,
    private val settingsSource: SettingsSource
) : AppRepository {

    override fun allApps(): Flow<List<AppWithSettings>> =
        flowOf(androidAppSource.allApps())
            .flatMapLatest { apps ->
                settingsSource.appsWithBlockedHeadsUp().map {
                    apps.withSettings(it)
                }
            }

    private fun List<App>.withSettings(appsWithBlockedHeadsUp: Set<PackageName>) =
        map { app ->
            val shouldBlockHeadsUp = app.packageName in appsWithBlockedHeadsUp
            AppWithSettings(app, AppSettings(shouldBlockHeadsUp))
        }

    override suspend fun updateAppSettings(app: App, settings: AppSettings) {
        if (settings.shouldBlockHeadsUp)
            settingsSource.addToBlockHeadsUp(app.packageName)
        else
            settingsSource.removeFromBlockHeadsUp(app.packageName)
    }
}
