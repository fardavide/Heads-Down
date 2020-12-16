package studio.forface.headsdown.ui

import androidx.compose.ui.graphics.Color

object Colors {

    val White = Color(rgb = 0xffffff)
    val Cultured = Color(rgb = 0xf9f9f9)
    val EerieBlack = Color(rgb = 0x1c1c1c)
    val RichBlack = Color(rgb = 0x0c0c0c)
    val SalmonPink = Color(rgb = 0xff9999)
    val Bittersweet = Color(rgb = 0xff6b6b)
    val OrangeRedCrayola = Color(rgb = 0xff5c5c)
    val CambridgeBlue = Color(rgb = 0xa4ccB8)
    val EtonBlue = Color(rgb = 0x87bba2)
    val ShinyShamrock = Color(rgb = 0x6fae8f)
    val CeruleanFrost = Color(rgb = 0x5590b4)
    val CeladonBlue = Color(rgb = 0x457b9d)
    val BlueSapphire = Color(rgb = 0x386480)
}

fun Color(rgb: Long) =
    Color(color = rgb or 0xff000000)
