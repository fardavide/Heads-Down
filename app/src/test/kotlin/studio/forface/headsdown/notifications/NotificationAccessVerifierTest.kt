package studio.forface.headsdown.notifications

import android.content.ComponentName
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NotificationAccessVerifierTest {

    private val notificationListenerComponentName: ComponentName = mockk {
        every { flattenToString() } returns "notificationListener"
    }
    private var stringFromSecureSettings: String = ""
    private fun getVerifier() = NotificationAccessVerifier(
        notificationListenerComponentName = notificationListenerComponentName
    ) { stringFromSecureSettings }

    @Test
    fun `emit initial value if access is granted`() = runBlockingTest {

        // given
        stringFromSecureSettings = "notificationListener"

        // when
        val result = getVerifier().hasNotificationAccess.first()

        // then
        assertTrue(result)
    }

    @Test
    fun `emit initial value if access is not granted`() = runBlockingTest {

        // given
        stringFromSecureSettings = ""

        // when
        val result = getVerifier().hasNotificationAccess.first()

        // then
        assertFalse(result)
    }
}
