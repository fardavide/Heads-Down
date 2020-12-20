package studio.forface.headsdown.viewmodel

import assert4k.assert
import assert4k.equals
import assert4k.that
import co.touchlab.kermit.CommonLogger
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import studio.forface.headsdown.data.AppRepository
import kotlin.test.Test

class AppViewModelTest {

    private val repo: AppRepository = mockk {
        every { allApps() } returns emptyFlow()
    }
    private val viewModel = AppViewModel(logger = CommonLogger(), repository = repo)

    @Test
    fun `InitialState at start`() = runBlockingTest {

        assert that viewModel.state.first() equals InitialState
    }

    @Test
    fun `correct state with notification access`() = runBlockingTest {

        val expected = AppState(
            hasNotificationAccess = true,
            generalHeadsUpBlockEnabled = true,
            AppsWithSettingsState.Loading
        )

        assert that viewModel.state.first() equals expected
    }

    @Test
    fun `correct state without notification access`() = runBlockingTest {

        val expected = AppState(
            hasNotificationAccess = false,
            generalHeadsUpBlockEnabled = true,
            AppsWithSettingsState.Loading
        )

        assert that viewModel.state.first() equals expected
    }
}
