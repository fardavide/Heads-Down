package studio.forface.headsdown

import android.graphics.drawable.Drawable
import io.mockk.mockk
import studio.forface.headsdown.model.App
import studio.forface.headsdown.model.PackageName

val CineScout = App(
    appName = "CineScout",
    packageName = "studio.forface.cinescout"
)

val HeadsDown = App(
    appName = "HeadsDown",
    packageName = "studio.forface.headsdown"
)

val WildRift = App(
    appName = "WildRift",
    packageName = "some.pkg.wildrift"
)

val Zooba = App(
    appName = "Zooba",
    packageName = "some.pkg.zooba"
)

private val MockDrawable: Drawable = mockk()
private fun App(appName: String, packageName: String) = App(
    appName = appName,
    packageName = PackageName(packageName),
    icon = mockk()
)
