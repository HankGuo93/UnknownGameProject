package obj.Armory

import core.utils.Delay
import obj.bullet.BulletComm
import obj.utils.GameObject
import utils.Global
import java.awt.Color
import java.awt.Graphics
import java.awt.Image

abstract class Weapon(x: Int, y: Int,
                      var allwaysFull: Boolean,
                      protected val bullet: BulletComm,
                      private val autoShot : Boolean
) : GameObject(x, y, Global.SCREEN_X / 15, Global.SCREEN_Y / 7) {

    var actImg: Image?
        protected set

    var unActImg: Image?
        protected set

    var ammo: Int = 0
        protected set

    var ammoMax: Int = 0
        protected set

    var ammoMagazine: Int = 0

    protected var damageRate : Double = 1.0 //初始無增傷倍率

    protected var delay: Delay

    protected var shotCD: Int

    protected var reloadCD: Int

    var wpnState = WPNSTATE.STANDBY
        protected set

    enum class WPNSTATE { STANDBY, SHOTCD, RELOADCD }

    protected var shotMaxRange:Double

    init {
        shotMaxRange =0.0

        shotCD = 0
        reloadCD = 0
        actImg = null
        unActImg = null
        delay = Delay(0)
        delay.stop()
    }

    protected var shotKeepFlag = false

    protected abstract fun shotAction(x: Int, y: Int, v: Double,baseDemage:Int): MutableList<BulletComm>

    fun shot(x: Int, y: Int, v: Double,baseDemage:Int,range:Double=shotMaxRange): MutableList<BulletComm> {
        shotMaxRange = range
        var b: MutableList<BulletComm> = mutableListOf()
        if (!shotKeepFlag || autoShot) {
            shotKeepFlag = true
            if (ammo > 0 && wpnState == WPNSTATE.STANDBY) {
                ammo--
                b = shotAction(x, y, v,baseDemage)
                wpnState = WPNSTATE.SHOTCD
                delay.stop()
                delay.countLimit = shotCD
                delay.play()
            }
        }
        return b
    }

    fun releaseWeapon(){
        shotKeepFlag = false
    }

    fun reload() {
        if (ammoMagazine > 0 && ammo < ammoMax && wpnState != WPNSTATE.RELOADCD) { //彈匣已使用 且 仍有庫存彈夾 開始Reload
            wpnState = WPNSTATE.RELOADCD
            delay.stop()
            delay.countLimit = reloadCD
            delay.play()
        }
    }

    fun paintLine(x: Int, y:Int, xSelf: Int, ySelf:Int, color: Color, g: Graphics){
        var tmp: Int = ySelf
        g.color = Color.RED
        if (tmp == y){
            tmp+= 1;
        }
        if (tmp < y) {
            g.drawLine(x, y, x - Global.WINDOW_WIDTH * (xSelf - x) / (tmp - y), y - Global.WINDOW_WIDTH)
        } else {
            g.drawLine(x, y, x + Global.WINDOW_WIDTH * (xSelf - x) / (tmp - y), y + Global.WINDOW_WIDTH)
        }
    }

    override fun paintComponent(g: Graphics) {

    }

    override fun update(timePassed: Long) {
        if (delay.count()) {
            when (wpnState) {
                WPNSTATE.RELOADCD -> {
                    if (!allwaysFull) ammoMagazine--
                    ammo = ammoMax
                }
            }
            wpnState = WPNSTATE.STANDBY
        }

    }
}