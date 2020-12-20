package studio.forface.headsdown.viewmodel

import assert4k.assert
import assert4k.equals
import assert4k.that
import co.touchlab.kermit.CommonLogger
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import studio.forface.headsdown.notifications.NotificationAccessVerifier
import studio.forface.headsdown.usecase.GetAllNonSystemApps
import kotlin.test.Test

class AppViewModelTest {

    private val getAllNonSystemApps: GetAllNonSystemApps = mockk {
        every { this@mockk() } returns flowOf(emptyList())
    }
    private val notificationAccessVerifier: NotificationAccessVerifier = mockk {
        every { hasNotificationAccess } returns MutableStateFlow(true)
    }
    private val viewModel = AppViewModel(
        logger = CommonLogger(),
        getAllNonSystemApps = getAllNonSystemApps,
        addToShouldBlockHeadsUp = mockk(),
        removeFromShouldBlockHeadsUp = mockk(),
        notificationAccessVerifier = notificationAccessVerifier
    )

    @Test
    fun `InitialState at start`() = runBlockingTest {

        assert that viewModel.state.first() equals InitialState
    }

    @Test
    fun `correct state with notification access`() = runBlockingTest {

        // given
        every { notificationAccessVerifier.hasNotificationAccess } returns
            MutableStateFlow(true)

        // when
        val expected = AppState(
            hasNotificationAccess = true,
            generalHeadsUpBlockEnabled = true,
            AppsWithSettingsState.Data(emptyList())
        )

        // then
        advanceUntilIdle()
        assert that viewModel.state.value equals expected
    }

    @Test
    fun `correct state without notification access`() = runBlockingTest {

        // given
        every { notificationAccessVerifier.hasNotificationAccess } returns
            MutableStateFlow(false)

        // when
        val expected = AppState(
            hasNotificationAccess = false,
            generalHeadsUpBlockEnabled = true,
            AppsWithSettingsState.Data(emptyList())
        )

        // then
        advanceUntilIdle()
        assert that viewModel.state.value equals expected
    }
}
