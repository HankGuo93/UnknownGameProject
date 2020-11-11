package obj.background

import core.controllers.ResController
import obj.utils.GameObject
import utils.Global
import utils.Path
import java.awt.AlphaComposite
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import kotlin.math.roundToInt
import kotlin.time.days
class Fog (x:Int, y:Int): GameObject(x, y, 778, 204){
    private var flt:Float = 0.1F
    var xPosition: Float = this.painter.width*1/100.toFloat();
    private lateinit var g2d : Graphics2D
    private lateinit var alcom : AlphaComposite
    private val img = ResController.instance.image(Path.Imgs.BackGround.fog)

    override fun paintComponent(g: Graphics) {
        g2d  = g.create() as Graphics2D
        alcom = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, flt)
        g2d.composite = alcom
        g2d.drawImage(img, this.painter.left, 0, this.painter.right*42/10, this.painter.bottom,
                        xPosition.roundToInt(), 0,
                        (xPosition + this.painter.width.toFloat()*5/10).roundToInt(), this.painter.height
            ,null)
    }

    override fun update(timePassed: Long) {
        if (flt < 0.5F && xPosition < this.painter.width*20/100){
            flt += 0.003F
        }
        if (flt > 0.01F && xPosition >= this.painter.width*25/100){
            flt -= 0.003F
        } else if (xPosition >= this.painter.width* 28/100){
            xPosition = this.painter.width*1/100.toFloat();
        }
        xPosition += 0.2F;
    }

    fun floorArr(): MutableList<Fog>{
        var tmp: MutableList<Fog> = mutableListOf()
        tmp.add(Fog((Global.SCREEN_X / 2), (Global.SCREEN_Y * 8 / 10)))
        for (i in 1 until Global.SCREEN_X/32){
            tmp.add(Fog(tmp[tmp.size-1].collider.right,Global.SCREEN_Y * 8 / 10))
        }
        return tmp
    }

}
