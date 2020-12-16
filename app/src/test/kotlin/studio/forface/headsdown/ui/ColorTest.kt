package studio.forface.headsdown.ui

import assert4k.*
import io.mockk.*
import kotlin.test.*

internal class ColorTest {

    @Test
    fun `alpha-red-green-blue have right values on Red RGB color`() {
        val color = Color(rgb = 0xFF0000)
        assert that color * {
            +alpha() equals 255
            +red() equals 255
            +green() equals 0
            +blue() equals 0
        }
    }

    @Test
    fun `alpha-red-green-blue have right values on Green RGB color`() {
        val color = Color(rgb = 0x00FF00)
        assert that color * {
            +alpha() equals 255
            +red() equals 0
            +green() equals 255
            +blue() equals 0
        }
    }

    @Test
    fun `alpha-red-green-blue have right values on Blue RGB color`() {
        val color = Color(rgb = 0x0000FF)
        assert that color * {
            +alpha() equals 255
            +red() equals 0
            +green() equals 0
            +blue() equals 255
        }
    }
}
