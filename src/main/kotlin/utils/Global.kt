package utils

import mathUtils.Coordinate
import mathUtils.Vector
import java.awt.Font
import java.io.File

object Global {

    enum class Direction(val value: Int) {
        UP(3),
        DOWN(0),
        LEFT(1),
        RIGHT(2)
    }

    const val IS_DEBUG = false

    fun log(str: String) {
        if (IS_DEBUG) {
            println(str)
        }
    }

    internal class Command{
        val CONNECT: Int = 0;
        val MOVE: Int = 1;
    }

    //單位大小
    const val UNIT_X = 32
    const val UNIT_Y = 32
    const val scale = 1.5

    // 視窗大小
    const val WINDOW_WIDTH = 1600
    const val WINDOW_HEIGHT = 900
    const val SCREEN_X = WINDOW_WIDTH - 8 - 8
    const val SCREEN_Y = WINDOW_HEIGHT - 30 - 8
    const val SCREEN_CENTER_X = SCREEN_X / 2

    const val MapObject: Int = 32

    // 資料刷新時間
    const val UPDATE_FREQ = 60 // 每秒更新60次遊戲邏輯

    // 畫面更新時間
    const val PAINT_FREQ = 60

    const val ROLE_SPEED = 3
    val c =
            run {
                Coordinate.vectorToXY(Vector(ROLE_SPEED.toDouble(), 45.0))
            }

}