package hhh.qaq.ovo.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import hhh.qaq.ovo.R
import hhh.qaq.ovo.app.OvoApp
import hhh.qaq.ovo.constant.Constant
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * @By Journey 2020/12/31
 * @Description
 */
object BitmapUtil {
    fun blurBitmap(bitmap: Bitmap, inSampleSize: Int): Drawable {
        val rs = RenderScript.create(OvoApp.getInstance())
        val options = BitmapFactory.Options()
        options.inSampleSize = inSampleSize
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos)
        val byte = baos.toByteArray()
        val bais = ByteArrayInputStream(byte)
        val blurTemplate = BitmapFactory.decodeStream(bais, null, options)
        val input = Allocation.createFromBitmap(rs, blurTemplate)
        val output = Allocation.createTyped(rs, input.type)
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        script.setRadius(20f)
        script.setInput(input)
        script.forEach(output)
        output.copyTo(blurTemplate)
        return BitmapDrawable(OvoApp.getInstance().resources, blurTemplate)
    }

    fun getCoverDrawable(bitmap: Bitmap?): Drawable? {
        val mScreenWidth = screenWidth
        if (bitmap == null) return null
        val discSize = (mScreenWidth * Constant.SCALE_DISC_SIZE).toInt()
        val musicPicSize = (mScreenWidth * Constant.SCALE_MUSIC_PIC_SIZE).toInt()
        val bitmapDisc = Bitmap.createScaledBitmap(
            BitmapFactory.decodeResource(OvoApp.getInstance().resources, R.drawable.gray_border),
            discSize,
            discSize,
            false
        )
        val bitmapMusicPic = Bitmap.createScaledBitmap(bitmap, musicPicSize, musicPicSize, true)
        val discDrawable = BitmapDrawable(bitmapDisc)
        val roundMusicDrawable =
            RoundedBitmapDrawableFactory.create(OvoApp.getInstance().resources, bitmapMusicPic)
        discDrawable.setAntiAlias(true)
        roundMusicDrawable.setAntiAlias(true)
        val drawables = arrayOfNulls<Drawable>(2)
        drawables[0] = roundMusicDrawable
        drawables[1] = discDrawable
        val layerDrawable = LayerDrawable(drawables)
        val musicPicMargin =
            ((Constant.SCALE_DISC_SIZE - Constant.SCALE_MUSIC_PIC_SIZE) * mScreenWidth / 2).toInt()
        layerDrawable.setLayerInset(
            0,
            musicPicMargin,
            musicPicMargin,
            musicPicMargin,
            musicPicMargin
        )
        return layerDrawable
    }

    fun getCoverDrawable2(bitmap: Bitmap?): Drawable? {
        val mScreenWidth = screenWidth
        if (bitmap == null) return null
//        val discSize = (mScreenWidth * Constant.SCALE_DISC_SIZE).toInt()

        val musicPicSize = (mScreenWidth * Constant.SCALE_MUSIC_PIC_SIZE2).toInt()

//        val bitmapDisc = Bitmap.createScaledBitmap(
//            BitmapFactory.decodeResource(OvoApp.getInstance().resources, R.drawable.ic_disc_border),discSize,discSize,false
//        )

        val bitmapMusicPic = Bitmap.createScaledBitmap(bitmap, musicPicSize, musicPicSize, true)

//        val discDrawable = BitmapDrawable(bitmapDisc)

        val roundMusicDrawable =
            RoundedBitmapDrawableFactory.create(OvoApp.getInstance().resources, bitmapMusicPic)
//        discDrawable.setAntiAlias(true)
        roundMusicDrawable.setAntiAlias(true)
//        val drawables = arrayOfNulls<Drawable>(2)
//        drawables[0] = roundMusicDrawable
//        drawables[1] = discDrawable

//        val layerDrawable = LayerDrawable(drawables)
//        val musicPicMargin = ((Constant.SCALE_DISC_SIZE - Constant.SCALE_MUSIC_PIC_SIZE) * mScreenWidth / 2).toInt()
//        layerDrawable.setLayerInset(0,musicPicMargin,musicPicMargin,musicPicMargin,musicPicMargin)
        return roundMusicDrawable
    }
}