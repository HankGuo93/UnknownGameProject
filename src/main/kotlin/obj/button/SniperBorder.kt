package obj.button

import core.controllers.ResController
import obj.Armory.Weapon
import obj.utils.WeaponBorder
import utils.Global
import utils.Path
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.Image
import java.io.File
import java.io.InputStream

class SniperBorder(x: Int, y: Int, weaponState: Weapon) : WeaponBorder(x, y, weaponState, price = 500, unlockdistance = 90) {
    private var file: InputStream? = javaClass.getResourceAsStream("/font/pixelFJ8pt1__.TTF")
    private var font: Font = Font.createFont(Font.TRUETYPE_FONT, file)
    var weapon: Image = ResController.instance.image(Path.Imgs.Objs.Shuriken.sniper)
    private var crossHair: Image = ResController.instance.image(Path.Imgs.Objs.crosshair)
    override fun paintComponent(g: Graphics) {
        if (lock) {
            g.drawImage(
                locker, painter.left, painter.top,
                painter.width, painter.height, null
            )
            g.font = font.deriveFont(1, 15.0F)
            g.color = Color.white
            g.drawString("${this.unlockdistance} m", this.painter.left + this.painter.width/4, this.painter.centerY)
        } else {
            g.drawImage(
                border, painter.left, painter.top,
                painter.width, painter.height, null
            )
            g.drawImage(
                weapon, painter.left + painter.width * 20 / 50, painter.top + painter.height * 10 / 100,
                Global.MapObject, Global.MapObject, null
            )
            g.drawImage(
                crossHair, painter.left + painter.width * 20 / 50 + 25, painter.top + painter.height * 10 / 100 -10,
                Global.MapObject, Global.MapObject, null
            )
            g.drawImage(
                item, painter.left + painter.width * 8 / 50, painter.top + painter.height * 2 / 5,
                Global.MapObject / 2, Global.MapObject / 2, null
            )
            g.drawImage(
                box, painter.left + painter.width * 8 / 50, painter.top + painter.height * 3 / 5,
                Global.MapObject / 2, Global.MapObject / 2, null
            )
            g.drawImage(
                coin, painter.left + painter.width * 8 / 50, painter.top + painter.height * 4 / 5,
                Global.MapObject / 2, Global.MapObject / 2, null
            )
            if (!state) {
                g.color = Color(1F, 1F, 1F, 0.5F)
                g.fillRect(
                    painter.left, painter.top,
                    painter.width, painter.height
                )
            }
//            g.font = Font("Luxi Serif", 1, 15)
            g.font = font.deriveFont(1, 13.0F)
            g.color = Color.white

            g.drawString(
                "${weaponState.ammo}",
                painter.left + painter.width * 35 / 50,
                painter.top + painter.height * 25 / 50
            )
            if (weaponState.ammoMax * weaponState.ammoMagazine <= 0) {
                g.color = Color.red.darker()
            }
            g.drawString(
                "${weaponState.ammoMax * weaponState.ammoMagazine}",
                painter.left + painter.width * 35 / 50,
                painter.top + painter.height * 35 / 50
            )
            g.color = Color.white
            g.drawString("$price", painter.left + painter.width * 30 / 50, painter.top + painter.height * 45 / 50)
        }
    }
}