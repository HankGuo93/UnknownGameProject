package obj.utils

import core.GameKernel
import core.GameKernel.GameInterface
import utils.Global
import java.awt.Color
import java.awt.Graphics
import kotlin.math.roundToInt

abstract class GameObject(
        val collider: Rect, val painter: Rect = Rect(collider),
        val c1UP: Rect,
        val c2UP: Rect,
        val c3UP: Rect,
        val c4UP: Rect,
        val c5UP: Rect,
        val c1DOWN: Rect,
        val c2DOWN: Rect,
        val c3DOWN: Rect,
        val c4DOWN: Rect,
        val c5DOWN: Rect,
) : GameInterface {
    constructor(
            x: Int, y: Int, width: Int, height: Int,
            x2: Int = x, y2: Int = y, width2: Int = width, height2: Int = height
    ) : this(
            collider = Rect.genWithCenter(x, y, width, height),
            painter = Rect.genWithCenter(x2, y2, width2, height2),
            c1UP = Rect.genWithCenter(x - width/2 + 8              , y + height * 4 / 5 - height/2+5, width/ 5, height/ 5),
            c2UP = Rect.genWithCenter(x + width * 1 / 5 - width/2+8, y + height * 3 / 5 - height/2+5, width/ 5, height/ 5),
            c3UP = Rect.genWithCenter(x + width * 2 / 5 - width/2+8, y + height * 2 / 5 - height/2+5, width/ 5, height/ 5),
            c4UP = Rect.genWithCenter(x + width * 3 / 5 - width/2+8, y + height * 1 / 5 - height/2+5, width/ 5, height/ 5),
            c5UP = Rect.genWithCenter(x + width * 4 / 5 - width/2+8, y - height / 2 + 5              , width/5, height/ 5),

            c1DOWN = Rect.genWithCenter(x - width/2                , y - height / 2 + 5              , width/ 5, height/ 5),
            c2DOWN = Rect.genWithCenter(x + width * 1 / 5 - width/2, y + height * 1 / 5 - height/2 +5, width/ 5, height/ 5),
            c3DOWN = Rect.genWithCenter(x + width * 2 / 5 - width/2, y + height * 2 / 5 - height/2 +5, width/ 5, height/ 5),
            c4DOWN = Rect.genWithCenter(x + width * 3 / 5 - width/2, y + height * 3 / 5 - height/2 +5, width/ 5, height/ 5),
            c5DOWN = Rect.genWithCenter(x + width * 4 / 5 - width/2, y + height * 4 / 5 - height/2 +5, width/ 5, height/ 5),
    )

    fun isCollisionUPStair(obj: GameObject): Boolean {
        if (c5UP.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c5UP.top))
            return true
        }
        if (c4UP.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c4UP.top))
            return true
        }
        if (c3UP.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c3UP.top))
            return true
        }
        if (c2UP.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c2UP.top))
            return true
        }
        if (c1UP.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c1UP.top))
            return true
        }
        return false
    }

    fun isCollisionDOWNStair(obj: GameObject): Boolean {
        if (c1DOWN.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c1DOWN.top))
            return true
        }
        if (c2DOWN.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c2DOWN.top))
            return true
        }
        if (c3DOWN.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c3DOWN.top))
            return true
        }
        if (c4DOWN.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c4DOWN.top))
            return true
        }
        if (c5DOWN.overlap(obj.collider)) {
            obj.translate(y = -(obj.collider.bottom - c5DOWN.top))
            return true
        }
        return false
    }
    fun isCollisionUPStairBullet(obj: GameObject): Boolean {
        if (c5UP.overlap(obj.collider)) {
            return true
        }
        if (c4UP.overlap(obj.collider)) {
            return true
        }
        if (c3UP.overlap(obj.collider)) {
            return true
        }
        if (c2UP.overlap(obj.collider)) {
            return true
        }
        if (c1UP.overlap(obj.collider)) {
            return true
        }
        return false
    }

    fun isCollisionDOWNStairBullet(obj: GameObject): Boolean {
        if (c1DOWN.overlap(obj.collider)) {
            return true
        }
        if (c2DOWN.overlap(obj.collider)) {
            return true
        }
        if (c3DOWN.overlap(obj.collider)) {
            return true
        }
        if (c4DOWN.overlap(obj.collider)) {
            return true
        }
        if (c5DOWN.overlap(obj.collider)) {
            return true
        }
        return false
    }
    val outOfScreen: Boolean
        get() {
            if (painter.bottom <= 0) {
                return true
            }
            if (painter.right <= 0) {
                return true
            }
            return if (painter.left >= Global.SCREEN_X) {
                true
            } else painter.top >= Global.SCREEN_Y
        }

    private var innerX: Double = collider.centerX.toDouble()

    private var innerY: Double = collider.centerY.toDouble()

    val touchTop: Boolean
        get() = collider.top <= 0

    val touchLeft: Boolean
        get() = collider.left <= 0

    val touchRight: Boolean
        get() = collider.right >= Global.SCREEN_X

    val touchBottom: Boolean
        get() = collider.bottom >= Global.SCREEN_Y

    fun isCollision(obj: GameObject): Boolean {
        return collider.overlap(obj.collider)
    }

    fun <T : GameObject> isCollision(obj: List<T>): List<T> {
        return obj.filter { it.collider.overlap(collider) }
    }

    fun translate(x: Int = 0, y: Int = 0) {

        var p_dx = collider.centerX - painter.centerX
        var p_dy = collider.centerY - painter.centerY

        innerX += x
        innerY += y

        collider.setCenter(
                innerX.roundToInt(),
                innerY.roundToInt()
        )
        painter.setCenter(
                (innerX - p_dx).roundToInt(),
                (innerY - p_dy).roundToInt()
        )

        c1UP.translate(x,y)
        c2UP.translate(x,y)
        c3UP.translate(x,y)
        c4UP.translate(x,y)
        c5UP.translate(x,y)
        c1DOWN.translate(x,y)
        c2DOWN.translate(x,y)
        c3DOWN.translate(x,y)
        c4DOWN.translate(x,y)
        c5DOWN.translate(x,y)

    }

    fun translate(x: Double = 0.0, y: Double = 0.0) {
        var p_dx = collider.centerX - painter.centerX
        var p_dy = collider.centerY - painter.centerY
        var c1up_dx = collider.centerX - c1UP.centerX
        var c1up_dy = collider.centerY - c1UP.centerY
        var c2up_dx = collider.centerX - c2UP.centerX
        var c2up_dy = collider.centerY - c2UP.centerY
        var c3up_dx = collider.centerX - c3UP.centerX
        var c3up_dy = collider.centerY - c3UP.centerY
        var c4up_dx = collider.centerX - c4UP.centerX
        var c4up_dy = collider.centerY - c4UP.centerY
        var c5up_dx = collider.centerX - c5UP.centerX
        var c5up_dy = collider.centerY - c5UP.centerY

        var c1down_dx = collider.centerX - c1DOWN.centerX
        var c1down_dy = collider.centerY - c1DOWN.centerY
        var c2down_dx = collider.centerX - c2DOWN.centerX
        var c2down_dy = collider.centerY - c2DOWN.centerY
        var c3down_dx = collider.centerX - c3DOWN.centerX
        var c3down_dy = collider.centerY - c3DOWN.centerY
        var c4down_dx = collider.centerX - c4DOWN.centerX
        var c4down_dy = collider.centerY - c4DOWN.centerY
        var c5down_dx = collider.centerX - c5DOWN.centerX
        var c5down_dy = collider.centerY - c5DOWN.centerY

        innerX += x
        innerY += y

        collider.setCenter(
                innerX.roundToInt(),
                innerY.roundToInt()
        )

        painter.setCenter(
                (innerX - p_dx).roundToInt(),
                (innerY - p_dy).roundToInt()
        )
        c1UP.setCenter(
                (innerX - c1up_dx).roundToInt(),
                (innerY - c1up_dy).roundToInt()
        )
        c2UP.setCenter(
                (innerX - c2up_dx).roundToInt(),
                (innerY - c2up_dy).roundToInt()
        )
        c3UP.setCenter(
                (innerX - c3up_dx).roundToInt(),
                (innerY - c3up_dy).roundToInt()
        )
        c4UP.setCenter(
                (innerX - c4up_dx).roundToInt(),
                (innerY - c4up_dy).roundToInt()
        )
        c5UP.setCenter(
                (innerX - c5up_dx).roundToInt(),
                (innerY - c5up_dy).roundToInt()
        )
        c1DOWN.setCenter(
                (innerX - c1down_dx).roundToInt(),
                (innerY - c1down_dy).roundToInt()
        )
        c2DOWN.setCenter(
                (innerX - c2down_dx).roundToInt(),
                (innerY - c2down_dy).roundToInt()
        )
        c3DOWN.setCenter(
                (innerX - c3down_dx).roundToInt(),
                (innerY - c3down_dy).roundToInt()
        )
        c4DOWN.setCenter(
                (innerX - c4down_dx).roundToInt(),
                (innerY - c4down_dy).roundToInt()
        )
        c5DOWN.setCenter(
                (innerX - c5down_dx).roundToInt(),
                (innerY - c5down_dy).roundToInt()
        )
    }

    override fun paint(g: Graphics) {
        paintComponent(g)
        if (Global.IS_DEBUG) {
            g.color = Color.RED
            g.drawRect(painter.left, painter.top, painter.width, painter.height)
            g.color = Color.BLUE
            g.drawRect(collider.left, collider.top, collider.width, collider.height)

            g.color = Color.YELLOW
            g.drawRect(c1UP.left, c1UP.top, c1UP.width, c1UP.height)
            g.drawRect(c2UP.left, c2UP.top, c2UP.width, c2UP.height)
            g.drawRect(c3UP.left, c3UP.top, c3UP.width, c3UP.height)
            g.drawRect(c4UP.left, c4UP.top, c4UP.width, c4UP.height)
            g.drawRect(c5UP.left, c5UP.top, c5UP.width, c5UP.height)
            g.color = Color.GREEN
            g.drawRect(c1DOWN.left, c1DOWN.top, c1DOWN.width, c1DOWN.height)
            g.drawRect(c2DOWN.left, c2DOWN.top, c2DOWN.width, c2DOWN.height)
            g.drawRect(c3DOWN.left, c3DOWN.top, c3DOWN.width, c3DOWN.height)
            g.drawRect(c4DOWN.left, c4DOWN.top, c4DOWN.width, c4DOWN.height)
            g.drawRect(c5DOWN.left, c5DOWN.top, c5DOWN.width, c5DOWN.height)

            g.color = Color.BLACK
        }
    }

    override val input: ((GameKernel.Input.Event) -> Unit)? = null

    abstract fun paintComponent(g: Graphics)
}