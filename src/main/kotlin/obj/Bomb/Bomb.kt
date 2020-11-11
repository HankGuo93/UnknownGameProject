package obj.bomb

import audio.AudioResourceController
import core.utils.Delay
import mathUtils.Coordinate
import mathUtils.Vector
import obj.bullet.BulletComm
import obj.utils.BulletsAnimator
import obj.utils.GameObject
import utils.Global

import java.awt.Graphics
import kotlin.math.absoluteValue
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

class Bomb(
    val x: Int,
    val y: Int,
    private val targetX: Int,
    private val targetY: Int,
    private var dir: Global.Direction = Global.Direction.RIGHT
) :
    GameObject(x, 400, 100, 1000, x, 400, 800, 600) {

    private enum class LIFESTAGE(val keepTime: Int) {
        CHARGE(24),
        ROLL(64),
        BOOM(24),
        REMOVE(0)
    }

    private var flyVector = Vector(0.0, 0.0)
    private var lifecycle = LIFESTAGE.CHARGE
    private var delay = Delay(0)

    private var camareFix: Double = 0.0
    private var animator = BulletsAnimator(BulletsAnimator.State.SKILLSTART, 0)
    var damage: Int = 100000 //固定傷害 不傳入
        private set

    private var ballSize: Int = 0
    private var ballMax: Int = 12
    private var ballShift: Int = 120

    private var chargeReady: Boolean = false

    private var shakeCount:Int = 0
    private var shakeLevel: Int =1

    init {
        flyVector = Vector.xyToVector(Coordinate((targetX - x).toDouble(), (targetY - y).toDouble()))
        flyVector.distance /= LIFESTAGE.ROLL.keepTime / 2
        delay.stop()
        delay.countLimit = LIFESTAGE.CHARGE.keepTime / 2
        delay.play()

        //播放大招音效
        AudioResourceController.getInstance()
            .play("sounds\\Bullets\\RockRoll.wav")

    }

    private fun charge() { //充電
        if (lifecycle == LIFESTAGE.CHARGE) {
            lifecycle = LIFESTAGE.ROLL

            delay.stop()
            delay.countLimit = LIFESTAGE.ROLL.keepTime
            delay.play()
        }
    }

    private fun boomsmoke() { //炸裂
        if (lifecycle == LIFESTAGE.ROLL) {
            lifecycle = LIFESTAGE.BOOM
            animator.setState(BulletsAnimator.State.SKILLEND)
            delay.stop()
            delay.countLimit = LIFESTAGE.BOOM.keepTime
            delay.play()
        }
    }

    fun camaraFix(f: Double) {
        camareFix = f
    }

    fun camaraFix(f: Int) {
        camareFix = f.toDouble()
    }

    fun shakeValue():Coordinate{
        if(lifecycle != LIFESTAGE.REMOVE){
            shakeCount+=2
            var x = shakeLevel*sin(shakeCount.toDouble())+ Random.nextInt(-2,2)
            var y = shakeLevel*cos(shakeCount.toDouble())+ Random.nextInt(-2,2)
            return Coordinate(x,y)
        }
        return Coordinate(0.0,0.0)
    }

    override fun paintComponent(g: Graphics) {
        if (ballSize < ballMax) {
            ballSize++
        }
        animator.setDir(dir)
        var shift: Int = ballShift //球的移動
        var genX: Int = 300
        when (dir) {
            Global.Direction.LEFT -> {
                shift = -ballShift
                genX = 520
            }
        }


        when (lifecycle) {
            LIFESTAGE.CHARGE -> {
                animator.paint(
                    painter.left + shift + genX * (12 - ballSize) / 12,
                    painter.top + (y-50) * (12 - ballSize) / 12,
                    painter.width * ballSize / ballMax,
                    painter.height * ballSize / ballMax, g
                )
            }
            LIFESTAGE.ROLL -> {
                animator.paint(
                    painter.left+shift,
                    painter.top,
                    painter.width,
                    painter.height, g
                )
            }
            LIFESTAGE.BOOM -> {
                animator.paint(
                    painter.left+shift,
                    painter.top,
                    painter.width,
                    painter.height, g
                )
            }

        }

    }

    override fun update(timePassed: Long) {
        var c = Coordinate.vectorToXY(flyVector)
        animator.update(timePassed)
        when (lifecycle) {
            LIFESTAGE.CHARGE -> {
                if (!chargeReady) {
                    if (delay.count()) {
                        animator.setState(BulletsAnimator.State.SKILLROLL)
                        chargeReady = true
                        delay.stop()
                        delay.countLimit = LIFESTAGE.CHARGE.keepTime / 2
                        delay.play()
                    }
                } else {
                    if(shakeLevel<2){
                        shakeLevel++
                    }
                    if (delay.count()) {
                        charge()
                    }
                }
            }
            LIFESTAGE.ROLL -> {
                if(shakeLevel<4){
                    shakeLevel++
                }
                var c = Coordinate.vectorToXY(flyVector)

                translate(x= c.x + camareFix )

                if ( (c.x >0 && painter.right >= Global.SCREEN_X+100) || (c.x<0 && painter.left <= -50)) {
                    boomsmoke()
                }
                if (delay.count()) {
                    boomsmoke()
                }
            }
            LIFESTAGE.BOOM -> {
                if(shakeLevel>0){
                    shakeLevel--
                }
                var d: Int = -30
                if (c.x > 0) {
                    d = 30
                }
                collider.translate(dx = d)
                if ( (c.x >0 && collider.right >= Global.SCREEN_X) || (c.x<0 && collider.left <= 0)) {
                    collider.translate(dx = 10000)
                }

                if (delay.count()) {
                    lifecycle = LIFESTAGE.REMOVE
                    flyVector.distance = 10000.0
                    var c = Coordinate.vectorToXY(flyVector)
                    translate(c.x, c.y) //超出飛行距離時，傳送到畫面外
                }
            }

        }

    }

}