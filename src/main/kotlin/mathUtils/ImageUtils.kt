package mathUtils

import java.awt.Graphics2D
import java.awt.Image
import java.awt.RenderingHints
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage


class  ImageUtils {
    companion object {
        //awt image rotation  >0,/\90,<180,\/ 270
        fun imageRotate(image: Image,degree:Int): Image? {
            var img = image
            img = ImageUtils.bufferedImageRotate(img as BufferedImage,degree)!!
            return img
        }

        //bufferedImage rotation
        fun bufferedImageRotate(bufferedImage: BufferedImage,degree:Int):BufferedImage?{

//            var d = 360 - degree
            val w: Int = bufferedImage.width
            val h: Int = bufferedImage.height
            val type: Int = bufferedImage.colorModel.transparency
            var img: BufferedImage?
            var g2d: Graphics2D
            BufferedImage(w, h, type).also { img = it }.createGraphics().also { g2d = it }
                .setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
                )
            g2d.rotate(Math.toRadians(degree.toDouble()), w / 2.toDouble(), h / 2.toDouble())
            g2d.drawImage(bufferedImage, 0, 0, null)
            g2d.dispose()
            return img
        }

        //upside down
        fun imageVFlip(image: Image):Image{
            var img = bufferedImageVFlip(image as BufferedImage)
            return img
        }
        fun bufferedImageVFlip(bufferedImage: BufferedImage):BufferedImage{
            // Flip the image vertically
            val tx = AffineTransform.getScaleInstance(1.0, -1.0)
            tx.translate(0.0, (-bufferedImage.getHeight(null)).toDouble())
            val op = AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
            var img = op.filter(bufferedImage, null)
            return img
        }
        //
        fun imageHFlip(image: Image):Image{
            var img = bufferedImageHFlip(image as BufferedImage)
            return img
        }
        fun bufferedImageHFlip(bufferedImage: BufferedImage):BufferedImage{
            // Flip the image vertically
            val tx = AffineTransform.getScaleInstance(-1.0, 1.0)
            tx.translate((-bufferedImage.getWidth(null)).toDouble(), 0.0)
            val op = AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
            var img = op.filter(bufferedImage, null)
            return img
        }
    }
}