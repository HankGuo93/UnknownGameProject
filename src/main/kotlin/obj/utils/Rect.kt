package obj.utils

class Rect(var left: Int, var top: Int, var right: Int, var bottom: Int) {

    constructor(rect: Rect) : this(rect.left, rect.top, rect.right, rect.bottom)

    val centerX: Int
        get() = (left + right) / 2

    val centerY: Int
        get() = (top + bottom) / 2

    val exactCenterX: Float
        get() = (left + right) / 2f

    val exactCenterY: Float
        get() = (top + bottom) / 2f

    val width: Int
        get() = right - left

    val height: Int
        get() = bottom - top

    fun translate(dx: Int = 0, dy: Int = 0) {
        left += dx
        right += dx
        top += dy
        bottom += dy
    }

    fun setCenter(x: Int, y: Int) {
        translate(x - centerX, y - centerY)
    }

    fun overlap(left: Int, top: Int, right: Int, bottom: Int): Boolean {
        if (this.left > right) {
            return false
        }
        if (this.right < left) {
            return false
        }
        if (this.top > bottom) {
            return false
        }
        return this.bottom >= top
    }

    fun overlap(b: Rect): Boolean {
        return overlap(b.left, b.top, b.right, b.bottom)
    }

    companion object {
        fun genWithCenter(x: Int, y: Int, width: Int, height: Int): Rect {
            val left = x - width / 2
            val right = left + width
            val top = y - height / 2
            val bottom = top + height
            return Rect(left, top, right, bottom)
        }
    }
}