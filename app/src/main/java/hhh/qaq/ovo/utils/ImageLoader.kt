package hhh.qaq.ovo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import hhh.qaq.ovo.R
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.model.Music

/**
 * @By Journey 2020/10/26
 * @Description
 */
fun ImageView.loadImg(
    url: String,
    placeholder: Int = R.drawable.ic_default_cover,
    error: Int = R.drawable.ic_default_cover
) {
    val option = RequestOptions()
        .placeholder(placeholder)
        .error(error)
    Glide
        .with(this.context)
        .load(url)
        .apply(option)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun loadImgOfReady(
    context: Context,
    url: String,
    placeholder: Int = R.drawable.ic_default_cover,
    error: Int = R.drawable.ic_default_cover,
    block: (resource: Drawable) -> Unit
) {
    Glide
        .with(context)
        .load(url)
        .apply(RequestOptions.placeholderOf(placeholder).error(error))
        .into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                block.invoke(resource)
            }
        })
}

// 显示播放页大图
fun loadBigImageView(context: Context?, music: Music?, callBack: ((Bitmap) -> Unit)?) {
    if (music == null) return
    if (context == null) return
    val url = if (music.type==Constant.QQ) {
        music.coverUri
    } else {
        MusicTransformUtil.getAlbumPic(music.coverUri, music.type, MusicTransformUtil.PIC_SIZE_BIG)
    }
    Glide.with(context)
        .asBitmap()
        .load(url ?: R.drawable.ic_default_cover)
        .error(R.drawable.ic_default_cover)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into<CustomTarget<Bitmap>>(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {

            }
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                callBack?.invoke(resource)
            }
        })

}

fun ImageView.loadImgOfError(url: String, errorBlock: () -> Unit) {
    Glide.with(this.context)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                errorBlock.invoke()
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(this)
}

fun getLocalSongCover(context: Context,albumId:String):String?{
    if (albumId=="-1")return null
    var uri: String? = null
    try {
        val cursor = context.contentResolver.query(
            Uri.parse("content://media/external/audio/albums/$albumId"),
            arrayOf("album_art"), null, null, null)
        if (cursor != null) {
            cursor.moveToNext()
            uri = cursor.getString(0)
            cursor.close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return uri
}