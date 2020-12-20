package studio.forface.headsdown.notifications

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import co.touchlab.kermit.Logger
import org.koin.android.ext.android.inject
import studio.forface.headsdown.utils.i

class NotificationListener : NotificationListenerService() {

    private val logger: Logger by inject()

    override fun onListenerConnected() {
        super.onListenerConnected()
        logger i "NotificationListener connected"
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        logger i "NotificationListener disconnected"
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        logger i "NotificationListener received notification $sbn"
    }
}
