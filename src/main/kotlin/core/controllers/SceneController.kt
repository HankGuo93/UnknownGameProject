package core.controllers

import core.GameKernel
import core.Scene
import obj.utils.GameInfo
import java.awt.Graphics

class SceneController private constructor() : GameKernel.GameInterface {
    private var lastScene: Scene? = null
    private var currentScene: Scene? = null

    fun change(scene: Scene) {
        lastScene = currentScene
        scene.gameInfo = lastScene?.gameInfo ?:  GameInfo()
        scene.sceneBegin()
        lastScene?.run { ResController.instance.keep() }
        currentScene = scene
    }

    override fun paint(g: Graphics) {
        currentScene?.paint(g)
    }

    override fun update(timePassed: Long) {
        lastScene?.let {
            it.sceneEnd()
            lastScene = null
            ResController.instance.release()
        }
        currentScene?.update(timePassed)
    }

    override val input: ((GameKernel.Input.Event) -> Unit)?
        get() = currentScene?.input

    companion object {
        val instance: SceneController by lazy { SceneController() }
    }
}