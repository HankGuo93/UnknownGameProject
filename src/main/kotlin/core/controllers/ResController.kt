package core.controllers

import java.awt.Image
import javax.imageio.ImageIO


class ResController private constructor() {
    private val map: MutableMap<String, Image> = mutableMapOf()
    private val keepMap: MutableMap<String, Image> = mutableMapOf()
    private var isKeep = false

    fun image(path: String): Image {
        return if (isKeep) {
            keepMap
        } else {
            map
        }.run {
            getOrElse(path){
                val img: Image = ImageIO.read(javaClass.getResource(path))
                this[path] = img
                return img
            }
        }
    }

    fun keep() {
        isKeep = true
    }

    fun release() {
        map.putAll(keepMap)
        drop()
    }

    fun drop() {
        keepMap.clear()
        isKeep = false
    }

    fun clear() {
        map.clear()
    }

    companion object {
        val instance: ResController by lazy { ResController() }
    }
}
