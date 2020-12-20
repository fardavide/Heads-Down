package studio.forface.headsdown

import android.content.Context
import androidx.datastore.preferences.createDataStore
import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import studio.forface.headsdown.data.AndroidAppSource
import studio.forface.headsdown.data.AppRepository
import studio.forface.headsdown.data.AppRepositoryImpl
import studio.forface.headsdown.data.SettingsSource
import studio.forface.headsdown.viewmodel.AppViewModel

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

    viewModel { AppViewModel(logger = get(), repository = get()) }

    single<Logger> { LogcatLogger() }

} + dataModule
