package core.utils

import java.awt.Font
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream


class AddFont  {
    fun createFont(): Font? {
        var ttfBase: Font? = null;
        try {
            var myStream = BufferedInputStream(
                javaClass.getResourceAsStream(FONT_PATH_TELEGRAFICO)
            )
            ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream)
        } catch (ex: Exception) {
            ex.printStackTrace()
            System.err.println("Font not loaded.")
        }
        return ttfBase
    }

    companion object {
        private var ttfBase: Font? = null
        private var telegraficoFont: Font? = null
        private var myStream: InputStream? = null
//        private const val FONT_PATH_TELEGRAFICO = "C:\\Users\\user\\OneDrive\\文件\\game-project\\src\\main\\resources\\font\\pixelFJ8pt1__.TTF"
        private const val FONT_PATH_TELEGRAFICO = "\\font\\pixelFJ8pt1__.TTF"
    }
}