package studio.forface.headsdown.notifications

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.flow.MutableStateFlow

class NotificationAccessVerifier(
    private val context: Context
) {

    val hasNotificationAccess = MutableStateFlow(hasNotificationAccess())

    private fun hasNotificationAccess() =
        context.packageName in NotificationManagerCompat.getEnabledListenerPackages(context)
}
