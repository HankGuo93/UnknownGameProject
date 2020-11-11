package obj.Armory

import core.controllers.ResController
import core.utils.Delay
import obj.utils.GameObject
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image

class Shield(x: Int = 0, y: Int = 0): GameObject(x, y, 50, 80,x,y,70,80) {
    private val img: Image = ResController.instance.image(Path.Imgs.Objs.shield)

    private var delay = Delay(3)
    var refund = true
        private set

    init{
        delay.play()
    }
    override fun paintComponent(g: Graphics) {
        g.drawImage(img, painter.left, painter.top, painter.width, painter.height, null)
    }

    override fun update(timePassed: Long) {
        if(delay.count()){
            refund = false
        }
    }

}