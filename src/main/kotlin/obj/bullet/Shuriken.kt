package obj.bullet

import mathUtils.Vector
import obj.utils.BulletsAnimator
import kotlin.math.roundToInt

class Shuriken(x: Int, y: Int, flyAngle: Double, range:Double,damage:Int = 5)
    : BulletComm(x,y,flyAngle,range,damage) {
    override var picFixAngle: Int = 0
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
        BulletsAnimator(BulletsAnimator.State.SHURIKEN,flyAngle.toInt()+picFixAngle)

    override var bulletSpeed: Double = 10.0
    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return Shuriken(fx,fy,fa,r,d.roundToInt())
    }

    init {
        criticalDamage = this.damage
    }
}