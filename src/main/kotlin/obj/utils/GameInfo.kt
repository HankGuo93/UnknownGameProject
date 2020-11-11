package obj.utils

import java.lang.System.nanoTime

class GameInfo(
    var money: Int = 0, var spendAmmo: Int = 0,
    var killCount: Int = 0, var distance: Int = 0,
    var level: Int = 1, var timer: Timer = Timer(nanoTime()),
) {

    fun getPassedTime(): String {
        return timer.timeCount(nanoTime())
    }

    fun getTotalPoint(): Int {
        return spendAmmo * 1 + distance / 100 * 10 + killCount * 7
    }

    fun update() {
        if (this.getTotalPoint() >= 2000) {
            level = 3
        } else if (this.getTotalPoint() >= 1000) {
            level = 2
        }
    }

    override fun toString(): String {
        var level: Int = 0
        level = when {
            (this.getTotalPoint() <= 600) -> 1
            (this.getTotalPoint() <= 1500) -> 2
            (this.getTotalPoint() <= 3000) -> 3
            (this.getTotalPoint() <= 5000) -> 4
            else -> 5
        }
        return "$level $spendAmmo ${distance / 100} $killCount ${getTotalPoint()}"
    }
}