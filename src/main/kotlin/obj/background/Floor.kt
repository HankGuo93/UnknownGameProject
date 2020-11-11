package obj.background

import core.controllers.ResController
import obj.utils.GameObject
import utils.Global
import utils.Path
import java.awt.Color
import java.awt.Graphics
import kotlin.time.days

class Floor (x:Int, y:Int): GameObject(x, y, 3200, 40){


    private val img = ResController.instance.image(Path.Imgs.BackGround.floor)

    override fun paintComponent(g: Graphics) {
//        g.color = Color.PINK
//        g.fillRect(this.painter.left, this.painter.top, this.painter.width, this.painter.height)
        g.drawImage(img,this.painter.left, this.painter.top, this.painter.width, this.painter.height, null)
    }

    override fun update(timePassed: Long) {

    }

    fun floorArr(): MutableList<Floor>{
        var tmp: MutableList<Floor> = mutableListOf()
        tmp.add(Floor((Global.SCREEN_X / 2), (Global.SCREEN_Y * 8 / 10)))
        for (i in 1 until Global.SCREEN_X/32){
            tmp.add(Floor(tmp[tmp.size-1].collider.right,Global.SCREEN_Y * 8 / 10))
        }
        return tmp
    }

}
