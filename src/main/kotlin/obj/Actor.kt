package obj

import core.GameKernel
import obj.Armory.*
import obj.bullet.*
import obj.utils.GameObject
import obj.utils.NinjaAnimator
import obj.Armory.Weapon
import utils.Global

import java.awt.Graphics
import java.awt.Point
import java.awt.event.KeyEvent

class Actor(x: Int, y: Int) : GameObject(x, y, 64, 64, x, y + 13, 77, 90) {


    var hpMax: Int = 100
    var HP: Int = 100

    var attack: Int = 10
    var defense: Int = 100

    var dir = Global.Direction.RIGHT
        private set
    private var state = NinjaAnimator.State.IDLE
    private var ninja = NinjaAnimator.Ninja.NINJA6
    private var ninjaCount = 6
    private var animator = NinjaAnimator(ninja, state)

    //    private var marioAnimator = MarioAnimator(MarioAnimator.State.IDLE)
    private var weapons: MutableList<Weapon> = mutableListOf()

    private var marioFlag = true

    init {
        if (!marioFlag) {
            weapons.clear()
            weapons.add(ShurikenBag(-1, -1, Rocket(-1, -1, 0.0, 0.0)))
            weapons.add(SingleShot(-1, -1, SingleB(-1, -1, 0.0, 0.0)))
            weapons.add(BurstShot(-1, -1, BurstB(-1, -1, 0.0, 0.0)))
            weapons.add(Sniper(-1, -1, SniperB(-1, -1, 0.0, 0.0)))
            weapons.add(ShotGun(-1, -1, ShotB(-1, -1, 0.0, 0.0)))
        } else {
            weapons.clear()
            weapons.add(ShurikenBag(-1, -1, Hammer(-1, -1, 0.0, 0.0)))
            weapons.add(SingleShot(-1, -1, Hammer(-1, -1, 0.0, 0.0)))
            weapons.add(BurstShot(-1, -1, Hammer(-1, -1, 0.0, 0.0)))
            weapons.add(Sniper(-1, -1, Hammer(-1, -1, 0.0, 0.0)))
            weapons.add(ShotGun(-1, -1, Hammer(-1, -1, 0.0, 0.0)))
        }

        if (!marioFlag) {
            animator.setState(NinjaAnimator.State.IDLE)
        } else {
            animator.setState(NinjaAnimator.State.MIDLE)
        }
    }

    var operateWeapon: Weapon = weapons[0]
        private set


    override fun paintComponent(g: Graphics) {
        animator.paint(
            dir,
            painter.left,
            painter.top,
            painter.width,
            painter.height, g
        )
    }

    fun shot(x: Int, y: Int, v: Double): MutableList<BulletComm>? {
        var arr = operateWeapon.shot(x, y, v, attack)
        if (operateWeapon.wpnState == Weapon.WPNSTATE.SHOTCD) {
            if (!marioFlag) {
                animator.setState(NinjaAnimator.State.THROWRUN)
            } else {
                animator.setState(NinjaAnimator.State.MTHROWRUN)
            }
        }
        return arr
    }

    fun reload() {
        operateWeapon.reload()
    }

    private fun mm(p: Point) {
        if (p.x > painter.centerX) {
            dir = Global.Direction.RIGHT
        } else {
            dir = Global.Direction.LEFT
        }
    }

    private fun kp(keyCode: Int) {
        when (keyCode) {
            KeyEvent.VK_1 -> {
                operateWeapon = weapons[0]
            }
            KeyEvent.VK_2 -> {
                operateWeapon = weapons[1]
            }
            KeyEvent.VK_3 -> {
                operateWeapon = weapons[2]
            }
            KeyEvent.VK_4 -> {
                operateWeapon = weapons[3]
            }
            KeyEvent.VK_5 -> {
                operateWeapon = weapons[4]
            }

            KeyEvent.VK_W -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.JUMP)
                } else {

                }
            }
            KeyEvent.VK_J -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.THROWRUN)
                } else {
                    animator.setState(NinjaAnimator.State.MTHROWRUN)
                }
            }
            KeyEvent.VK_E -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.HIGHJUMP)
                } else {
                    animator.setState(NinjaAnimator.State.MRUN)
                }
            }
            KeyEvent.VK_Q -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.THROWJUMP)
                } else {
                    animator.setState(NinjaAnimator.State.MRUN)
                }
            }

            KeyEvent.VK_P -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.DEADSMOKE)
                } else {

                }
            }
            KeyEvent.VK_O -> {
                ninjaCount = ++ninjaCount % NinjaAnimator.Ninja.values().size
                ninja = NinjaAnimator.Ninja.values()[ninjaCount]
                animator = NinjaAnimator(ninja, state)

                if (!marioFlag) {
                    ninjaCount = ++ninjaCount % NinjaAnimator.Ninja.values().size
                    ninja = NinjaAnimator.Ninja.values()[ninjaCount]
                    animator = NinjaAnimator(ninja, state)
                } else {

                }
            }
        }
    }

    private fun kkp(keyCode: Int) {
        when (keyCode) {

            KeyEvent.VK_A -> {
                translate(x = -4)
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.RUN)
                } else {
                    animator.setState(NinjaAnimator.State.MRUN)
                }

            }
            KeyEvent.VK_S -> {
                if (marioFlag) {
                    animator.setState(NinjaAnimator.State.MSHIELD)
                }

            }
            KeyEvent.VK_D -> {
                translate(x = 4)
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.RUN)
                } else {
                    animator.setState(NinjaAnimator.State.MRUN)
                }
            }
        }
    }

    private fun kr(keyCode: Int) {
        when (keyCode) {
            KeyEvent.VK_A -> {
                translate(x = -4)
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }

            }
            KeyEvent.VK_S -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }
            }
            KeyEvent.VK_D -> {
                translate(x = 4)
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }

            }
            KeyEvent.VK_W -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }
            }
            KeyEvent.VK_J -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }
            }
            KeyEvent.VK_E -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }
            }
            KeyEvent.VK_Q -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }
            }

            KeyEvent.VK_P -> {
                if (!marioFlag) {
                    animator.setState(NinjaAnimator.State.IDLE)
                } else {
                    animator.setState(NinjaAnimator.State.MIDLE)
                }
            }
        }
    }


    override fun update(timePassed: Long) {


        if (!marioFlag) {
            animator.update(timePassed)
        } else {
            animator.update(timePassed)
        }

        weapons.forEach { a -> a.update(timePassed) }
//        operateWeapon.update(timePassed)
    }

    override val input: ((GameKernel.Input.Event) -> Unit)? = { e ->
        run {
            when (e) {
                is GameKernel.Input.Event.MouseReleased -> {
                    if (!marioFlag) {
                        animator.setState(NinjaAnimator.State.IDLE)
                    } else {
                        animator.setState(NinjaAnimator.State.MIDLE)
                    }
                }
                is GameKernel.Input.Event.MouseMoved -> mm(e.data.point)
                is GameKernel.Input.Event.KeyPressed -> kp(e.data.keyCode)
                is GameKernel.Input.Event.KeyKeepPressed -> kkp(e.data.keyCode)

                is GameKernel.Input.Event.KeyReleased -> kr(e.data.keyCode)

                else -> {
                }
            }
        }
    }
}