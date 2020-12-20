package studio.forface.headsdown.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import co.touchlab.kermit.Logger
import org.koin.android.ext.android.inject
import studio.forface.headsdown.utils.i

class NotificationListener : NotificationListenerService() {

    private val notificationAccessVerifier: NotificationAccessVerifier by inject()
    private val logger: Logger by inject()

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
        logger i "NotificationListener received notification $sbn"
    }
}
