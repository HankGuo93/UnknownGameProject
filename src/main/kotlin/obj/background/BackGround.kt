package obj.background

import core.controllers.ResController
import obj.utils.GameObject
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image

class BackGround(x: Int = 0, y: Int = 0): GameObject(x, y, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT) {
    private val imgBackGround: Image = ResController.instance.image(Path.Imgs.BackGround.bg)

    override fun paintComponent(g: Graphics) {
        g.drawImage(imgBackGround ,painter.left, painter.top, Global.WINDOW_WIDTH, Global.SCREEN_Y*8/10, null)
    }

    override fun update(timePassed: Long) {

    }

}