import audio.AudioResourceController
import core.GameInterfaceImpl
import core.GameKernel
import core.controllers.ResController
import scene.ChoseRoleScene
import scene.GameStartScene
import scene.MainScene
import scene.TestScene
import utils.Global
import utils.Path
import java.awt.*
import javax.swing.JFrame


fun main(args: Array<String>) {
    val gk = GameKernel(Global.UPDATE_FREQ, Global.PAINT_FREQ, GameInterfaceImpl(GameStartScene()))
    var screenSize: Dimension = Toolkit.getDefaultToolkit().screenSize

    //滑鼠游標測試
//    val cursorImg = BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB) //空的圖示 隱藏游標
    val cursorImg = ResController.instance.image(Path.Imgs.Objs.crosshair) //更換游標圖示
    val picCursor: Cursor = Toolkit.getDefaultToolkit().createCustomCursor(
        cursorImg, Point(16, 16), "picture cursor") //游標圖示為32X32 point為滑鼠指向位置，使用圖片中心 故設定16,16
    //滑鼠游標測試

    val data = JFrame().apply {
        title = "Unknown"
//        pack()
        setSize(Global.WINDOW_WIDTH.toInt(), Global.WINDOW_HEIGHT.toInt())
        isResizable = false
        setLocation(
            (screenSize.width - Global.WINDOW_WIDTH * 4 / 5) / 2,
            (screenSize.height - Global.WINDOW_HEIGHT * 5 / 6) / 2
        )
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        add(gk)
        isVisible = true
        contentPane.cursor = picCursor//滑鼠游標測試
        gk.play(Global.IS_DEBUG)
    }

    AudioResourceController.getInstance().loop(Path.Sounds.BackGround.BGM1, Integer.MAX_VALUE / 2)

}