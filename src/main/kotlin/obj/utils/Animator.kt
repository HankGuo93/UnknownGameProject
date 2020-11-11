package obj.utils

import core.GameKernel
import core.controllers.ResController
import core.utils.Delay
import utils.Global
import utils.Path
import java.awt.Graphics

class Animator(var type: Int,private var state:State) {

    enum class State(val arr:IntArray,val speed:Int) {
        WALK( intArrayOf(0, 1, 2, 1),20),
        RUN( intArrayOf(0, 2),15)
    }
    private val img = ResController.instance.image(Path.Imgs.Actors.ACTOR1)
    private val delay = core.utils.Delay(0)
    private var count = 0

    init {
        setState(state)
    }

    fun setState(state: State){
        this.state = state
        this.delay.countLimit=state.speed
        delay.loop()
    }

    fun update(timePassed: Long) {
        if (delay.count()) {
            count = ++count % state.arr.size
        }
    }

    fun paint(dir: Global.Direction, left: Int, top: Int, right: Int, bottom: Int, g: Graphics) {
        g.drawImage(
            img, left, top,
            right, bottom,
            (Global.UNIT_X * 3) * (type % 4) + Global.UNIT_X * state.arr[count],
            (Global.UNIT_Y * 4) * (type / 4) + Global.UNIT_Y * dir.value,
            (Global.UNIT_X * 3) * (type % 4) + Global.UNIT_X + Global.UNIT_X * state.arr[count],
            (Global.UNIT_Y * 4) * (type / 4) + Global.UNIT_Y + Global.UNIT_Y * dir.value, null
        )
    }


}