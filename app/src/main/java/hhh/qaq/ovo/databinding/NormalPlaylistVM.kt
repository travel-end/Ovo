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
    var mRippleViewClickListener = RippleView.OnRippleCompleteListener {
        "position:$position ,url:${music?.coverUri}".log()
        music?.let {
            PlayManager.playOnline(music)

        }
    }
    fun onMoreClick() {
        "moreClick".log()
    }
    fun bindData() {

    }
}