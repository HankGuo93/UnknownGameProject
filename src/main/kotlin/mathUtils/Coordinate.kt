package mathUtils

import kotlin.math.cos
import kotlin.math.sin

class Coordinate(var x: Double, var y: Double) {
    override fun toString(): String {
        return "($x,$y) "
    }

    companion object {
        fun vectorToXY(v: Vector): Coordinate {
            val radian = (v.degree / 180) * Math.PI
            return Coordinate(
                (v.distance * cos(radian)),
                (v.distance * sin(radian))
            )
        }
    }

}
