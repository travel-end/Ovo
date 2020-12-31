package hhh.qaq.ovo.databinding

import android.app.Application
import androidx.databinding.ObservableField
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.ui.player.PlayerActivity
import hhh.qaq.ovo.utils.isNotNullOrEmpty
import hhh.qaq.ovo.utils.log
import hhh.qaq.ovo.viewmodel.BaseItemViewModel

/**
 * @By Journey 2020/12/30
 * @Description
 */
class PlayControllerVM(app: Application, private val music: Music?=null):BaseItemViewModel(app) {

    var mCoverUrl = ObservableField<String>()
    var mMusicName = ObservableField<String>()
    var mMusicSinger = ObservableField<String>()
    fun bindData() {
        setMusicCover()
        setMusicName()
        setMusicSinger()
    }

    override fun onItemClick() {
        super.onItemClick()
        "${music?.title}".log()
        startAct(PlayerActivity::class.java)
    }

    fun setMusicCover() {
        music?.let {
            if (it.coverUri.isNotNullOrEmpty()) {
                mCoverUrl.set(it.coverUri)
            }
        }
    }
    fun setMusicName() {
        mMusicName.set(music?.title)
    }

    fun setMusicSinger() {
        mMusicSinger.set(music?.artist)
    }
}