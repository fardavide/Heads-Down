package studio.forface.headsdown

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
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

private fun App(appName: String, packageName: String) = App(
    appName = appName,
    packageName = PackageName(packageName),
    icon = object : Drawable() {
        override fun draw(canvas: Canvas) {}
        override fun setAlpha(alpha: Int) {}
        override fun setColorFilter(colorFilter: ColorFilter?) {}
        override fun getOpacity(): Int = PixelFormat.OPAQUE
    }
)
