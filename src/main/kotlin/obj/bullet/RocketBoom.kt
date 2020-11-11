package obj.bullet

import mathUtils.Coordinate
import mathUtils.Vector
import obj.utils.BulletsAnimator
import java.awt.Graphics
import kotlin.math.roundToInt
import kotlin.random.Random

class RocketBoom(x: Int, y: Int, flyAngle: Double, range:Double,damage:Int = 50)
    : BulletComm(x,y,flyAngle,range,damage,
    300,300,400,300) {  /* adjust size */
    override var picFixAngle: Int = 45
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
        BulletsAnimator(BulletsAnimator.State.ROCKET,flyAngle.toInt()+picFixAngle)

    override var damage: Int= damage
        get() {
            if (lifecycle == LIFESTAGE.BOOMHIT) {
                return field
            }
            return 0
        }

    override var bulletSpeed: Double = 0.0 //原地不飛行

    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {

        return RocketBoom(fx, fy, fa,r, d.roundToInt())
    }

    init {
        criticalDamage = this.damage
        lifecycle = LIFESTAGE.BOOMHIT
        delay.stop()
        delay.countLimit = 1 //只維持1 frame 有傷害
        delay.play()
    }

    override fun paintComponent(g: Graphics) {
        if (lifecycle == LIFESTAGE.BOOM) {
            animator.paint(
                painter.left,
                painter.top,
                painter.width,
                painter.height, g
            )
        }
    }
    override fun boomSmoke(): BulletComm? {//撥放煙霧效果
        if (lifecycle == LIFESTAGE.FLY ){
            lifecycle = LIFESTAGE.BOOM
            animator = BulletsAnimator(BulletsAnimator.State.BOOMBSMOKE, flyAngle.toInt() - 90)
            delay.stop()
            delay.countLimit = BulletsAnimator.State.BOOMBSMOKE.speed
            delay.play()
        }
        return null
    }
}