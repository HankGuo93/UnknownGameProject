package obj.utils

import core.controllers.ResController
import mathUtils.ImageUtils
import utils.Global
import utils.MarioPath
import utils.NinjaPath
import java.awt.Graphics
import java.awt.Image
import java.util.*
import kotlin.math.roundToInt

class BulletsAnimator(private var state: State, private val picFixAngle: Int) {

    enum class State(val arr: List<String>, var isLoop: Boolean = false, val speed: Int) {
        //weapon bullets:
        SHURIKEN(NinjaPath.Imgs.Objs.shuriken.roll.picArr, true, 10),
        MGUN_B(NinjaPath.Imgs.Objs.gun.machineGun_Arr, false, 10),
        GUN_B(NinjaPath.Imgs.Objs.gun.gun_Arr, false, 10),
        SHOT_B(NinjaPath.Imgs.Objs.gun.shotGun_Arr, false, 10),
        SNIPER_B(NinjaPath.Imgs.Objs.gun.sniper_Arr, false, 10),
        ROCKET(NinjaPath.Imgs.Objs.gun.rocket_Arr, false, 10),
        MACHINE_B(NinjaPath.Imgs.Objs.gun.BulletTripod, false, 10),

        //Boom
        BOOMBFLY(NinjaPath.Imgs.Objs.effect.boomEffect.picArr, true, 15),
        BOOMBEND(NinjaPath.Imgs.Objs.effect.boomEffect.endArr, true, 12),

        //Smoke effect:
        BOOMBSMOKE(NinjaPath.Imgs.Objs.effect.boomSmoke.picArr, true, 24),
        BULLETSMOKE(NinjaPath.Imgs.Objs.effect.bulletSmoke.picArr, false, 12),

        //Skill:
        SKILL(NinjaPath.Imgs.Objs.skill.skillStart_Arr, false, 60),
        SKILLSTART(NinjaPath.Imgs.Objs.skill.skillStart_Arr, false, 24),
        SKILLROLL(NinjaPath.Imgs.Objs.skill.skillRoll_Arr, true, 24),
        SKILLEND(NinjaPath.Imgs.Objs.skill.skillEnd_Arr, false, 24),

        //effect
        REFUND(NinjaPath.Imgs.Objs.effect.REFUND, false, 12),

        //hammer
        HAMMER( MarioPath.Imgs.Actors.hamrio.hammer.pic_Arr, true,24)

    }

    private var img: Image? = null
    private val delay = core.utils.Delay(0)
    private var count = 0
    private var dir:Global.Direction = Global.Direction.RIGHT

    init {
        setState(state)
    }

    fun setShotAngle(degree: Int) {

    }

    fun setState(state: State) {
        if (this.state != state) {
            count = 0
        }
        this.state = state
        this.delay.countLimit = state.speed / (state.arr.size+1)
        if(delay.countLimit <0 ) delay.countLimit =0

        delay.loop() //切換動畫的delay

    }

    fun update(timePassed: Long) {
        if (delay.count()) {
            count++
            if (state.isLoop) {
                count %= state.arr.size
            } else {
                if (count >= state.arr.size) {
                    count = state.arr.size - 1
                }
            }
        }
    }

    fun setDir(dir:Global.Direction){
        this.dir = dir
    }
    fun paint( left: Int, top: Int, wide: Int, high: Int, g: Graphics) {
        if (count < state.arr.size) {
            img = ResController.instance.image(state.arr[count])
            if (picFixAngle != 0) {
                img = ImageUtils.imageRotate(img!!, picFixAngle)
            }
            if(dir == Global.Direction.LEFT){
                img  = ImageUtils.imageHFlip(img!!)
            }
        }
//        if(Global.IS_DEBUG){
//            println( state.name + " : "+ count)
//        }
        g.drawImage(
            img, left, top, wide, high, null
        )

    }


}