package obj.background

import core.controllers.ResController
import obj.utils.GameObject
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image

class StairObject(x: Int, y:Int,var type:StairType = StairType.FLOOR): GameObject(x, y, 32, 32
        ) {
    var judgeable: Boolean = false
    enum class StairType(val img: Image){
        FLOOR(ResController.instance.image(Path.Imgs.BackGround.ground)),
        FLOOR_UP(ResController.instance.image(Path.Imgs.BackGround.groundUp)),
        FLOOR_DOWN(ResController.instance.image(Path.Imgs.BackGround.groundDown));
    }

    override fun paintComponent(g: Graphics) {
        g.drawImage(type.img, painter.left, painter.top, painter.width, painter.height,null)
    }

    override fun update(timePassed: Long) {
        translate(y = 50)
    }

}