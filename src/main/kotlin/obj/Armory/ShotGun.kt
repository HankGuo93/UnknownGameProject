package obj.Armory

import obj.bullet.BulletComm
import kotlin.random.Random

class ShotGun(x: Int, y: Int, b: BulletComm,allwaysFull:Boolean = false) : Weapon(x, y, allwaysFull, b, false) {

    init {
        shotMaxRange = 600.0

        ammoMagazine = 1
        ammoMax = 5
        ammo = ammoMax

        shotCD = 20
        reloadCD = 10
    }

    override fun shotAction(x: Int, y: Int, v: Double, baseDemage: Int): MutableList<BulletComm> {
        var arr: MutableList<BulletComm> = mutableListOf()
        var vRandom = Random.nextInt(-10, 11)
        damageRate = 1.0
        for (i in 0..8) {
            var tmpR = Random.nextInt(-35, 35)
            arr.add(
                bullet.clone(
                    x + Random.nextInt(-10, 11), y,
                    v + vRandom + Random.nextInt(-10, 11),
                    shotMaxRange + tmpR,
                    damageRate * baseDemage
                )
            )
        }
        return arr
    }

}