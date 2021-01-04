package hhh.qaq.ovo.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.databinding.NormalPlaylistVM
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.repository.MusicListRepository
import hhh.qaq.ovo.ui.adapter.MusicItemAdapter
import hhh.qaq.ovo.ui.rv.RvViewHolder
import hhh.qaq.ovo.utils.isNotNullOrEmpty
import hhh.qaq.ovo.utils.log
import hhh.qaq.ovo.widget.RippleView

class MusicListViewModel(app:Application):BaseResViewModel<MusicListRepository>(app,MusicListRepository()) {
    private val mData = mutableListOf<NormalPlaylistVM>()
    private val mAdapter = MusicItemAdapter(R.layout.item_normal_music,mData)
    var mAdapterObservable: ObservableField<RecyclerView.Adapter<RvViewHolder>> = ObservableField()

    override fun onBindViewModel() {
        super.onBindViewModel()
        val musicType = mBundle?.getString(Constant.KEY_MUSIC_TYPE)
        request {
            val normalMusics = repository.getNormalMusics(getApplication(),musicType)
            if (!normalMusics.isNullOrEmpty()) {
                normalMusics.forEach {
                    mData.add(NormalPlaylistVM(getApplication(),it).apply {
                        bindData()
                        setOnRippleViewClickListener(RippleView.OnRippleCompleteListener {
                            if (musicType.isNotNullOrEmpty()) {
                                PlayManager.play(position,normalMusics,musicType!!)
                            }
                        })
                    })
                }
                mAdapterObservable.set(mAdapter)
            }
        }
    }
}