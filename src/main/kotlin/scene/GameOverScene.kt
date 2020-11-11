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
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Image
import java.io.*


class GameOverScene(gameInfo: GameInfo = GameInfo()) : Scene(gameInfo) {
//    private var file : File = File("/pixelFJ8pt1__.TTF")
//    private var file : File = File(ClassLoader.getSystemResource("\\font\\pixelFJ8pt1__.TTF").toURI())
    private var file: File = File(javaClass.getResource("..\\font\\pixelFJ8pt1__.TTF").toURI())
    private var font: Font = Font.createFont(Font.TRUETYPE_FONT, file)
    private val menuButton: SceneButton = SceneButton(Global.SCREEN_X*5/6, Global.SCREEN_Y*7/8,
            Path.Imgs.EndScene.menu, Path.Imgs.EndScene.menu_click)
    private val backGround: Image = ResController.instance.image(Path.Imgs.EndScene.backGround)
    private val unAcademy: Image = ResController.instance.image(Path.Imgs.EndScene.unAcademy)
    private val academy: Image = ResController.instance.image(Path.Imgs.EndScene.academy) // 1
    private val unGenin: Image = ResController.instance.image(Path.Imgs.EndScene.unGenin)
    private val genin: Image = ResController.instance.image(Path.Imgs.EndScene.genin) // 2
    private val unChunning: Image = ResController.instance.image(Path.Imgs.EndScene.unChuuning)
    private val chunning: Image = ResController.instance.image(Path.Imgs.EndScene.chuuning) // 3
    private val unJounin: Image = ResController.instance.image(Path.Imgs.EndScene.unJounin)
    private val jounin: Image = ResController.instance.image(Path.Imgs.EndScene.jounin) // 4
    private val unHokage: Image = ResController.instance.image(Path.Imgs.EndScene.unHokage)
    private val hokage: Image = ResController.instance.image(Path.Imgs.EndScene.hokage) //5
    private val rankState = arrayOf(false, false, false, false, false)

    override fun sceneBegin() {
        var fileID: String = "gameRecord.date";
        val file = File(fileID)
//        val file : File = File(ClassLoader.getSystemResource("gameRecord.date").toURI())
        var tmp = file.readLines() as MutableList<String>
        tmp.add(gameInfo.toString())
//        tmp.sortBy{ a -> -a[0].toInt()}
        tmp.sortBy{ a -> -a.split(" ")[4].toInt()}
        file.writeText("${tmp[0]}\n" +  "${tmp[1]}\n" + "${tmp[2]}\n" + "${tmp[3]}\n" + "${tmp[4]}")
        when(true){
            (gameInfo.getTotalPoint() >= 5001) -> rankState[4] = true
            (gameInfo.getTotalPoint() >= 3001) -> rankState[3] = true
            (gameInfo.getTotalPoint() >= 1501) -> rankState[2] = true
            (gameInfo.getTotalPoint() >= 601) -> rankState[1] = true
            (gameInfo.getTotalPoint() >= 0) -> rankState[0] = true
        }
        AudioResourceController.getInstance().loop(Path.Sounds.BackGround.OVER2,Integer.MAX_VALUE/2)
    }

    override fun sceneEnd() {
        ResController.instance.clear()
        AudioResourceController.getInstance().stop(Path.Sounds.BackGround.OVER2)
        AudioResourceController.getInstance().loop(Path.Sounds.BackGround.BGM1, Integer.MAX_VALUE / 2)//撥放選單音樂
    }

    override fun paint(g: Graphics) {
        g.drawImage(backGround, 0, 0, null)
        g.color = Color.BLACK
        g.fillRect(Global.SCREEN_X / 15, Global.SCREEN_Y / 20, 400, 720)
        g.font = font.deriveFont(3, 48.0F)
        g.color = Color.red.darker()
        g.drawString("Darts spent point:", Global.SCREEN_X / 3, Global.SCREEN_Y / 6)
        g.drawString("${gameInfo.spendAmmo}", Global.SCREEN_X * 9 / 10 - countStringDistance(gameInfo.spendAmmo) * 25, Global.SCREEN_Y / 6)
        g.drawString("DISTANCE:", Global.SCREEN_X / 3, Global.SCREEN_Y / 6 + 120)
        g.drawString("${gameInfo.distance/100}", Global.SCREEN_X * 9 / 10 - countStringDistance(gameInfo.distance) * 9, Global.SCREEN_Y / 6 + 120)
        g.drawString("KILL PTS:", Global.SCREEN_X / 3, Global.SCREEN_Y / 6 + 240)
        g.drawString("${gameInfo.killCount}", Global.SCREEN_X * 9 / 10 - countStringDistance(gameInfo.killCount) * 12, Global.SCREEN_Y / 6 + 240)
        g.fillRect(Global.SCREEN_X / 3, Global.SCREEN_Y * 3 / 5, Global.SCREEN_X / 2, 20)
        g.color = Color.DARK_GRAY
        g.font = font.deriveFont(3, 60.0F)
        g.drawString("TOTAL POINT:", Global.SCREEN_X / 3, Global.SCREEN_Y / 6 + 480)
        g.font = font.deriveFont(3, 80.0F)
        g.color = Color.red
        g.drawString("${gameInfo.getTotalPoint()}", Global.SCREEN_X * 9 / 10 - countStringDistance(gameInfo.getTotalPoint()) * 60, Global.SCREEN_Y / 6 + 480)
        when (rankState[0]) {
            false -> g.drawImage(unAcademy, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 560, null)
            true -> g.drawImage(academy, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 560, null)
        }
        when (rankState[1]) {
            false -> g.drawImage(unGenin, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 430, null)
            true -> g.drawImage(genin, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 430, null)
        }
        when (rankState[2]) {
            false -> g.drawImage(unChunning, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 300, null)
            true -> g.drawImage(chunning, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 300, null)
        }
        when (rankState[3]) {
            false -> g.drawImage(unJounin, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 170, null)
            true -> g.drawImage(jounin, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 170, null)
        }
        when (rankState[4]) {
            false -> g.drawImage(unHokage, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 40, null)
            true -> g.drawImage(hokage, Global.SCREEN_X / 15 + 40, Global.SCREEN_Y / 20 + 40, null)
        }
        menuButton.paint(g)
    }

    override fun update(timePassed: Long) {

    }

    override val input: ((GameKernel.Input.Event) -> Unit)?
        get() = { e ->
            run {
                when (e) {
                    is GameKernel.Input.Event.MousePressed -> {
                        if (menuButton.click(e.data.x, e.data.y)) {
                            this.gameInfo = GameInfo()
                            SceneController.instance.change(GameStartScene())
                        }
                    }
                    is GameKernel.Input.Event.MouseMoved -> {
                        menuButton.touch(e.data.x,  e.data.y)
                    }
                }
            }
        }
}