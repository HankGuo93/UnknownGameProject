package obj.Armory

import core.controllers.ResController
import obj.utils.GameObject
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image

class Postion(x: Int = 0, y: Int = 0): GameObject(x, y, 48, 48) {
    private val img: Image = ResController.instance.image(Path.Imgs.Objs.postion)

    override fun paintComponent(g: Graphics) {
        g.drawImage(img, collider.left, collider.top, painter.width, painter.height, null)
    }

    override fun update(timePassed: Long) {
        translate(y = 3)
    }

}