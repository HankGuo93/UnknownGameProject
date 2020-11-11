package scene

import core.GameKernel
import core.Scene
import core.controllers.ResController
import core.controllers.SceneController
import obj.button.SceneButton
import obj.utils.GameInfo
import utils.Global
import utils.Path
import java.awt.*
import java.awt.event.KeyEvent
import javax.swing.JFrame
import kotlin.system.exitProcess

class ManualScene(override var gameInfo: GameInfo = GameInfo()) : Scene(gameInfo) {
    var backGround: Image = ResController.instance.image(Path.Imgs.StartScene.readme_border)
    var readPaper: Image = ResController.instance.image(Path.Imgs.StartScene.readme_paper)
    private var backButton: SceneButton = SceneButton(Global.SCREEN_X * 6 / 7, Global.SCREEN_Y * 7 / 8,
            Path.Imgs.StartScene.back_button, Path.Imgs.StartScene.back_button_click)

    override fun sceneBegin() {

    }

    override fun sceneEnd() {
        ResController.instance.clear()
    }

    override fun paint(g: Graphics) {
        g.drawImage(readPaper, 0,0, Global.SCREEN_X, Global.SCREEN_Y, null)
        g.drawImage(backGround, 0, 0, Global.SCREEN_X / 2, Global.SCREEN_Y / 2, null)
        backButton.paint(g)

    }

    override fun update(timePassed: Long) {}

    override val input: ((GameKernel.Input.Event) -> Unit)?
        get() = { e ->
            run {
                when (e) {
                    is GameKernel.Input.Event.KeyKeepPressed -> {
                    }
                    is GameKernel.Input.Event.MousePressed -> {

                        if (backButton.click(e.data.x, e.data.y)) {
                            SceneController.instance.change(GameStartScene())
                        }
                    }
                    is GameKernel.Input.Event.MouseMoved -> {
                        backButton.touch(e.data.x, e.data.y)
                    }
                }
            }
        }

}