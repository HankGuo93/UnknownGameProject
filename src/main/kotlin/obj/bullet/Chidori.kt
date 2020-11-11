package obj.bullet

import audio.AudioResourceController
import mathUtils.Coordinate
import mathUtils.Vector
import obj.MainRole
import obj.utils.BulletsAnimator
import utils.Global
import utils.Path
import java.awt.Graphics
import kotlin.math.roundToInt

class Chidori(x: Int, y: Int, flyAngle: Double, range: Double, damage: Int = 10) : BulletComm(
    x, y, flyAngle, range, damage, 80, 80, 200, 170
) {
    override var picFixAngle: Int = 45
    override var camareFix: Double = 0.0
    override var animator: BulletsAnimator =
        BulletsAnimator(BulletsAnimator.State.BOOMBFLY, flyAngle.toInt() + picFixAngle)

    override var bulletSpeed: Double = 20.0
    private var chidoriReadyFlag: Boolean = false
    override var damage: Int = 500
        get() {
            if (lifecycle == LIFESTAGE.BOOM) {
                return field
            }
            return 0
        }

    override fun clone(fx: Int, fy: Int, fa: Double, r: Double, d: Double): BulletComm {
        return Chidori(fx, fy, fa, r, d.roundToInt())
    }

    init {
        powerCount = 0
        powerTarget = 45

        criticalDamage = 1000

        delay.stop()
        delay.countLimit = powerTarget //集氣 45frame要0.75秒
        delay.play()
        chidoriReadyFlag = false
    }

    override fun boomSmoke(): BulletComm? {//碰到敵人時
        return null
    }

    fun checkChidori(): Int {
        //停止集氣音效
        AudioResourceController.getInstance()
            .stop(Path.Sounds.Bullets.CHIDORI2)
        when (lifecycle) {
            LIFESTAGE.FLY -> {
                return if (chidoriReadyFlag) {

                    //播放千鳥音效
                    AudioResourceController.getInstance()
                        .play(Path.Sounds.Bullets.CHIDORI1)

                    lifecycle = LIFESTAGE.BOOM
                    delay.stop()
                    delay.countLimit = 24 //12frame 0.2second 20*12 = 移動240
                    delay.play()
                    bulletSpeed.roundToInt()
                } else {
                    chidoriReadyFlag = false
                    delay.stop()
                    var c = Coordinate.vectorToXY(Vector(10000.0, 90.0))
                    translate(c.x, c.y) //超出飛行距離時，傳送到畫面外
                    lifecycle = LIFESTAGE.REMOVE
                    -1   //未集氣完成 取消招式
                }
            }
            LIFESTAGE.BOOM -> {
                return bulletSpeed.roundToInt() //提供人物移動速度 與子彈相同
            }
            else -> {
                chidoriReadyFlag = false
                var c = Coordinate.vectorToXY(Vector(10000.0, 90.0))
                translate(c.x, c.y) //超出飛行距離時，傳送到畫面外
                return 0 //已完成 不加入千鳥移動狀態
            }
        }
        return 0
    }

    fun follow(ac: MainRole) {
        var d = 20
        if (ac.dir == Global.Direction.LEFT) {
            d = -20
        }

        translate(ac.collider.centerX - collider.centerX + d, ac.collider.centerY - collider.centerY) //跟隨角色的更新
    }

    override fun paintComponent(g: Graphics) {

        if (powerCount < powerTarget * 1 / 2) {
            powerCount++
        }

        when (lifecycle) {
            LIFESTAGE.FLY -> {
                animator.paint(
                    painter.centerX - (painter.centerX - painter.left) * powerCount / powerTarget,
                    painter.centerY - (painter.centerY - painter.top) * powerCount / powerTarget,
                    painter.width * powerCount / powerTarget,
                    painter.height * powerCount / powerTarget, g
                )
            }
            LIFESTAGE.BOOM -> {
                animator.paint(
                    painter.left,
                    painter.top,
                    painter.width,
                    painter.height, g
                )
            }
        }
    }


    override fun update(timePassed: Long) {

        when (lifecycle) {
            LIFESTAGE.FLY -> {

                if (!chidoriReadyFlag && delay.count()) {//計時
                    animator.setState(BulletsAnimator.State.BOOMBEND)
                    chidoriReadyFlag = true
                }
            }
            LIFESTAGE.BOOM -> {
                if (delay.count()) {
                    lifecycle = LIFESTAGE.REMOVE
                    var c = Coordinate.vectorToXY(Vector(10000.0, 90.0))
                    translate(c.x, c.y) //超出飛行距離時，傳送到畫面外
                }
            }
            LIFESTAGE.REMOVE -> {
                AudioResourceController.getInstance()
                    .stop(Path.Sounds.Bullets.CHIDORI2)//結束時 強制停止音效
            }
        }
        animator.update(timePassed)
    }

}