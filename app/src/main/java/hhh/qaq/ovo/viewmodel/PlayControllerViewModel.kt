package hhh.qaq.ovo.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.databinding.PlayControllerVM
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.repository.PlayControllerRepository
import hhh.qaq.ovo.ui.adapter.MusicItemAdapter
import hhh.qaq.ovo.ui.rv.RvControllerViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
class PlayControllerViewModel(app: Application) :
    BaseResViewModel<PlayControllerRepository>(app, PlayControllerRepository()) {
    var musicList = mutableListOf<Music>()
    var mIsPlaying = ObservableField<Boolean>(PlayManager.isPlaying())
    var mIsShowController = ObservableField<Boolean>(PlayManager.getPlayingMusic() != null)
    var mData = mutableListOf<PlayControllerVM>()
    private val mControllerAdapter = MusicItemAdapter(R.layout.item_bottom_music, mData)
    var mPlayControllerRv = RvControllerViewModel(app).apply {
        mAdapterObservable.set(mControllerAdapter)
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        setMusicList()
    }

    fun setMusicList() {
        musicList.clear()
        musicList.addAll(PlayManager.getPlayList())
        musicList.forEach {
            mData.add(PlayControllerVM(getApplication(), it).apply {
                bindData()
            })
            mControllerAdapter.notifyDataSetChanged()
        }
    }
}