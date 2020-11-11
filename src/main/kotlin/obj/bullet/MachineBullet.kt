package obj.bullet

import obj.utils.BulletsAnimator
import kotlin.math.roundToInt
import kotlin.random.Random

class MachineBullet(x: Int, y: Int, flyAngle: Double, range:Double,damage:Int=20, painterWidth: Int = 8, painterHeight: Int = 8)
    : BulletComm(x,y,flyAngle,range,damage, painterWidth, painterHeight) {
    override var picFixAngle: Int = 45
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
        BulletsAnimator(BulletsAnimator.State.MACHINE_B,flyAngle.toInt()+picFixAngle)

    override var bulletSpeed: Double= 3.0

    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return MachineBullet(fx, fy, fa,r, d.roundToInt())
    }

    override fun boomSmoke(): BulletComm? {
        if (lifecycle == LIFESTAGE.FLY) {
            lifecycle = LIFESTAGE.BOOM
            animator = BulletsAnimator(BulletsAnimator.State.BULLETSMOKE, flyAngle.toInt() - 90)
            delay.stop()
            delay.countLimit = LIFESTAGE.BOOM.keepTime
            delay.play()
        }
        return null
    }

    init {
        bulletSpeed += Random.nextInt(0,1)
        criticalDamage = this.damage
    }
}