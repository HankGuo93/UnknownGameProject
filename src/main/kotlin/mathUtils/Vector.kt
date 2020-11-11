package mathUtils


import kotlin.math.*
import kotlin.math.roundToInt

class Vector(var distance: Double, var degree: Double) {
    override fun toString(): String {
        return "($distance,$degree degree ) "
    }

    companion object {
        fun xyToVector(c: Coordinate): Vector {
            val radian = atan2(c.y,c.x)
            var d = c.x / cos(radian)
            var degree = radian  / Math.PI* 180
            if (d <0) {
                d=abs(d)
                degree += 180
            }
            if(degree<0) degree +=360

            return Vector(d, degree)
        }
    }

}