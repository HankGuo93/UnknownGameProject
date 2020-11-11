package obj.bullet

import core.utils.Delay
import mathUtils.Coordinate
import mathUtils.Vector
import obj.utils.BulletsAnimator
import obj.utils.GameObject

import java.awt.Graphics
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

abstract class BulletComm(
    x: Int, y: Int, var flyAngle: Double, val maxRange: Double,
    baseDemage: Int = 10,
    colliderWidth: Int = 5, colliderHeight: Int = 2, /* collider default size*/
    painterWidth: Int = 32, painterHeight: Int = 32      /* painter default size*/
) : GameObject(x, y, colliderWidth, colliderHeight, x, y, painterWidth, painterHeight) {


    enum class LIFESTAGE(val keepTime: Int) {
        INIT(0),
        FLY(0),
        BOOMHIT(1),
        BOOM(12),
        REMOVE(0)
    }

    var gravityForce :Double = 0.0
    open var lifecycle = LIFESTAGE.FLY
        protected set

    protected var delay = Delay(0)

    protected abstract var picFixAngle: Int
    protected abstract var camareFix: Double
    protected abstract var animator: BulletsAnimator

    open var damage: Int = baseDemage
        get() {
            if (lifecycle == LIFESTAGE.FLY) {
                return field
            }
            return 0
        }

    protected var powerCount: Int = 0
    protected var powerTarget: Int = 30

    var criticalDamage = damage * 10000
        get() {
            if (lifecycle == LIFESTAGE.FLY) {
                return field
            }
            return 0
        }
    protected abstract var bulletSpeed: Double


    //for bullet fly distance
    protected var genPoint = Coordinate(x.toDouble(), y.toDouble())

    protected var shakeLevel:Int = 5
    protected var shakeCount:Int = 0

    open fun boomSmoke(): BulletComm? {
        if (lifecycle == LIFESTAGE.FLY) {

            lifecycle = LIFESTAGE.BOOM
            animator = BulletsAnimator(BulletsAnimator.State.BULLETSMOKE, flyAngle.toInt() - 90)
            delay.stop()
            delay.countLimit = LIFESTAGE.BOOM.keepTime
            delay.play()
        }
        return null
    }

    fun camaraFix(f: Double) {
        camareFix = f
    }

    fun camaraFix(f: Int) {
        camareFix = f.toDouble()
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
    fun shakeValue():Coordinate{
        var level:Int
        shakeCount++
        if (shakeLevel >1 ){
            shakeLevel--
            level =Random.nextInt(shakeLevel)
            var x = level* sin(shakeCount.toDouble()) + Random.nextInt(2)
            var y = level* cos(shakeCount.toDouble()) + Random.nextInt(2)
            return Coordinate(x,y)
        }else{
            level = 0
            return Coordinate(0.0,0.0)
        }

    }


    override fun update(timePassed: Long) {
        var c = Coordinate.vectorToXY(Vector(bulletSpeed, flyAngle))
        if(gravityForce > 0){
            c.y += gravityForce
            var tmpV = Vector.xyToVector(c)
            bulletSpeed=tmpV.distance
            flyAngle=tmpV.degree
        }


        when (lifecycle) {
            LIFESTAGE.FLY -> {

                translate(c.x + camareFix, c.y)

                var fly = Vector.xyToVector(Coordinate(collider.centerX - genPoint.x, collider.centerY - genPoint.y))
                if (fly.distance > maxRange) {
                    boomSmoke();
//                    var c = Coordinate.vectorToXY(Vector(10000.0, flyAngle))
//                    translate(c.x, c.y) //超出飛行距離時，傳送到畫面外
                }
            }
            LIFESTAGE.BOOMHIT -> {
                if (delay.count()) {
                    lifecycle = LIFESTAGE.FLY
                    boomSmoke()
                }
            }
            LIFESTAGE.BOOM -> {

                translate(x = camareFix)

                if (delay.count()) {
                    lifecycle = LIFESTAGE.REMOVE
                    var c = Coordinate.vectorToXY(Vector(10000.0, flyAngle))
                    translate(c.x, c.y) //超出飛行距離時，傳送到畫面外
                }
            }

        }

        animator.update(timePassed)
    }

    abstract fun clone(fx: Int, fy: Int, fa: Double, r: Double, d: Double): BulletComm
}