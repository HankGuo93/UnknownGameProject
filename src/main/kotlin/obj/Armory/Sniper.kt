package obj.Armory

import obj.bullet.BulletComm
import java.awt.Graphics

class Sniper(x: Int, y: Int, b: BulletComm,allwaysFull:Boolean = false) : Weapon(x, y, allwaysFull, b, false) {

    init {
        shotMaxRange = 1600.0

        ammoMagazine = 1
        ammoMax = 8
        ammo = ammoMax

        shotCD = 60
        reloadCD = 60
    }

    override fun shotAction(x: Int, y: Int, v: Double, baseDemage: Int): MutableList<BulletComm> {
        var arr: MutableList<BulletComm> = mutableListOf()
        damageRate = 1.5
        arr.add(
            bullet.clone(
                x, y,
                v,
                shotMaxRange,
                damageRate * baseDemage
            )
        )
        return arr
    }

    override fun paintComponent(g: Graphics) {

    }

}