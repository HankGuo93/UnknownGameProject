package obj.Armory

import obj.bullet.BulletComm
import kotlin.random.Random

class BurstShot(x: Int, y:Int , b:BulletComm,allwaysFull:Boolean = false) : Weapon(x, y, allwaysFull,b,true) {

    init {
        shotMaxRange =1600.0

        ammoMagazine = 1
        ammoMax = 30
        ammo = ammoMax
        shotCD = 10
        reloadCD = 60
    }

    override fun shotAction(x: Int, y: Int, v: Double,baseDemage:Int): MutableList<BulletComm> {
        var arr : MutableList<BulletComm> = mutableListOf()
        var tmpR = Random.nextInt(-5,5)
        damageRate = 1.2
        arr.add(bullet.clone(x, y,
            v+ Random.nextInt(-2,2),
            shotMaxRange+tmpR,
            damageRate*baseDemage))
        return arr
    }
}