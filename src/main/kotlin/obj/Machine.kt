package obj

import core.controllers.ResController
import core.utils.Delay
import mathUtils.Coordinate
import mathUtils.Vector
import obj.Armory.*
import obj.bullet.*
import obj.utils.MachineAnimator
import obj.utils.MachineAnimator.Machine
import obj.utils.NinjaAnimator
import utils.Global
import utils.Global.IS_DEBUG
import utils.Path
import java.awt.Graphics
import java.awt.Point
import kotlin.random.Random

class Machine(
    x: Int, y: Int,
    width: Int = 64, height: Int = 64,
    x2: Int = x, y2: Int = y, width2: Int = 128, height2: Int = 128
) : Enemy(x, y, 1, width, height, x2, y2, width2, height2) {
    private var delay = Delay(180).apply {
        play()
    }
    private var moveDelay = Delay(360).apply {
        loop()
    }
    private var moved: Boolean = true
    private var mstate = MachineAnimator.State.IDLE
    private var machine = arrayOf(
        Machine.Machine1,
        Machine.Machine2,
    )
    private var animatorDelay = Delay(12)
    private var shotAnimatorFlag = false
    private var animatorRandom = MachineAnimator(machine[Random.nextInt(0, 2)], mstate)

    init {
        hpMax = 200
        hp=hpMax //設定機器人血量200
        mstate = MachineAnimator.State.IDLE
        weapons.clear()//清空
        weapons.add(SingleShot(-1, -1, MachineBullet(-1, -1, 0.0, 0.0)))
        setWeapon(0)
    }

    override fun shot(x: Int, y: Int): MutableList<BulletComm>? {
        if (lifecycle != LIFESTAGE.ALIVE) {
            return null
        } //只在存活階段可行動
        if (operateWeapon.ammo <= 0 && operateWeapon.wpnState == Weapon.WPNSTATE.STANDBY) {
            operateWeapon.reload()
        }
        if (operateWeapon.ammo <= 0){
            return null
        }
        animatorRandom.setState(MachineAnimator.State.ATTACK)
        animatorDelay.play()
        shotAnimatorFlag = true

        val tmp = Vector.xyToVector(
            Coordinate(
                (x - this.painter.centerX).toDouble(),
                (y - this.painter.centerY + this.painter.height / 3).toDouble()
            )
        )
        return (
                shot(
                    this.painter.centerX,
                    this.painter.centerY - this.painter.height / 3,
                    tmp.degree
                )
                )
    }

    override fun move(x: Int) {
        if (lifecycle != LIFESTAGE.ALIVE) {
            return
        } //只在存活階段可行動
        if (animatorDelay.count()) {
            shotAnimatorFlag = false
        }
        if (!shotAnimatorFlag) {
            animatorRandom.setState(MachineAnimator.State.IDLE)
        }

        if (moveDelay.count()) {
            moved = true;
        }
        if (this.collider.left > x && moved) {
            front = false
            if (this.collider.left - Global.ROLE_SPEED >= 0 && this.moveType != MoveType.NULL) {
                translate(moveType.x(Global.c), moveType.y(Global.c))
            } else if (this.collider.left - Global.ROLE_SPEED >= 0) {
                translate(-Global.ROLE_SPEED, 0)
            }
        } else if (this.collider.right < x && moved) {
            front = true
            if (this.moveType != MoveType.NULL) {
                translate(moveType.x(Global.c), moveType.y(Global.c))
            } else {
                translate(Global.ROLE_SPEED, 0)
            }
        } else {
            moved = false
        }
    }

    override fun paintComponent(g: Graphics) {
        if (lifecycle == LIFESTAGE.REMOVE) {
            return
        }
        animatorRandom.paint(
            dir,
            painter.left,
            painter.top,
            painter.width,
            painter.height * 2 / 3, g
        )
    }

    override fun update(timePassed: Long) {
        when (lifecycle) {
            LIFESTAGE.ALIVE -> {
                operateWeapon.update(timePassed)
                operateWeapon.releaseWeapon()
                if (hp <= 0) {
                    animatorRandom.setState(MachineAnimator.State.DEADSMOKE)
                    lifeDelay.stop()
                    lifeDelay.countLimit = LIFESTAGE.DEADSMOKE.keepTime
                    lifeDelay.play()
                    lifecycle = LIFESTAGE.DEADSMOKE
                    if (IS_DEBUG) {
                        println(lifecycle.name)
                    }
                }
            }
            LIFESTAGE.DEADSMOKE -> {
                if (lifeDelay.count()) {
                    lifecycle = LIFESTAGE.REMOVE
                    if (IS_DEBUG) {
                        println(lifecycle.name)
                    }
                }
            }
            LIFESTAGE.REMOVE -> {
                return
            }
        }
        animatorRandom.update(timePassed)
    }
}
