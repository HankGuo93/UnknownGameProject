package obj.background

import utils.Global
import kotlin.random.Random

class StairObjectCreator(var x: Int = Global.WINDOW_WIDTH*15/10,
                         var y: Int = 100,
                         var amount: Int,
                         var distance: Int,
                         private val heelHeight: Int,
                         private var random: Int) {

    private val mapProductList: MutableList<MutableList<StairObject>> = mutableListOf()
    fun mapObjectCreator(): MutableList<MutableList<StairObject>> {
        for (a in 0 until amount) {
            var mapObject: MutableList<StairObject> = mutableListOf()
            var tmpType: StairObject.StairType = StairObject.StairType.FLOOR
            var middleFloor = Random.nextInt(2, random)
            var heelHeightRandom = Random.nextInt(heelHeight)+1
            var height: Int = 1

            for (j in 0 until heelHeightRandom) {
                for (i in 0 until height) {
                    if (i == height - 1)
                        mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR_UP).apply { this.judgeable = true })
                    else
                        mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR))
                    y -= Global.MapObject
                }
                height++
                x += Global.MapObject
            }
            y = 60
            for (j in 0 until middleFloor) {
                for (i in 0 until heelHeightRandom) {
                    if (i == height - 1)
                        mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR).apply { this.judgeable = true })
                    else
                        mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR))

                    y -= Global.MapObject
                }
                x += Global.MapObject
            }
            height--
            for (j in 0 until heelHeightRandom) {
                for (i in 0 until height) {
                    if (i == height - 1)
                        mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR_DOWN).apply { this.judgeable = true })
                    else
                        mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR))
                    y -= Global.MapObject
                }
                height--
                x += Global.MapObject
            }
            x += distance * Global.MapObject
            mapProductList.add(mapObject)
        }
        return mapProductList;
    }

    fun addMapObject(): MutableList<StairObject> {
        var mapObject: MutableList<StairObject> = mutableListOf()
        var tmpType: StairObject.StairType = StairObject.StairType.FLOOR
        var middleFloor = Random.nextInt(2, random)
        var heelHeightRandom = Random.nextInt(heelHeight)+1
        var height: Int = 1

        for (j in 0 until heelHeightRandom) {
            for (i in 0 until height) {
                if (i == height - 1)
                    mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR_UP).apply { this.judgeable = true })
                else
                    mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR))
                y -= Global.MapObject
            }
            height++
            x += Global.MapObject
        }
        y = 60
        for (j in 0 until middleFloor) {
            for (i in 0 until heelHeightRandom) {
                if (i == height - 1)
                    mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR).apply { this.judgeable = true })
                else
                    mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR))
                y -= Global.MapObject
            }
            x += Global.MapObject
        }
        height--
        for (j in 0 until heelHeightRandom) {
            for (i in 0 until height) {
                if (i == height - 1)
                    mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR_DOWN).apply { this.judgeable = true })
                else
                    mapObject.add(StairObject(x, y, StairObject.StairType.FLOOR))
                y -= Global.MapObject
            }
            height--
            x += Global.MapObject
        }
        return mapObject;
    }
}