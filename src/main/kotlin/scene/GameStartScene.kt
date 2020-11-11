package scene

import audio.AudioResourceController
import core.GameKernel
import core.Scene
import core.controllers.ResController
import core.controllers.SceneController
import obj.button.SceneButton
import obj.utils.GameInfo
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image
import javax.imageio.ImageIO
import kotlin.system.exitProcess

class GameStartScene(override var gameInfo: GameInfo = GameInfo()) : Scene(gameInfo) {
//    var screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize
    var gameName: Image = ResController.instance.image(Path.Imgs.StartScene.gameName)
    var backGround: Image = ResController.instance.image(Path.Imgs.StartScene.startSceneBackGround)
    private var exitButton: SceneButton = SceneButton(
        Global.SCREEN_X * 2 / 3, Global.SCREEN_Y * 7 / 8,
        Path.Imgs.StartScene.exit_button, Path.Imgs.StartScene.exit_button_click
    )
    private var gameStartButton: SceneButton = SceneButton(
        Global.SCREEN_X * 1 / 3, Global.SCREEN_Y * 4 / 6,
        Path.Imgs.StartScene.gameStart_button, Path.Imgs.StartScene.gameStart_button_click
    )
    private var readmeButton: SceneButton = SceneButton(
        Global.SCREEN_X * 1 / 3, Global.SCREEN_Y * 7 / 8,
        Path.Imgs.StartScene.readme_button, Path.Imgs.StartScene.readme_button_click
    )
    private var recordButton: SceneButton = SceneButton(
        Global.SCREEN_X * 2 / 3, Global.SCREEN_Y * 4 / 6,
        Path.Imgs.StartScene.record_button, Path.Imgs.StartScene.record_button_click
    )
    override fun sceneBegin() {
        Global.log("Game Start")
//        println(javaClass.getResource(Path.Imgs.StartScene.gameName).toURI())
    }

    override fun sceneEnd() {
        ResController.instance.clear()
    }

    override fun paint(g: Graphics) {
        g.drawImage(backGround, 0, 0, Global.SCREEN_X, Global.SCREEN_Y, null)
        g.drawImage(
            gameName,
            Global.SCREEN_X / 6,
            Global.SCREEN_Y / 18,
            Global.WINDOW_WIDTH * 3 / 4,
            Global.WINDOW_HEIGHT *4/10,
            null
        )
        exitButton.paint(g)
        gameStartButton.paint(g)
        readmeButton.paint(g)
        recordButton.paint(g)
    }

    override fun update(timePassed: Long) {

    }

    override val input: ((GameKernel.Input.Event) -> Unit)?
        get() = { e ->
            run {
                when (e) {
                    is GameKernel.Input.Event.MousePressed -> {
                        if (gameStartButton.click(e.data.x, e.data.y)) {
                            SceneController.instance.change(MainScene())
                        }
                        if (readmeButton.click(e.data.x, e.data.y)) {
                            SceneController.instance.change(ManualScene())
                        }
                        if (recordButton.click(e.data.x, e.data.y)) {
                            SceneController.instance.change(RecordScene())
                        }
                        if (exitButton.click(e.data.x, e.data.y)) {
                            exitProcess(0)
                        }
                    }
                    is GameKernel.Input.Event.MouseMoved -> {
                        exitButton.touch(e.data.x, e.data.y)
                        gameStartButton.touch(e.data.x, e.data.y)
                        readmeButton.touch(e.data.x, e.data.y)
                        recordButton.touch(e.data.x, e.data.y)
                    }

//                    is GameKernel.Input.Event.KeyPressed -> {
//                        if (e.data.keyCode == KeyEvent.VK_ENTER){
//                            Global.setScreen(screenSize.width, screenSize.height)
//                        }
//                    }
//                    is GameKernel.Input.Event.KeyKeepPressed -> TODO()
//                    is GameKernel.Input.Event.KeyReleased -> TODO()
//                    is GameKernel.Input.Event.MouseReleased -> TODO()
//                    is GameKernel.Input.Event.MouseEntered -> TODO()
//                    is GameKernel.Input.Event.MouseExited -> TODO()
//                    is GameKernel.Input.Event.MouseDragged -> TODO()
//                    is GameKernel.Input.Event.MouseWheelMoved -> TODO()
                }
            }
        }

}