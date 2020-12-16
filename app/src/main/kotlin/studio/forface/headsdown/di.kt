package studio.forface.headsdown

import android.content.Context
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import org.koin.core.module.Module
import org.koin.dsl.module
import studio.forface.headsdown.data.AndroidAppSource
import studio.forface.headsdown.data.AppRepository
import studio.forface.headsdown.data.AppRepositoryImpl
import studio.forface.headsdown.data.SettingsSource
import studio.forface.headsdown.model.AppSettings

val dataModule = module {

    factory<AppRepository> { AppRepositoryImpl(androidAppSource = get(), settingsSource = get()) }

    // Apps
    single { AndroidAppSource(packageManager = get()) }
    single { get<Context>().packageManager }

    // Settings
    single { SettingsSource(dataStore = get()) }
    single { get<Context>().createDataStore("settings") }
}

val appModule = module {

} + dataModule
