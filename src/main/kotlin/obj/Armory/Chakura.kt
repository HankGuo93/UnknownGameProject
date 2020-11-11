package obj.Armory

import obj.bullet.BulletComm
import kotlin.random.Random

class Chakura(x: Int, y: Int, private var b: BulletComm,allwaysFull:Boolean = false) : Weapon(x, y, allwaysFull, b, false) {

    init {
        shotMaxRange = 1000.0

        ammoMagazine = 1
        ammoMax = 5
        ammo = ammoMax

        shotCD = 30
        reloadCD = 30
    }

    override fun shotAction(x: Int, y: Int, v: Double, baseDemage: Int): MutableList<BulletComm> {
        var arr: MutableList<BulletComm> = mutableListOf()
        damageRate = 6.0
        arr.add(    //單發
            b.clone(
                x, y,
                v,
                shotMaxRange,
                damageRate * baseDemage
            )
        )
        return arr
    }


}