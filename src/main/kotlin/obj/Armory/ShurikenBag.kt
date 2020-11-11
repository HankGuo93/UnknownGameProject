package obj.Armory

import obj.bullet.BulletComm
import kotlin.random.Random

class ShurikenBag(x: Int, y: Int, b: BulletComm,allwaysFull:Boolean = true) : Weapon(x, y, allwaysFull, b, false) {

    init {
        shotMaxRange = 1600.0

        ammoMagazine = 10
        ammoMax = 20
        ammo = ammoMax

        shotCD = 20
        reloadCD = 60
    }

    override fun shotAction(x: Int, y: Int, v: Double, baseDemage: Int): MutableList<BulletComm> {
        var arr: MutableList<BulletComm> = mutableListOf()
        damageRate = 1.1
        arr.add(
            bullet.clone(
                x, y,
                v + Random.nextInt(-20, 21),
                shotMaxRange,
                damageRate * baseDemage
            )
        )
        return arr
    }


}