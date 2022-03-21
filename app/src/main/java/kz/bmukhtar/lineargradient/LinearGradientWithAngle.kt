package kz.bmukhtar.lineargradient

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import kotlin.math.*

/**
 * Android analog of CSS linear-gradient which supports any angle
 * https://developer.mozilla.org/en-US/docs/Web/CSS/gradient/linear-gradient
 */
@Immutable
class LinearGradientWithAngle internal constructor(
    private val colors: List<Color>,
    private val stops: List<Float>? = null,
    private val angle: Float = 0f,
    private val tileMode: TileMode = TileMode.Clamp
) : ShaderBrush() {

    private val normalizedAngle = angle % 360
    private val angleInRadians = Math.toRadians(normalizedAngle.toDouble()).toFloat()

    override fun createShader(size: Size): Shader {
        val diagonal = sqrt(size.width.pow(2) + size.height.pow(2))
        val angleBetweenDiagonalAndWidth = acos(size.width / diagonal)
        val angleBetweenDiagonalAndGradientLine =
            if ((normalizedAngle > 90 && normalizedAngle < 180)
                || (normalizedAngle > 270 && normalizedAngle < 360)
            ) {
                PI.toFloat() - angleInRadians - angleBetweenDiagonalAndWidth
            } else {
                angleInRadians - angleBetweenDiagonalAndWidth
            }
        val gradientLineLength = abs(cos(angleBetweenDiagonalAndGradientLine) * diagonal)

        val horizontalOffset = gradientLineLength * cos(angleInRadians) / 2
        val verticalOffset = gradientLineLength * sin(angleInRadians) / 2

        val start = size.center + Offset(-horizontalOffset, verticalOffset)
        val end = size.center + Offset(horizontalOffset, -verticalOffset)

        return LinearGradientShader(
            colors = colors,
            colorStops = stops,
            from = start,
            to = end,
            tileMode = tileMode
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinearGradientWithAngle) return false

        if (colors != other.colors) return false
        if (stops != other.stops) return false
        if (angle != other.angle) return false
        if (tileMode != other.tileMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = colors.hashCode()
        result = 31 * result + (stops?.hashCode() ?: 0)
        result = 31 * result + angle.hashCode()
        result = 31 * result + tileMode.hashCode()
        return result
    }

    override fun toString(): String {
        return "LinearGradient(colors=$colors, " +
                "stops=$stops, " +
                "angle=$angle, " +
                "tileMode=$tileMode)"
    }
}