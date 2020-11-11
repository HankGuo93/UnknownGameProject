package obj.utils

class Timer(var nanoFirst: Long = 0) {
    private var hour: Int = 0
    private var minute: Int = 0
    private var second: Int = 0
    var totalSecond: Int = 0

    fun timeCount(nanoLast: Long) : String{
        var timeCounter: Long = nanoLast - nanoFirst
        timeCounter /= 1000000000
        hour = (timeCounter/3600).toInt()
        minute = ((timeCounter-hour*3600)/60).toInt()
        second = (timeCounter-hour*3600-minute*60).toInt()
        return this.toString()
    }

    fun timeSecond(nanoLast: Long): Long{
        var timeCounter: Long = nanoLast - nanoFirst
        timeCounter /= 1000000000
        return timeCounter
    }

    override fun toString(): String{
        val formattedHour = String.format("%02d", hour)
        val formattedMinute = String.format("%02d", minute)
        val formattedSecond = String.format("%02d", second)
        return "$formattedHour : $formattedMinute : $formattedSecond"
    }
}