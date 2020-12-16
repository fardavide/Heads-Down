package studio.forface.headsdown.data

import assert4k.assert
import assert4k.equals
import assert4k.that
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import studio.forface.headsdown.CineScout
import studio.forface.headsdown.HeadsDown
import studio.forface.headsdown.WildRift
import studio.forface.headsdown.Zooba
import studio.forface.headsdown.model.AppSettings
import studio.forface.headsdown.model.PackageName
import studio.forface.headsdown.model.with
import kotlin.test.Test

class AppRepositoryImplTest {

    private val allTestApps = listOf(
        CineScout,
        HeadsDown,
        WildRift,
        Zooba
    )
    private val androidAppSource: AndroidAppSource = mockk {
        every { allApps() } returns allTestApps
    }
    private val settingsSource: SettingsSource = mockk()
    private val repo = AppRepositoryImpl(androidAppSource, settingsSource)

    @Test
    fun `allApps returns all the apps if no settings is stored`() = runBlockingTest {

        // given
        every { settingsSource.appsWithBlockedHeadsUp() } returns flowOf(emptySet())
        val expected = listOf(
            CineScout with AppSettings(shouldBlockHeadsUp = false),
            HeadsDown with AppSettings(shouldBlockHeadsUp = false),
            WildRift with AppSettings(shouldBlockHeadsUp = false),
            Zooba with AppSettings(shouldBlockHeadsUp = false),
        )

        // when
        val result = repo.allApps().first()

        // then
        assert that result equals expected
    }

    @Test
    fun `allApps returns all the apps with relative stored settings`() = runBlockingTest {

        // given
        val appsThatShouldBlockHeadsUp = setOf(WildRift.packageName, Zooba.packageName)
        every { settingsSource.appsWithBlockedHeadsUp() } returns flowOf(appsThatShouldBlockHeadsUp)
        val expected = listOf(
            CineScout with AppSettings(shouldBlockHeadsUp = false),
            HeadsDown with AppSettings(shouldBlockHeadsUp = false),
            WildRift with AppSettings(shouldBlockHeadsUp = true),
            Zooba with AppSettings(shouldBlockHeadsUp = true),
        )

        // when
        val result = repo.allApps().first()

        // then
        assert that result equals expected
    }

    @Test
    fun `updateAppSetting can set to block heads up`() = runBlockingTest {

        // given
        val appsThatShouldBlockHeadsUp = MutableStateFlow(setOf<PackageName>())
        every { settingsSource.appsWithBlockedHeadsUp() } answers {
            appsThatShouldBlockHeadsUp
        }
        coEvery { settingsSource.addToBlockHeadsUp(any()) } answers {
            appsThatShouldBlockHeadsUp.value += firstArg<PackageName>()
        }
        coEvery { settingsSource.removeFromBlockHeadsUp(any()) } answers {
            appsThatShouldBlockHeadsUp.value -= firstArg<PackageName>()
        }
        val expected = listOf(
            CineScout with AppSettings(shouldBlockHeadsUp = false),
            HeadsDown with AppSettings(shouldBlockHeadsUp = false),
            WildRift with AppSettings(shouldBlockHeadsUp = true),
            Zooba with AppSettings(shouldBlockHeadsUp = true),
        )

        // when
        val result = with(repo) {
            updateAppSettings(WildRift, AppSettings(shouldBlockHeadsUp = true))
            updateAppSettings(Zooba, AppSettings(shouldBlockHeadsUp = true))
            allApps().first()
        }

        // then
        assert that result equals expected
    }

    @Test
    fun `updateAppSetting can set to not block heads up`() = runBlockingTest {

        // given
        val appsThatShouldBlockHeadsUp = MutableStateFlow(setOf<PackageName>())
        every { settingsSource.appsWithBlockedHeadsUp() } answers {
            appsThatShouldBlockHeadsUp
        }
        coEvery { settingsSource.addToBlockHeadsUp(any()) } answers {
            appsThatShouldBlockHeadsUp.value += firstArg<PackageName>()
        }
        coEvery { settingsSource.removeFromBlockHeadsUp(any()) } answers {
            appsThatShouldBlockHeadsUp.value -= firstArg<PackageName>()
        }
        val expected = listOf(
            CineScout with AppSettings(shouldBlockHeadsUp = false),
            HeadsDown with AppSettings(shouldBlockHeadsUp = false),
            WildRift with AppSettings(shouldBlockHeadsUp = true),
            Zooba with AppSettings(shouldBlockHeadsUp = true),
        )

        // when
        val result = with(repo) {
            for (app in allTestApps)
                updateAppSettings(app, AppSettings(shouldBlockHeadsUp = true))

            updateAppSettings(CineScout, AppSettings(shouldBlockHeadsUp = false))
            updateAppSettings(HeadsDown, AppSettings(shouldBlockHeadsUp = false))

            allApps().first()
        }

        // then
        assert that result equals expected
    }
}
