package obj.button

import core.controllers.ResController
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image

class PauseButton (x: Int, y: Int, path: String, pathSub: String, width: Int, height: Int):
    SceneButton(x, y, path, pathSub, width, height){
    var click : Boolean = false
    var paused: Image = ResController.instance.image(Path.Imgs.Objs.paused)
    override fun update(timePassed: Long) {
        if (click){
            this.img = ResController.instance.image(pathSub)
        } else {
            this.img = ResController.instance.image(path)
        }
    }
    override fun paintComponent(g: Graphics) {
        g.drawImage(img, painter.left, painter.top,
            painter.width, painter.height, null)
        if (click){
            g.drawImage(paused, Global.SCREEN_CENTER_X/2, Global.SCREEN_Y/3, null)
        }
    }

}