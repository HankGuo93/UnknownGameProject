package obj.Armory

import core.controllers.ResController
import core.utils.Delay
import mathUtils.Coordinate
import mathUtils.Vector
import obj.bullet.BulletComm
import obj.bullet.ShotB
import obj.bullet.Shuriken
import obj.utils.GameObject
import obj.utils.NinjaAnimator
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image

class Scroll(x: Int = Global.WINDOW_WIDTH + Global.MapObject*10, y: Int =  -Global.MapObject*10)
    :GameObject(x, y, 450, 300){
    var scroll: Image = ResController.instance.image(Path.Imgs.Objs.scroll)
    var weapon: Weapon = ShotGun(-1, -1, Shuriken(-1, -1, 0.0, 0.0)).apply {
        this.allwaysFull=true
        this.ammoMagazine = 1
    }
    var standBy: Boolean = false
    var disappear: Boolean = false
    var remove: Boolean = false
    private var delayDisplay: Delay = Delay(300)
    private var smokeDisplay: Delay = Delay(24)
    private val moon2: Image = ResController.instance.image(Path.Imgs.StartScene.moon2)
    private val animator = NinjaAnimator(NinjaAnimator.Ninja.NINJA6, NinjaAnimator.State.DEADSMOKE)

    override fun paintComponent(g: Graphics) {
        if (!disappear) {
            g.drawImage(scroll, this.painter.left, this.painter.top, this.painter.width, this.painter.height, null)
        }
        if (standBy && !disappear) {
            g.drawImage(scroll, this.painter.left, this.painter.top, this.painter.width, this.painter.height, null)
            g.drawImage(moon2, this.painter.left * 103 / 100, this.painter.top * 2, 300, 200, null)
        } else if (!remove){
            animator.paint(
                Global.Direction.RIGHT,
                painter.left,
                painter.top,
                painter.width,
                painter.height, g
            )
        }
    }

    override fun update(timePassed: Long) {
        if (this.painter.centerX > 1400) {
            this.translate(-3, 3)
            delayDisplay.play()
        } else {
            if(delayDisplay.count()){
                disappear = true
                smokeDisplay.play()
            } else {
                standBy = true
                weapon.update(timePassed)
                weapon.releaseWeapon()
            }
        }
        if (disappear) {
            animator.update(timePassed)
            if(smokeDisplay.count()){
                remove = true
            }
        }

    }

    fun shooting(x: Int, y:Int): MutableList<BulletComm>{
        val tmp = Vector.xyToVector(
            Coordinate(
                (x - this.painter.centerX).toDouble(),
                (y - this.painter.centerY).toDouble()
            )
        )
        if (weapon.ammo <= 0 && weapon.wpnState == Weapon.WPNSTATE.STANDBY) {
            weapon.reload()
        }
        return weapon.shot(this.painter.centerX-20, this.painter.centerY, tmp.degree-5, 10, 2000.0)
    }
}