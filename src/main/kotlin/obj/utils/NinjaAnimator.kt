package obj.utils

import core.controllers.ResController
import mathUtils.ImageUtils
import utils.Global
import utils.MarioPath
import utils.NinjaPath
import java.awt.Graphics
import java.awt.Image

class NinjaAnimator(private var ninja: Ninja, private var state: State) {

    enum class Ninja(val folderPath:String){
        NINJA1(NinjaPath.Imgs.Actors.ninja.ninja1.toString()),
        NINJA2(NinjaPath.Imgs.Actors.ninja.ninja2.toString()),
        NINJA3(NinjaPath.Imgs.Actors.ninja.ninja3.toString()),
        NINJA4(NinjaPath.Imgs.Actors.ninja.ninja4.toString()),
        NINJA5(NinjaPath.Imgs.Actors.ninja.ninja5.toString()),
        NINJA6(NinjaPath.Imgs.Actors.ninja.ninja6.toString()),
        NINJA7(NinjaPath.Imgs.Actors.ninja.ninja7.toString())
    }

    enum class State(val arr: List<String>, var isLoop: Boolean = false, val speed: Int) {
        DEFAULT(NinjaPath.Imgs.Actors.ninja.ninjaAct.idle.picArr, true, 20),
        HIGHJUMP(NinjaPath.Imgs.Actors.ninja.ninjaAct.highJump.picArr, false, 20),
        IDLE(NinjaPath.Imgs.Actors.ninja.ninjaAct.idle.picArr, true, 20),
        JUMP(NinjaPath.Imgs.Actors.ninja.ninjaAct.jump.picArr, false, 10),
        RUN(NinjaPath.Imgs.Actors.ninja.ninjaAct.run.picArr, true, 30),
        THROW(NinjaPath.Imgs.Actors.ninja.ninjaAct.throwAct.picArr, false, 20),
        THROWJUMP(NinjaPath.Imgs.Actors.ninja.ninjaAct.throwJump.picArr, false, 20),
        THROWRUN(NinjaPath.Imgs.Actors.ninja.ninjaAct.throwRun.picArr, false, 20),
        DEADSMOKE(NinjaPath.Imgs.Objs.effect.deadSmoke.picArr, false, 20),

        //hamrio
        MIDLE(MarioPath.Imgs.Actors.hamrio.idle.pic_Arr, true, 12),
        MRUN(MarioPath.Imgs.Actors.hamrio.run.pic_Arr, true, 24),
        MSHIELD(MarioPath.Imgs.Actors.hamrio.shield.pic_Arr, true, 24),
        MTHROWRUN(MarioPath.Imgs.Actors.hamrio.throwHam.pic_Arr, false, 12),
    }

    private var img: Image? = null
    private val delay = core.utils.Delay(0)
    private var count = 0

    init {
        setState(state)
    }

    fun setNinja(ninja:Ninja){
        this.ninja=ninja
    }

    fun setState(state: State) {
        if(this.state != state){
            count =0
        }
        this.state = state
        this.delay.countLimit = state.speed / state.arr.size
        delay.loop()

    }


    fun update(timePassed: Long) {
        if (delay.count()) {
            if (state.isLoop) {
                count = ++count % state.arr.size
            } else {
                if (count < state.arr.size - 1) {
                    count++
                }
            }
        }
    }

    fun paint(dir: Global.Direction, left: Int, top: Int, wide: Int, high: Int, g: Graphics) {
        if (count < state.arr.size) {
            if(state == State.DEADSMOKE || state == State.MIDLE || state == State.MRUN || state == State.MSHIELD || state == State.MTHROWRUN){
                img = ResController.instance.image( state.arr[count])
            }else{
                img = ResController.instance.image( ninja.folderPath + state.arr[count])
            }
                if (dir == Global.Direction.LEFT) {
                    img = ImageUtils.imageHFlip(img!!)
                }

        }
        g.drawImage(
            img, left, top, wide, high, null
        )
    }
}