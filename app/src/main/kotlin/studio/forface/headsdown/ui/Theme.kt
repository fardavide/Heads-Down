package studio.forface.headsdown.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import studio.forface.headsdown.ui.Colors.Bittersweet
import studio.forface.headsdown.ui.Colors.CambridgeBlue
import studio.forface.headsdown.ui.Colors.CeladonBlue
import studio.forface.headsdown.ui.Colors.CeruleanFrost
import studio.forface.headsdown.ui.Colors.Cultured
import studio.forface.headsdown.ui.Colors.EerieBlack
import studio.forface.headsdown.ui.Colors.EtonBlue
import studio.forface.headsdown.ui.Colors.OrangeRedCrayola
import studio.forface.headsdown.ui.Colors.RichBlack
import studio.forface.headsdown.ui.Colors.SalmonPink
import studio.forface.headsdown.ui.Colors.White

private val DarkColorPalette = darkColors(
    primary = CeruleanFrost,
    primaryVariant = CambridgeBlue,
    secondary = SalmonPink,
    // secondaryVariant = Bittersweet,
    background = RichBlack,
    surface = EerieBlack
)

private val LightColorPalette = lightColors(
    primary = CeladonBlue,
    primaryVariant = EtonBlue,
    secondary = Bittersweet,
    secondaryVariant = OrangeRedCrayola,
    background = White,
    surface = Cultured

    /* Other default colors to override
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun HeadsDownTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
