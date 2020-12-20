package studio.forface.headsdown.notifications

import android.content.ComponentName
import kotlinx.coroutines.flow.MutableStateFlow

class NotificationAccessVerifier(
    private val notificationListenerComponentName: ComponentName,
    private val getStringFromSecureSettings: (String) -> String
) {

    val hasNotificationAccess = MutableStateFlow(hasNotificationAccess())

    private fun hasNotificationAccess(): Boolean {
        val flat = getStringFromSecureSettings("enabled_notification_listeners")
        return notificationListenerComponentName.flattenToString() in flat
    }
}
