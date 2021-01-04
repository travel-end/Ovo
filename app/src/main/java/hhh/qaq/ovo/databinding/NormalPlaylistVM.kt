package hhh.qaq.ovo.databinding

import android.app.Application
import androidx.databinding.ObservableField
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.utils.log
import hhh.qaq.ovo.viewmodel.BaseItemViewModel
import hhh.qaq.ovo.widget.RippleView

class NormalPlaylistVM(app:Application,private val music: Music?=null):BaseItemViewModel(app) {
    var mMusicName= ObservableField("")
    var mIsPlaying = ObservableField(false)
    var mMusicSinger= ObservableField("")
    var mMusicAlbumName= ObservableField("")

    var mRippleViewClickListener : RippleView.OnRippleCompleteListener ?=null
    fun onMoreClick() {
        "moreClick".log()
    }


    fun bindData() {
        setMusicName()
        setMusicSinger()
        showIsPlaying()
        setMusicAlbumName()
    }
    fun setOnRippleViewClickListener(listener:RippleView.OnRippleCompleteListener?) {
        mRippleViewClickListener = listener
    }

    fun setMusicName() {
        mMusicName.set(music?.title)
    }

    fun setMusicSinger() {
        mMusicSinger.set(music?.artist)
    }
    fun showIsPlaying() {
    }
    fun setMusicAlbumName() {
        mMusicAlbumName.set(music?.album)
    }
}