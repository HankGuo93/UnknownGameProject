package scene

import core.GameKernel
import core.Scene
import core.controllers.ResController
import core.controllers.SceneController
import core.utils.AddFont
import obj.button.SceneButton
import obj.utils.GameInfo
import utils.Global
import utils.Path
import java.awt.*
import java.io.File
import java.io.InputStream
import javax.xml.bind.annotation.XmlElementDecl
import kotlin.system.exitProcess

class RecordScene(override var gameInfo: GameInfo = GameInfo()) : Scene(gameInfo) {
//    private var file: File = File(javaClass.getResource("/font/pixelFJ8pt1__.TTF").toURI())
    private var file: InputStream? = javaClass.getResourceAsStream("/font/pixelFJ8pt1__.TTF")
    private var font: Font = Font.createFont(Font.TRUETYPE_FONT, file)
//    private var font: Font = AddFont().createFont()!!
    private val academy: Image = ResController.instance.image(Path.Imgs.EndScene.academy)   // 1
    private val genin: Image = ResController.instance.image(Path.Imgs.EndScene.genin) // 2
    private val chunning: Image = ResController.instance.image(Path.Imgs.EndScene.chuuning)  // 3
    private val jounin: Image = ResController.instance.image(Path.Imgs.EndScene.jounin)  // 4
    private val hokage: Image = ResController.instance.image(Path.Imgs.EndScene.hokage)  //5
    private var displayInfo: MutableList<String> = mutableListOf()
    val scorelist: MutableList<MutableList<Int>> = mutableListOf()
    var backGround: Image = ResController.instance.image(Path.Imgs.StartScene.record_border)
    private var backButton: SceneButton = SceneButton(Global.SCREEN_X * 6 / 7, Global.SCREEN_Y * 7 / 8,
            Path.Imgs.StartScene.back_button, Path.Imgs.StartScene.back_button_click)

    override fun sceneBegin() {
        scorelist.add(mutableListOf<Int>(0, 20, 300, 50, 10000))
        var fileID: String = "gameRecord.date"
        val file = File(fileID)
//        val file = File(ClassLoader.getSystemResource("gameRecord.date").toURI())
        displayInfo = file.readLines() as MutableList<String>
    }

    override fun sceneEnd() {
        ResController.instance.clear()
    }

    override fun paint(g: Graphics) {
        g.drawImage(backGround, 0, 0, Global.SCREEN_X, Global.SCREEN_Y, null)
        g.color = Color.LIGHT_GRAY
//        g.font = Font("Utopia", 3, 45)
        g.font = font.deriveFont(3, 35.0F)
        g.drawString("RANK", Global.SCREEN_X * 10 / 100, Global.SCREEN_Y * 15 / 100)
        g.drawString("AMMO", Global.SCREEN_X * 25 / 100, Global.SCREEN_Y * 15 / 100)
        g.drawString("DISTANCE", Global.SCREEN_X * 40 / 100, Global.SCREEN_Y * 15 / 100)
        g.drawString("KILLCOUNT", Global.SCREEN_X * 60 / 100, Global.SCREEN_Y * 15 / 100)
        g.drawString("TOTALPOINT", Global.SCREEN_X * 80 / 100, Global.SCREEN_Y * 15 / 100)
        for (i in 0 until displayInfo.size) {
            var tmp: MutableList<String> = displayInfo[i].split(" ") as MutableList<String>
            if (tmp[0].toInt() == 0) {
                g.drawString("-", Global.SCREEN_X * 13 / 100, Global.SCREEN_Y * (25 + (i * 11)) / 100)
            } else {
                when (tmp[0]) {
                    "5" -> g.drawImage(hokage, Global.SCREEN_X * 9 / 100, Global.SCREEN_Y * (20 + (i * 11)) / 100, 150, 60, null)
                    "4" -> g.drawImage(jounin, Global.SCREEN_X * 9 / 100, Global.SCREEN_Y * (20 + (i * 11)) / 100, 150, 60, null)
                    "3" -> g.drawImage(chunning, Global.SCREEN_X * 9 / 100, Global.SCREEN_Y * (20 + (i * 11)) / 100, 150, 60, null)
                    "2" -> g.drawImage(genin, Global.SCREEN_X * 9 / 100, Global.SCREEN_Y * (20 + (i * 11)) / 100, 150, 60, null)
                    "1" -> g.drawImage(academy, Global.SCREEN_X * 9 / 100, Global.SCREEN_Y * (20 + (i * 11)) / 100, 150, 60, null)
                }
            }
            g.drawString(tmp[1], Global.SCREEN_X * 30 / 100 - countStringDistance(tmp[1].toInt()) * 20, Global.SCREEN_Y * (25 + (i * 11)) / 100)
            g.drawString(tmp[2], Global.SCREEN_X * 50 / 100 - countStringDistance(tmp[2].toInt()) * 20, Global.SCREEN_Y * (25 + (i * 11)) / 100)
            g.drawString(tmp[3], Global.SCREEN_X * 70 / 100 - countStringDistance(tmp[3].toInt()) * 20, Global.SCREEN_Y * (25 + (i * 11)) / 100)
            g.drawString(tmp[4], Global.SCREEN_X * 90 / 100 - countStringDistance(tmp[4].toInt()) * 20, Global.SCREEN_Y * (25 + (i * 11)) / 100)
        }
        backButton.paint(g)
//        g.drawString(displayInfo[0])
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