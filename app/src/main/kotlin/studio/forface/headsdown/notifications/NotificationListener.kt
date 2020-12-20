package studio.forface.headsdown.notifications

import android.app.ActivityManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.core.content.getSystemService
import co.touchlab.kermit.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import studio.forface.fluentnotifications.builder.channel
import studio.forface.fluentnotifications.createNotification
import studio.forface.fluentnotifications.enum.NotificationImportance
import studio.forface.headsdown.R
import studio.forface.headsdown.ui.Strings
import studio.forface.headsdown.usecase.GetAllBlockingHeadsUpApps
import studio.forface.headsdown.utils.i
import studio.forface.headsdown.viewmodel.AppsWithSettingsState
import studio.forface.headsdown.viewmodel.AppsWithSettingsState.Data
import studio.forface.headsdown.viewmodel.AppsWithSettingsState.Loading


class NotificationListener : NotificationListenerService() {

    private val scope = CoroutineScope(Job())

    private val notificationAccessVerifier: NotificationAccessVerifier by inject()
    private val logger: Logger by inject()

    private val notificationManager by lazy {
        requireNotNull(applicationContext.getSystemService<NotificationManager>()) {
            "Cannot get NotificationManager service"
        }
    }
    private val activityManager by lazy {
        requireNotNull(applicationContext.getSystemService<ActivityManager>()) {
            "Cannot get ActivityManager service"
        }
    }

    private val allBlockingHeadsUpApps = get<GetAllBlockingHeadsUpApps>().invoke()
        .map(AppsWithSettingsState::Data)
        .stateIn(scope, SharingStarted.Eagerly, Loading)

    override fun onListenerConnected() {
        super.onListenerConnected()
        notificationAccessVerifier.hasNotificationAccess.value = true
        logger i "NotificationListener connected"
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        notificationAccessVerifier.hasNotificationAccess.value = false
        logger i "NotificationListener disconnected"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        val notificationPackageName = sbn.packageName

        if (notificationPackageName == Strings.AppPackage)
            clearEmptyNotification()
        else
            maybeBlockHeadsUp(notificationPackageName)
    }

    private fun maybeBlockHeadsUp(notificationPackageName: String) {
        scope.launch {

            // Wait for Data to be loaded
            while (allBlockingHeadsUpApps.value is Loading) {
                delay(10)
            }

            val appsWithSettings = (allBlockingHeadsUpApps.value as Data).appsWithSettings
            val foregroundPackageNames =
                activityManager.runningAppProcesses.flatMap { it.pkgList.toList() }

            val shouldBlock = appsWithSettings.any {
                it.app.packageName.value in foregroundPackageNames
            }

            if (shouldBlock) {
                logger i "NotificationListener blocking heads up for notification " +
                    notificationPackageName
                postEmptyNotification()

            } else {
                logger i "NotificationListener ignoring notification " +
                    notificationPackageName
            }
        }
    }

    private fun postEmptyNotification() {
        createNotification(id = NotificationId) {
            behaviour {
                importance = NotificationImportance.HIGH
            }
            channel(id = NotificationChannelId, name = Strings.NotificationChannelName) {
                description = Strings.NotificationChannelDescription
            }
            notification {
                smallIconRes = R.drawable.ic_notification
                title = ""
            }
        }.let { notification ->
            notification.fullScreenIntent = PendingIntent.getService(
                applicationContext,
                0,
                Intent(),
                0
            )
            notificationManager.notify(NotificationId, notification)
        }
    }

    private fun clearEmptyNotification() {
        notificationManager.cancel(NotificationId)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel("Service is destroyed")
    }

    private companion object {
        const val NotificationId = 148
        const val NotificationChannelId = "HeadsDown"
    }
}
