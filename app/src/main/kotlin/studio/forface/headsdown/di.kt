package studio.forface.headsdown

import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import androidx.datastore.preferences.createDataStore
import co.touchlab.kermit.LogcatLogger
import co.touchlab.kermit.Logger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import studio.forface.headsdown.data.AndroidAppSource
import studio.forface.headsdown.data.AppRepository
import studio.forface.headsdown.data.AppRepositoryImpl
import studio.forface.headsdown.data.SettingsSource
import studio.forface.headsdown.notifications.NotificationAccessVerifier
import studio.forface.headsdown.notifications.NotificationListener
import studio.forface.headsdown.viewmodel.AppViewModel

val dataModule = module {

    factory<AppRepository> { AppRepositoryImpl(androidAppSource = get(), settingsSource = get()) }

    // Apps
    single { AndroidAppSource(packageManager = get()) }
    single { get<Context>().packageManager }

    // Notifications
    single {
        val context: Context = get()
        NotificationAccessVerifier(
            notificationListenerComponentName = ComponentName(
                context,
                NotificationListener::class.java
            ),
            getStringFromSecureSettings = { Settings.Secure.getString(context.contentResolver, it) }
        )
    }

    // Settings
    single { SettingsSource(dataStore = get()) }
    single { get<Context>().createDataStore("settings") }
}

val appModule = module {

    viewModel {
        AppViewModel(logger = get(), repository = get(), notificationAccessVerifier = get())
    }

    single<Logger> { LogcatLogger() }

} + dataModule
