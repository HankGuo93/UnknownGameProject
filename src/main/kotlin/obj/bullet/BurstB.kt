package obj.bullet

import mathUtils.Vector
import obj.utils.BulletsAnimator
import kotlin.math.roundToInt
import kotlin.random.Random

class BurstB(x: Int, y: Int, flyAngle: Double, range:Double,damage:Int = 10)
    : BulletComm(x,y,flyAngle,range,damage) {
    override var picFixAngle: Int = 45
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
        BulletsAnimator(BulletsAnimator.State.MGUN_B,flyAngle.toInt()+picFixAngle)

    override var bulletSpeed: Double = 15.0

    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return BurstB(fx,fy,fa,r,d.roundToInt())
    }

    init {
        bulletSpeed += Random.nextInt(-1,1)
        criticalDamage = this.damage

    }
}