package obj.Armory

import obj.bullet.BulletComm

class SingleShot(x: Int, y: Int, private var b: BulletComm,allwaysFull:Boolean = true) : Weapon(x, y, allwaysFull, b, false) {

    init {
        shotMaxRange = 1000.0

        ammoMagazine = 10
        ammoMax = 12
        ammo = ammoMax

        shotCD = 20
        reloadCD = 60
    }

    override fun shotAction(x: Int, y: Int, v: Double, baseDemage: Int): MutableList<BulletComm> {
        var arr: MutableList<BulletComm> = mutableListOf()
        damageRate = 1.1
        arr.add(
            b.clone(
                x, y,
                v,// + Random.nextInt(-2, 2),
                shotMaxRange,
                damageRate * baseDemage
            )
        )
        return arr
    }


}