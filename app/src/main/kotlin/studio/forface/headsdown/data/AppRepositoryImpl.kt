package studio.forface.headsdown.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import studio.forface.headsdown.model.App
import studio.forface.headsdown.model.AppSettings
import studio.forface.headsdown.model.AppWithSettings
import studio.forface.headsdown.model.PackageName

class AppRepositoryImpl(
    private val androidAppSource: AndroidAppSource,
    private val settingsSource: SettingsSource
) : AppRepository {

    override fun allApps(): Flow<List<AppWithSettings>> =
        allApps(includeSystemApp = true)

    override fun allNonSystemApps(): Flow<List<AppWithSettings>> =
        allApps(includeSystemApp = false)

    override fun allBlockingHeadsUpApps(): Flow<List<AppWithSettings>> =
        allApps().map { list ->
            list.filter { appWithSettings -> appWithSettings.settings.shouldBlockHeadsUp }
        }

    private fun allApps(includeSystemApp: Boolean): Flow<List<AppWithSettings>> =
        flowOf(androidAppSource.allApps(includeSystemApp))
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
