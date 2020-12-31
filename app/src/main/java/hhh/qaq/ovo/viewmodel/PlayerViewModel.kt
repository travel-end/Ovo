package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.utils.BitmapUtil
import hhh.qaq.ovo.utils.StringUtil
import hhh.qaq.ovo.utils.getDrawable
import hhh.qaq.ovo.utils.getResString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @By Journey 2020/12/30
 * @Description
 */
class PlayerViewModel(app: Application) : BaseViewModel(app) {
    var mMusicName = ObservableField("")
    var mMusicSinger = ObservableField("")
    var mIsLovedMusic = ObservableBoolean()
    var mIsMusicPlaying = ObservableBoolean()
    var mBgDrawable = ObservableField<Drawable>(R.drawable.shape_gradient_blue.getDrawable())
    var isPlaying = PlayManager.isPlaying()
    var mTotalDuration = ObservableField<Int>(0)
    var mTotalTextDuration = ObservableField<String>(R.string.play_time_start.getResString())
    var mPlaySeekBarProgress = ObservableField<Int>(0)
    var mPlayCurrentTextProgress = ObservableField<String>(R.string.play_time_start.getResString())

    fun setPlayingMusicInfo(music: Music?) {
        mIsMusicPlaying.set(isPlaying)
        if (music != null) {
            mMusicName.set(music.title)
            mMusicSinger.set(music.artist)
            mIsLovedMusic.set(music.isLove)
        }
    }

    fun setPlayBgDrawable(bitmap: Bitmap?) {
        if (bitmap != null) {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    BitmapUtil.blurBitmap(bitmap, 10)
                }
                mBgDrawable.set(result)
            }
        }
    }

    fun setProgressBarMax(max: Int) {
        mTotalDuration.set(max)
        mTotalTextDuration.set(StringUtil.formatProgress(max.toLong()))
    }

    fun updatePlayProgress(progress: Long) {
        mPlaySeekBarProgress.set(progress.toInt())
        mPlayCurrentTextProgress.set(StringUtil.formatProgress(progress))
    }
}