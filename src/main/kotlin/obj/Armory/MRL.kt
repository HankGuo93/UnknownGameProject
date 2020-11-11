package obj.Armory

import obj.bullet.BulletComm

class MRL(x: Int, y: Int, b: BulletComm,allwaysFull:Boolean = false) : Weapon(x, y, allwaysFull, b, false) {

    init {
        shotMaxRange = 1600.0

        ammoMagazine = 0
        ammoMax = 8
        ammo = ammoMax

        shotCD = 60
        reloadCD = 120
    }

    override fun shotAction(x: Int, y: Int, v: Double, baseDemage: Int): MutableList<BulletComm> {
        var arr: MutableList<BulletComm> = mutableListOf()
        damageRate = 6.0
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

}