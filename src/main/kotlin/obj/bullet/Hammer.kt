package obj.bullet

import mathUtils.Coordinate
import mathUtils.Vector
import obj.utils.BulletsAnimator
import java.awt.Point
import kotlin.math.roundToInt
import kotlin.random.Random

class Hammer(x: Int, y: Int, flyAngle: Double, range:Double, damage:Int = 20)
    : BulletComm(x,y,flyAngle,2000.0,damage) {
    override var picFixAngle: Int = 0
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
            BulletsAnimator(BulletsAnimator.State.HAMMER,flyAngle.toInt()+picFixAngle)

    override var bulletSpeed: Double = 20.0
    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return Hammer(fx,fy,fa,r,d.roundToInt())
    }

    init {
        criticalDamage = this.damage
        gravityForce = 0.4

    }

}