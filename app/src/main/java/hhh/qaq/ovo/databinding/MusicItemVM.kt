package hhh.qaq.ovo.databinding

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.viewmodel.BaseItemViewModel
import hhh.qaq.ovo.widget.RippleView

/**
 * @By Journey 2020/12/29
 * @Description
 */
class MusicItemVM(app:Application,private val music: Music?=null,private val keyWord:String):BaseItemViewModel(app) {
    var mMusicName= ObservableField("")
    var mIsPlaying = ObservableField(false)
    var mHasDownloaded = ObservableField(false)
    var mMusicSinger= ObservableField("")
    var mMusicAlbumName= ObservableField("")
    var mMusicLyric= ObservableField("")
    var mDiffColor = ObservableField(DiffViewModel())

    var mRippleViewClick = RippleView.OnRippleCompleteListener {

    }

    fun bindData() {
        setMusicName()
        setMusicSinger()
        showIsPlaying()
        showHasDownloaded()
        setMusicAlbumName()
        setMusicLyric()
    }

    fun setMusicName() {
        mMusicName.set(music?.title)
        mDiffColor.set(DiffViewModel().apply {
            appointStr.set(keyWord)
            originalStr.set(music?.title)
        })
    }

    fun setMusicSinger() {
        mMusicSinger.set(music?.artist)
        mDiffColor.set(DiffViewModel().apply {
            appointStr.set(keyWord)
            originalStr.set(music?.artist)
        })
    }
    fun showIsPlaying() {
//        mIsPlaying.set()
    }

    fun showHasDownloaded() {

    }
    fun setMusicAlbumName() {
        mMusicAlbumName.set(music?.album)
        mDiffColor.set(DiffViewModel().apply {
            appointStr.set(keyWord)
            originalStr.set(music?.album)
        })
    }
    fun setMusicLyric() {
        mMusicLyric.set(music?.lyric)
    }
}