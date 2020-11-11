package core

import core.controllers.SceneController
import java.awt.Graphics

class GameInterfaceImpl(startScene: Scene) : GameKernel.GameInterface {

    init {
        SceneController.instance.change(startScene)
    }

    override fun update(timePassed: Long) = SceneController.instance.update(timePassed)

    override fun paint(g: Graphics) = SceneController.instance.paint(g)

    override val input: ((GameKernel.Input.Event) -> Unit)?
        get() = SceneController.instance.input
}