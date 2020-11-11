package core

import core.GameKernel
import obj.utils.GameInfo

abstract class Scene(open var gameInfo: GameInfo) : GameKernel.GameInterface {
    abstract fun sceneBegin()

    abstract fun sceneEnd()

    protected fun countStringDistance(int: Int): Int{
        var tmp = int
        var count: Int = 0
        while(tmp/10 != 0 ){
            tmp /= 10
            count++
        }
        return count
    }

}