package obj.background

import core.controllers.ResController
import obj.utils.GameObject
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image
import kotlin.math.roundToInt

class Trees(x: Int = 0, y: Int = 0): GameObject(x, y, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT) {
    private val imgBackGround: Image = ResController.instance.image(Path.Imgs.BackGround.trees)
    private var paintShiftCount :Double = 0.0

    override fun paintComponent(g: Graphics) {
        g.drawImage(imgBackGround,
            painter.left,
            painter.top,
            painter.right,
            painter.bottom*8/10,
            (paintShiftCount*544/Global.SCREEN_X).roundToInt(),
            0,544+(paintShiftCount*544.0/Global.SCREEN_X).roundToInt(),
            160,
            null)
    }

    fun paintShift(dx:Double){
        paintShiftCount += dx
        if ((paintShiftCount*544/Global.SCREEN_X).roundToInt() > 544 ){
            paintShiftCount =0.0
        }
    }

    override fun update(timePassed: Long) {

    }

}