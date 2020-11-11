package obj.utils

import core.controllers.ResController
import mathUtils.ImageUtils
import utils.Global
import utils.NinjaPath
import java.awt.Graphics
import java.awt.Image

class MachineAnimator(private var machine: Machine, private var state: State) {

    enum class Machine(val folderPath:String){
        Machine1(NinjaPath.Imgs.Actors.ninja.machine1.toString()),
        Machine2(NinjaPath.Imgs.Actors.ninja.machine2.toString()),
    }

    enum class State(val arr: List<String>, var isLoop: Boolean = false, val speed: Int) {
        IDLE(NinjaPath.Imgs.Actors.ninja.machineAct.idle.picArr, true, 20),
        ATTACK(NinjaPath.Imgs.Actors.ninja.machineAct.attack.picArr, false, 12),
        WALK(NinjaPath.Imgs.Actors.ninja.machineAct.walk.picArr, true, 20),
        DEADSMOKE(NinjaPath.Imgs.Objs.effect.deadSmoke.picArr, false, 20)
    }

    private var img: Image? = null
    private val delay = core.utils.Delay(0)
    private var count = 0

    init {
        setState(state)
    }

    fun setNinja(machine:Machine){
        this.machine=machine
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
            if(state == State.DEADSMOKE){
                img = ResController.instance.image( state.arr[count])
            }else{
                img = ResController.instance.image( machine.folderPath + state.arr[count])
                if (dir == Global.Direction.LEFT) {
                    img = ImageUtils.imageHFlip(img!!)
                }
            }
        }
        g.drawImage(
            img, left, top, wide, high, null
        )
    }
}