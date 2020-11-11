package obj.bullet

import mathUtils.Coordinate
import mathUtils.Vector
import obj.utils.BulletsAnimator
import kotlin.math.roundToInt

class SniperB(x: Int, y: Int, flyAngle: Double, range:Double,damage:Int = 30)
    : BulletComm(x,y,flyAngle,range,damage) {
    override var picFixAngle: Int = 45
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
            BulletsAnimator(BulletsAnimator.State.SNIPER_B, flyAngle.toInt() + picFixAngle)

    override var bulletSpeed: Double = 10.0
    override fun clone(fx: Int, fy: Int, fa: Double,r:Double,d: Double): BulletComm {
        return SniperB(fx, fy, fa,r,d.roundToInt())
    }

    init {
        criticalDamage = damage*10
    }

    override fun boomSmoke(): BulletComm? {//子彈穿越效果
        if (lifecycle == LIFESTAGE.FLY) {
            lifecycle = LIFESTAGE.BOOM
            animator = BulletsAnimator(BulletsAnimator.State.BULLETSMOKE, flyAngle.toInt() - 90)
            delay.stop()
            delay.countLimit = LIFESTAGE.BOOM.keepTime
            delay.play()

            var m = Coordinate.vectorToXY(Vector( (utils.Global.MapObject * 2).toDouble() , flyAngle ))
            return SniperB(collider.centerX + m.x.roundToInt(), collider.centerY + m.y.roundToInt(), flyAngle,maxRange)
        }
        return null
    }

}