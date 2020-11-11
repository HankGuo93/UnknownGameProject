package obj

import audio.AudioResourceController
import core.GameKernel
import core.controllers.ResController
import core.utils.Delay
import mathUtils.Coordinate
import obj.Armory.*
import obj.Armory.Weapon
import obj.bullet.*
import obj.utils.*
import obj.utils.NinjaAnimator
import utils.Global
import utils.Global.IS_DEBUG
import utils.Path
import java.awt.*
import java.awt.event.KeyEvent
import java.io.File
import java.awt.event.MouseEvent
import java.io.InputStream

open class MainRole(
    x: Int, y: Int, width: Int = 32, height: Int = 32,
    x2: Int = x, y2: Int = y + 5, width2: Int = 64, height2: Int = 64
) : GameObject(x, y, width, height, x2, y2, width2, height2) {
    private var book: Image = ResController.instance.image(Path.Imgs.Objs.ult)
    private var border: Image = ResController.instance.image(Path.Imgs.Objs.border2)
    private var file: InputStream? = javaClass.getResourceAsStream("/font/pixelFJ8pt1__.TTF")
    private var font: Font = Font.createFont(Font.TRUETYPE_FONT, file)
    open var ultLoading: Int = 0
    open var ultBoolean: Boolean = false

    open var hamrioFlag = false
        set(value){
            if (!value) {
                animator.setState(NinjaAnimator.State.IDLE)
            } else {
                animator.setState(NinjaAnimator.State.MIDLE)
            }
            field = value
        }
    
    enum class LIFESTAGE(val keepTime: Int) {
        ALIVE(0),
        DEADSMOKE(24),
        REMOVE(0)
    }

    protected var lifeDelay = Delay(0)
    open var lifecycle = LIFESTAGE.ALIVE
        protected set
    var name: String = ""
        set(String) {
            field = String
        }
    var moveType: MoveType = MoveType.NULL
    var front: Boolean = true
    var skyMode: Boolean = false
    var dir = Global.Direction.RIGHT
        protected set

    var attack: Int = 25

    var defense: Int = 100
        set(newD) {
            field = if (newD < 60) {
                60
            } else {
                newD
            }
        }

    var hpMax: Int = 100
        set(max) {
            var tmp = (max - field)
            field = max
            hp += tmp
        }
    var hp: Int = 100
        set(newHp) = when {
            newHp > hpMax -> {
                field = hpMax
            }
            newHp >= field -> {
                field = newHp
            }
            else -> {
                if (hamrioFlag && isShield) {

                } else {
                    field += (newHp - field) * defense / 100

                    if (hamrioFlag && field <= 0) {
                        hp = hpMax
                        hamrioFlag = false
                    }else{}
                }
            }
        }

    private var chidori: Chidori? = null
    private var chidoriMoveFlag: Boolean = false

    var bomb: Int = 3
    var shield: Shield? = null
    protected var state = NinjaAnimator.State.IDLE
    protected var ninja = NinjaAnimator.Ninja.NINJA6
        set(value) {
            field = value
        }
    open var animator = NinjaAnimator(ninja, state)
    private val supplement = ResController.instance.image(Path.Imgs.Actors.supplement)
    private var HPline = Global.MapObject * 2
    var weapons: MutableList<Weapon> = mutableListOf()
    private var delay: Delay = Delay(30).apply { loop() }
    private var paintBoolean: Boolean = false;
    var isShield: Boolean = false;
    private var facingFront: Boolean = true;


    init {
        weapons.add(SingleShot(-1, -1, SingleB(-1, -1, 0.0, 0.0)))
        weapons.add(BurstShot(-1, -1, BurstB(-1, -1, 0.0, 0.0)))
        weapons.add(ShotGun(-1, -1, ShotB(-1, -1, 0.0, 0.0)))
        weapons.add(Sniper(-1, -1, SniperB(-1, -1, 0.0, 0.0)))
        weapons.add(MRL(-1, -1, Rocket(-1, -1, 0.0, 0.0)))
        weapons.add(Chakura(-1, -1, Chidori(-1, -1, 0.0, 0.0)))
        weapons.add(ShurikenBag(-1, -1, Shuriken(-1, -1, 0.0, 0.0)))

    }

    var operateWeapon: Weapon = weapons[0]
        protected set

    private var hamWPN = BurstShot(-1, -1, Hammer(-1, -1, 0.0, 0.0), true)

    fun getWeapon(int: Int): Weapon {
        return weapons[int]
    }

    fun setWeapon(int: Int) {
        if (int >= weapons.size) {
            return
        }
        operateWeapon = weapons[int]
    }

    enum class MoveType(val x: Int, val y: Int) {
        ONE(1, -1),
        TWO(-1, -1),
        THREE(-1, 1),
        FOUR(1, 1),
        NULL(Global.ROLE_SPEED, 0);

        fun x(c: Coordinate): Double {
            return c.x * this.x
        }

        fun y(c: Coordinate): Double {
            return c.y * this.y
        }
    }

    fun shot(x: Int, y: Int, v: Double): MutableList<BulletComm>? {
        if (lifecycle != LIFESTAGE.ALIVE) {
            return null
        } //只在存活階段可行動

        if (hamrioFlag) {
            operateWeapon = hamWPN
        }

        if (operateWeapon.ammo == 0) {
            return null
        }

        if (operateWeapon.wpnState == Weapon.WPNSTATE.STANDBY) {
            if (!hamrioFlag) {
                animator.setState(NinjaAnimator.State.THROWRUN)
            } else {
                animator.setState(NinjaAnimator.State.MTHROWRUN)
            }
        }

        var b = operateWeapon.shot(x, y, v, attack)

        if (!b.isNullOrEmpty() && b[0] is Chidori) { //有產生子彈 而且是千鳥
            chidori = b[0] as Chidori //操作中千鳥
            chidoriMoveFlag = false
        }
        return b
    }

    fun reload() {
        operateWeapon.reload()
    }

    override fun paintComponent(g: Graphics) {
        if (lifecycle == LIFESTAGE.REMOVE) {
            return
        }
        if (lifecycle == LIFESTAGE.DEADSMOKE) {
            animator.paint(
                dir,
                painter.left,
                painter.top,
                painter.width,
                painter.height, g
            )
            return
        }

        g.color = Color.red.darker()
        g.fillRect(painter.left, painter.top - Global.MapObject / 3, HPline * hp / 100, Global.MapObject / 5)

        if (this.operateWeapon.ammo <= 0) {
            if (delay.count()) {
                paintBoolean = !paintBoolean
            }
            if (paintBoolean) {
                g.drawImage(
                    supplement, painter.centerX - Global.MapObject * 2, painter.top - Global.MapObject * 10 / 5,
                    Global.MapObject * 4, Global.MapObject, null
                )
            }
        }
        animator.paint(
            dir,
            painter.left,
            painter.top,
            painter.width,
            painter.height, g
        )
        if (isShield) {
            if (!hamrioFlag) {
                if (shield == null) {
                    shield = Shield(this.collider.right + Global.MapObject * 5 / 10, this.collider.top)
                }
                if (this.facingFront && shield!!.collider.centerX != this.collider.right + Global.MapObject * 5 / 10) {
                    shield!!.translate(x = (this.collider.right + Global.MapObject * 5 / 10) - (shield!!.collider.centerX))
                } else if (!this.facingFront && shield!!.collider.centerX != this.collider.left - Global.MapObject * 5 / 10) {
                    shield!!.translate(x = (this.collider.left - Global.MapObject * 5 / 10) - (shield!!.collider.centerX))
                }
                shield?.paint(g)
            }
        }
        if (name != "") {
            g.font = Font("Utopia", 1, 20)
            g.color = Color.yellow.darker()
            g.drawString("$name", this.collider.centerX - Global.MapObject, this.painter.top - Global.MapObject)
        }
        g.font = font.deriveFont(1, 20.0F)
        g.color = Color.white
        g.drawString("HP             ${this.hp}", 20, Global.SCREEN_Y * 6 / 20)
        g.drawString("ATTACK       ${this.attack}", 20, Global.SCREEN_Y * 7 / 20)
        g.drawString("DEFENCE     ${100 - this.defense}%", 20, Global.SCREEN_Y * 8 / 20)
        g.color = Color(0.705F, 0.0026F, 0.0352F, 1.0F)
//        g.drawString("ULT", 20, Global.SCREEN_Y*9/20)
        g.drawImage(border, 14, Global.SCREEN_Y * 84 / 200, Global.SCREEN_X / 6, Global.SCREEN_Y / 15, null)
        g.drawImage(book, 20, Global.SCREEN_Y * 83 / 200, 64, 64, null)
        g.color = Color.white
        g.fillRect(100, Global.SCREEN_Y * 88 / 200, Global.SCREEN_X / 10, Global.SCREEN_Y / 90 * 2)
        g.color = Color.BLACK
        g.fillRect(100 + 3, Global.SCREEN_Y * 88 / 200 + 3, Global.SCREEN_X / 10 - 6, Global.SCREEN_Y / 90 * 2 - 6)
//        g.color = Color(0.153F, 0.611F, 0.235F, 1.0F)
        g.color = Color.BLUE
        g.fillRect(
            100 + 3,
            Global.SCREEN_Y * 88 / 200 + 3,
            (Global.SCREEN_X / 10 - 6) * ultLoading / 100,
            Global.SCREEN_Y / 90 * 2 - 6
        )
        if (ultLoading >= 100) {
            g.color = Color.CYAN
//            g.color = Color(0.705F, 0.0026F, 0.0352F, 1.0F)
            g.drawString("Ready!! [PRESS SPACE]", 140, Global.SCREEN_Y * 88 / 200)
        } else {
//            g.color = Color.CYAN
//            g.drawString("$ultLoading%", 80, Global.SCREEN_Y * 90 / 200)
        }
    }

    fun mm(p: Point) {
        if (p.x > painter.centerX) {
            dir = Global.Direction.RIGHT
        } else {
            dir = Global.Direction.LEFT
        }
    }

    override fun update(timePassed: Long) {
        ultBoolean = ultLoading >= 100
        when (lifecycle) {
            LIFESTAGE.ALIVE -> {

                //千鳥衝刺


                if (chidori == null) {
                    chidoriMoveFlag = false
                } else if (chidori!!.outOfScreen) {
                    chidori = null
                    chidoriMoveFlag = false
                    AudioResourceController.getInstance()
                        .stop(Path.Sounds.Bullets.CHIDORI1)
                    AudioResourceController.getInstance()
                        .stop(Path.Sounds.Bullets.CHIDORI2)
                }

                if (chidoriMoveFlag) { //進行衝刺移動
                    chidori?.checkChidori().let {
                        if (it == 0 || it == -1) {
                            chidoriMoveFlag = false
                            chidori = null
                        } else {
                            if (it != null && dir == Global.Direction.LEFT && (painter.left > 0)) {
                                translate(x = -it)
                            } else if (it != null && dir == Global.Direction.RIGHT && (painter.right < Global.SCREEN_X)) {
                                translate(x = it)
                            }
                        }
                    }
                }

                //千鳥跟隨主角
                if (chidori != null) {
                    chidori?.follow(this)
                }


                operateWeapon.update(timePassed)
                if (IS_DEBUG) {
                    if (hp < 30)
                        hp = hpMax
                }
                if (hp <= 0) {
                    animator.setState(NinjaAnimator.State.DEADSMOKE)

                    lifeDelay.stop()
                    lifeDelay.countLimit = LIFESTAGE.DEADSMOKE.keepTime
                    lifeDelay.play()
                    lifecycle = LIFESTAGE.DEADSMOKE
                }
            }
            LIFESTAGE.DEADSMOKE -> {
                if (lifeDelay.count()) {
                    lifecycle = LIFESTAGE.REMOVE
                }
            }
            LIFESTAGE.REMOVE -> {
                return
            }
        }
        animator.update(timePassed)
        shield?.update(timePassed)
    }

    override val input: ((GameKernel.Input.Event) -> Unit)? = { e ->
        run {
            when (e) {
                is GameKernel.Input.Event.MouseDragged -> {
                    mm(e.data.point)
                }
                is GameKernel.Input.Event.MouseMoved -> {
                    mm(e.data.point)
                    this.facingFront = e.data.x > this.collider.centerX
                }
                is GameKernel.Input.Event.KeyKeepPressed ->
                    when (e.data.keyCode) {
                        KeyEvent.VK_A -> {
                            if (!isShield) {
                                if (!hamrioFlag) {
                                    animator.setState(NinjaAnimator.State.RUN)
                                } else {
                                    animator.setState(NinjaAnimator.State.MRUN)
                                }
                                front = false
                                if (painter.left > 0) {
                                    translate(-Global.ROLE_SPEED, 0)
                                }
                            }
                        }
                        KeyEvent.VK_D -> {
                            if (!isShield) {
                                if (!hamrioFlag) {
                                    animator.setState(NinjaAnimator.State.RUN)
                                } else {
                                    animator.setState(NinjaAnimator.State.MRUN)
                                }
                                front = true
                                if (painter.right < Global.SCREEN_X) {
                                    translate(Global.ROLE_SPEED, 0)
                                }
                            }
                        }
                        KeyEvent.VK_S -> {
                            if (hamrioFlag) {
                                animator.setState(NinjaAnimator.State.MSHIELD)
                            }
                            if (chidori == null) { //使用千鳥狀態時 無法啟用盾牌
                                isShield = true
                            }
                        }
                    }

                is GameKernel.Input.Event.MouseReleased -> {
                    if (!hamrioFlag) {
                        animator.setState(NinjaAnimator.State.IDLE)
                    } else {
                        animator.setState(NinjaAnimator.State.MIDLE)
                    }
                    if (!hamrioFlag && e.data.button == MouseEvent.BUTTON1) {//左鍵釋放
                        //檢查千鳥集氣狀態
                        if (chidori != null && !chidoriMoveFlag) {
                            chidori!!.checkChidori().apply {
                                chidoriMoveFlag = this > 0 //確認集氣是否已滿
                                if (this == 0 || this == -1) { //未滿 或移除狀態 取消
                                    chidoriMoveFlag = false
                                    chidori = null
                                } else { //已集滿 傳回移動數值
                                    if (dir == Global.Direction.LEFT && (painter.left > 0)) {
                                        translate(x = -this)
                                    } else if (dir == Global.Direction.RIGHT && (painter.right < Global.SCREEN_X)) {
                                        translate(x = this)
                                    }
                                }
                            }
                        }
                    }
                }

                is GameKernel.Input.Event.MouseDragged -> {
                    mm(e.data.point)
                }
                is GameKernel.Input.Event.KeyReleased -> {

                    when (e.data.keyCode) {
                        KeyEvent.VK_A -> {
                            if (!hamrioFlag) {
                                animator.setState(NinjaAnimator.State.IDLE)
                            } else {
                                animator.setState(NinjaAnimator.State.MIDLE)
                            }

                        }
                        KeyEvent.VK_D -> {
                            if (!hamrioFlag) {
                                animator.setState(NinjaAnimator.State.IDLE)
                            } else {
                                animator.setState(NinjaAnimator.State.MIDLE)
                            }
                        }
                        KeyEvent.VK_S -> {
                            if (hamrioFlag) {
                                animator.setState(NinjaAnimator.State.MIDLE)
                            }
                            isShield = false
                            this.shield = null
                        }
                        KeyEvent.VK_A -> {
                            if (!hamrioFlag) {
                                animator.setState(NinjaAnimator.State.IDLE)
                            } else {
                                animator.setState(NinjaAnimator.State.MIDLE)
                            }
                        }
                        KeyEvent.VK_D -> {
                            if (!hamrioFlag) {
                                animator.setState(NinjaAnimator.State.IDLE)
                            } else {
                                animator.setState(NinjaAnimator.State.MIDLE)
                            }
                        }
                    }
                }

            }
        }
    }
}