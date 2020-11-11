package obj.bullet

import obj.utils.BulletsAnimator
import kotlin.math.roundToInt
import kotlin.random.Random

class SingleB(x: Int, y: Int, flyAngle: Double, range:Double,damage:Int = 20)
    : BulletComm(x,y,flyAngle,range,damage) {
    override var picFixAngle: Int = 45
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
            BulletsAnimator(BulletsAnimator.State.GUN_B,flyAngle.toInt()+picFixAngle)

    override var bulletSpeed: Double= 10.0
    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return SingleB(fx,fy,fa,r,d.roundToInt())
    }

    init {
        bulletSpeed += Random.nextInt(-1,1)
        criticalDamage = this.damage
    }
}