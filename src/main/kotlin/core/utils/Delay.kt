package core.utils

class Delay(var countLimit: Int){

    var count = 0
    private var isPause = true
    private var isLoop = false

    fun stop() {
        isPause = true
        isLoop = false
        count = 0
    }

    fun pause() {
        isPause = true
    }

    fun play() {
        isPause = false
        isLoop = false
    }

    fun loop() {
        isPause = false
        isLoop = true
    }

    fun count(): Boolean {
        if (isPause) {
            return false
        }
        if (count >= countLimit) {
            if (isLoop) {
                count = 0
            } else {
                stop()
            }
            return true
        }
        count++
        return false
    }
}