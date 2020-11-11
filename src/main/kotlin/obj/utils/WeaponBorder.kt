package obj.utils

import audio.AudioResourceController
import core.controllers.ResController
import obj.Armory.Weapon
import utils.Global
import utils.Path
import java.awt.Graphics
import java.awt.Image

abstract class WeaponBorder(x: Int, y: Int, var weaponState: Weapon, var price: Int, var lock: Boolean = true, var unlockdistance: Int): GameObject(x, y, Global.SCREEN_X/15, Global.SCREEN_Y/7){
    var item: Image = ResController.instance.image(Path.Imgs.Objs.item)
    var locker: Image = ResController.instance.image(Path.Imgs.Objs.Locker)
    open var border: Image = ResController.instance.image(Path.Imgs.Objs.border)
        protected set
    var box: Image = ResController.instance.image(Path.Imgs.Objs.box)
    var coin: Image = ResController.instance.image(Path.Imgs.Objs.coin)
    var state: Boolean = false
    override fun paintComponent(g: Graphics) {

    }

    override fun update(timePassed: Long) {

    }

    fun updateWeaponInfo(weapon: Weapon){
        this.weaponState = weapon
    }

    fun click (x: Int, y: Int): Boolean{
        var tmp: Boolean = x >= this.collider.left && x <= this.collider.right &&
                y >= this.collider.top && y <= this.collider.bottom
        if (!lock) {
            if(tmp) {
                AudioResourceController.getInstance()
                    .play("sounds\\click.wav")
            }
            return tmp
        }
        return false
    }

    fun purchasable(money: Int):Boolean{
        if (!lock) {
            return money > price
        }
        return false
    }
}