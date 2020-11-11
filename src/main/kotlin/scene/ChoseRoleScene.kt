package scene

import core.GameKernel
import core.Scene
import core.controllers.ResController
import obj.Enemy
import obj.MainRole
import obj.utils.GameInfo
import obj.utils.NinjaAnimator
import utils.Global
import utils.Path
import java.awt.Color
import java.awt.Graphics
import java.awt.Image
import java.awt.event.KeyEvent

class ChoseRoleScene(override var gameInfo: GameInfo = GameInfo()) : Scene(gameInfo) {
    enum class ANIMATOR(val ninjaAnimator: NinjaAnimator.Ninja) {
        ONE(NinjaAnimator.Ninja.NINJA1),
        TWO(NinjaAnimator.Ninja.NINJA2),
        THREE(NinjaAnimator.Ninja.NINJA3),
        FOUR(NinjaAnimator.Ninja.NINJA4),
        FIVE(NinjaAnimator.Ninja.NINJA5),
        SIX(NinjaAnimator.Ninja.NINJA6),
        SEVEN(NinjaAnimator.Ninja.NINJA7)
    }

    var xGap: Int = 280;
    var yGap: Int = 280;
    var ninjaArr = arrayOf("ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN")
    var ninjaPostion: Array<Array<Int>> = arrayOf(
        arrayOf(xGap * 2 * 118 / 100, yGap * 15 / 10),
        arrayOf(xGap * 3 * 112 / 100, yGap * 15 / 10),
        arrayOf(xGap * 4 * 109 / 100, yGap * 15 / 10),
        arrayOf(xGap * 135 / 100, yGap * 2 * 12 / 10),
        arrayOf(xGap * 2 * 118 / 100, yGap * 2 * 12 / 10),
        arrayOf(xGap * 3 * 112 / 100, yGap * 2 * 12 / 10),
        arrayOf(xGap * 4 * 109 / 100, yGap * 2 * 12 / 10)
    )
    var animator: Int = 0;
    var name: String = "LALALA"
    var selection: Image = ResController.instance.image(Path.Imgs.StartScene.selection)
    var mainRole: MainRole = MainRole(xGap * 135 / 100, yGap * 15 / 10)
    var role: MutableList<Enemy> = mutableListOf()

    override fun sceneBegin() {
        this.mainRole.name = name
//        role.add(Enemy(ninjaPostion[0][0],ninjaPostion[0][1]))
//        role.add(Enemy(ninjaPostion[1][0],ninjaPostion[1][1]))
//        role.add(Enemy(ninjaPostion[2][0],ninjaPostion[2][1]))
//        role.add(Enemy(ninjaPostion[3][0],ninjaPostion[3][1]))
//        role.add(Enemy(ninjaPostion[4][0],ninjaPostion[4][1]))
//        role.add(Enemy(ninjaPostion[5][0],ninjaPostion[5][1]))
//        role.add(Enemy(ninjaPostion[6][0],ninjaPostion[6][1]))
    }

    override fun sceneEnd() {

    }

    override fun update(timePassed: Long) {
        mainRole.update(timePassed)
        role.forEach { a ->
            a.update(timePassed)
        }
    }

    override fun paint(g: Graphics) {
        g.drawImage(selection, 0, 0, Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT, null)
        g.color = Color.black
        for (i in 1..4)
            for (j in 1..2) {
                g.fillRect(i * 280, j * 280, 200, 200)
            }
        mainRole.paint(g)
        role.forEach { a -> a.paint(g) }
    }

    override val input: ((GameKernel.Input.Event) -> Unit)?
        get() = { e ->
            run {
                when (e) {
                    is GameKernel.Input.Event.KeyPressed -> {
                        if (e.data.keyCode == KeyEvent.VK_S) {
                        }
                        if (e.data.keyCode == KeyEvent.VK_A) {
                            if (animator <= 0) {
                                animator = 6
                            }
                            animator = (--animator) % 7
                            mainRole.animator =
                                NinjaAnimator(
                                    ANIMATOR.valueOf(ninjaArr[animator]).ninjaAnimator,
                                    NinjaAnimator.State.IDLE
                                )
                        }
                        if (e.data.keyCode == KeyEvent.VK_D) {
                            animator = (++animator) % 7
                            mainRole.animator =
                                NinjaAnimator(
                                    ANIMATOR.valueOf(ninjaArr[animator]).ninjaAnimator,
                                    NinjaAnimator.State.IDLE
                                )

                        }
                    }
                }
            }
        }
}