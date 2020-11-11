package obj.bullet

import obj.utils.BulletsAnimator
import java.awt.Graphics
import kotlin.math.roundToInt
import kotlin.random.Random

class RefundEffect(x: Int, y: Int, flyAngle: Double, range:Double, damage:Int = 0)
    : BulletComm(x,y,flyAngle,range,damage,painterWidth = 16) {
    override var picFixAngle: Int = 0
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
            BulletsAnimator(BulletsAnimator.State.REFUND,flyAngle.toInt()+picFixAngle)

    override var bulletSpeed: Double= 0.0
    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return RefundEffect(fx,fy,fa,r,d.roundToInt())
    }

    init {

        criticalDamage = 0

        //重設BOOM時間
        lifecycle = LIFESTAGE.BOOM
        delay.stop()
        delay.countLimit = BulletsAnimator.State.REFUND.speed
        delay.play()

    }

    override fun paintComponent(g: Graphics) {

        if (lifecycle != LIFESTAGE.REMOVE) {
            animator.paint(
                painter.left,
                painter.top,
                painter.width,
                painter.height, g
            )
        }
    }
}