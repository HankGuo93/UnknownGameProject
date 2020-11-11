package obj.bullet

import mathUtils.Coordinate
import mathUtils.Vector
import obj.utils.BulletsAnimator
import java.awt.Graphics
import kotlin.math.roundToInt
import kotlin.random.Random

class Rocket(x: Int, y: Int, flyAngle: Double, range:Double,damage:Int = 0)
    : BulletComm(x,y,flyAngle,range,damage,
    3,3,40,40) {  /* adjust size */
    override var picFixAngle: Int = 45
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
        BulletsAnimator(BulletsAnimator.State.ROCKET,flyAngle.toInt()+picFixAngle)
    private var tmpDamage = damage //暫存用 只傳給boom

    override var bulletSpeed: Double = 15.0

    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return Rocket(fx, fy, fa,r, d.roundToInt())
    }

    init {
        bulletSpeed += Random.nextInt(-2,2)
        this.damage = 0 //火箭本身無傷害
        criticalDamage = this.damage
    }

    override fun paintComponent(g: Graphics) {
        if (lifecycle == LIFESTAGE.FLY) {
            animator.paint(
                painter.left,
                painter.top,
                painter.width,
                painter.height, g
            )
        }
    }

    override fun boomSmoke(): BulletComm? {//碰撞後子彈 產生 大型爆炸 傳入傷害值
        if (lifecycle == LIFESTAGE.FLY) {
            lifecycle = LIFESTAGE.BOOM
            delay.stop()
            delay.countLimit = LIFESTAGE.BOOM.keepTime
            delay.play()

            return RocketBoom(collider.centerX , collider.centerY , flyAngle,maxRange,tmpDamage)
        }
        return null
    }


}