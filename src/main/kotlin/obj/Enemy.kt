package obj

import core.controllers.ResController
import core.utils.Delay
import mathUtils.Coordinate
import mathUtils.Vector
import obj.Armory.*
import obj.bullet.*
import obj.utils.NinjaAnimator
import obj.utils.NinjaAnimator.Ninja
import utils.Global
import utils.Global.IS_DEBUG
import utils.Path
import java.awt.Graphics
import java.awt.Point
import kotlin.random.Random

open class Enemy(
    x: Int, y: Int, random: Int,
    width: Int = 32, height: Int = 32,
    x2: Int = x, y2: Int = y, width2: Int = 64, height2: Int = 64
) : MainRole(x, y, width, height, x2, y2, width2, height2) {
//    private var state = NinjaAnimator.State.IDLE
    private var ninjas = arrayOf(
        Ninja.NINJA1,
        Ninja.NINJA2,
        Ninja.NINJA3,
        Ninja.NINJA4,
        Ninja.NINJA5,
        Ninja.NINJA7
    )
    private var animatorRandom = NinjaAnimator(ninjas[Random.nextInt(0, random)], state)
    private var cloud = ResController.instance.image(Path.Imgs.Objs.cloud)
    private var moved: Boolean = true
    private var delay = Delay(180).apply {
        play()
    }
    private var moveDelay = Delay(360).apply {
        loop()
    }

    var shotCount: Int = 0

    init {
        state = NinjaAnimator.State.IDLE
        weapons.clear()//清空
        weapons.add(SingleShot(-1, -1, Shuriken(-1, -1, 0.0, 0.0),true))
        weapons.add(BurstShot(-1, -1, Shuriken(-1, -1, 0.0, 0.0),true))
        weapons.add(ShotGun(-1, -1, Shuriken(-1, -1, 0.0, 0.0),true))
        weapons.add(Sniper(-1, -1, Shuriken(-1, -1, 0.0, 0.0),true))
        setWeapon(Random.nextInt(0, random / 2 + 1)) //隨機選擇武器
    }

    fun changeCharactor(charactorNum: Int, weaponNum: Int) {
        if (charactorNum < ninjas.size) {
            animatorRandom.setNinja(ninjas[charactorNum])
        }
        setWeapon(weaponNum)
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
            painter.height, g
        )

        if (skyMode) {
            g.drawImage(
                cloud,
                this.collider.left - Global.MapObject * 3 / 5,
                this.collider.bottom - Global.MapObject * 2 / 5,
                this.painter.width * 15 / 10,
                this.painter.height,
                null
            )
        }
    }

    override fun update(timePassed: Long) {
        when (lifecycle) {
            LIFESTAGE.ALIVE -> {
                operateWeapon.update(timePassed)
                operateWeapon.releaseWeapon()
                if (hp <= 0) {
                    animatorRandom.setState(NinjaAnimator.State.DEADSMOKE)
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

    open fun shot(x: Int, y: Int): MutableList<BulletComm>? {
        if (lifecycle != LIFESTAGE.ALIVE) {
            return null
        } //只在存活階段可行動
        if (operateWeapon.ammo <= 0 && operateWeapon.wpnState == Weapon.WPNSTATE.STANDBY) {
            operateWeapon.reload()
        }
        if (operateWeapon.ammo <= 0) {
            return null
        }
        animatorRandom.setState(NinjaAnimator.State.THROWRUN)
        val tmp = Vector.xyToVector(
            Coordinate(
                (x - this.painter.centerX).toDouble(),
                (y - this.painter.centerY).toDouble()
            )
        )
        return (
                shot(//mainRole的shot
                    this.painter.centerX,
                    this.painter.centerY,
                    tmp.degree
                )
                )
    }


    open fun move(x: Int) {
        if (lifecycle != LIFESTAGE.ALIVE) {
            return
        } //只在存活階段可行動
        animatorRandom.setState(NinjaAnimator.State.IDLE)
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
}
