package obj.button

import audio.AudioResourceController
import core.controllers.ResController
import obj.utils.GameObject
import utils.Global
import java.awt.Graphics
import java.awt.Image

open class SceneButton(x: Int, y:Int, var path: String, var pathSub: String, var width:Int = Global.SCREEN_X/6, var height:Int = Global.SCREEN_Y/6):
    GameObject(x, y, width, height) {

    var img: Image = ResController.instance.image(path)
    override fun paintComponent(g: Graphics) {
        g.drawImage(img, painter.left, painter.top,
                painter.width, painter.height, null)
    }

    override fun update(timePassed: Long) {

    }

    open fun click(x: Int, y: Int): Boolean {
        var boolean: Boolean = x >= this.collider.left && x <= this.collider.right &&
                y >= this.collider.top && y <= this.collider.bottom
        if (boolean) {
            AudioResourceController.getInstance()
                .play("\\sounds\\click.wav")
        }
        return boolean
    }

    fun touch(x: Int, y: Int): Boolean {
        if (x >= this.collider.left && x <= this.collider.right &&
                y >= this.collider.top && y <= this.collider.bottom) {
            this.img = ResController.instance.image(pathSub)
            return true
        } else {
            this.img = ResController.instance.image(path)
        }
        return false
    }
}