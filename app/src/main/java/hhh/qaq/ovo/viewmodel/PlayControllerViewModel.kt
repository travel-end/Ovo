package hhh.qaq.ovo.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.databinding.PlayControllerVM
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.repository.PlayControllerRepository
import hhh.qaq.ovo.ui.adapter.MusicItemAdapter
import hhh.qaq.ovo.ui.rv.RvControllerViewModel
import hhh.qaq.ovo.ui.rv.RvViewHolder
import hhh.qaq.ovo.utils.log

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
    var mAdapterObservable: ObservableField<RecyclerView.Adapter<RvViewHolder>> = ObservableField(mControllerAdapter)
    var mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(app,
        LinearLayoutManager.HORIZONTAL,false)

    override fun onBindViewModel() {
        super.onBindViewModel()
        setMusicList()
    }

    fun playPause() {
        PlayManager.playPause()
    }

    fun setMusicList() {
        musicList.clear()
        musicList.addAll(PlayManager.getPlayList())
        musicList.forEach {
            mData.add(PlayControllerVM(getApplication(), it).apply {
                bindData()
            })
        }
        mControllerAdapter.notifyDataSetChanged()
    }
}