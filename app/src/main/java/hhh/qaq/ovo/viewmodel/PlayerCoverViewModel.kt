package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.app.OvoApp
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.utils.BitmapUtil
import hhh.qaq.ovo.utils.getDrawable
import hhh.qaq.ovo.utils.loadBigImageView

/**
 * @By Journey 2020/12/31
 * @Description
 */
class PlayerCoverViewModel(app: Application) : BaseViewModel(app) {
    var mCoverDrawable = ObservableField<Drawable>(R.drawable.disk.getDrawable())
    override fun onBindViewModel() {
        super.onBindViewModel()
        // 通过Parcelable传bitmap，可能根据网络情况而定,如果没有则通过Glide加载获取
        val coverBitmap = mBundle?.getParcelable<Bitmap>(Constant.KEY_COVER_BITMAP)
        if (coverBitmap != null) {
            mCoverDrawable.set(BitmapUtil.getCoverDrawable(coverBitmap))
        } else {
            loadBigImageView(getApplication(), PlayManager.getPlayingMusic()) {
                mCoverDrawable.set(BitmapUtil.getCoverDrawable(it))
            }
        }
    }
}