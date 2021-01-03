package hhh.qaq.ovo.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.databinding.NormalPlaylistVM
import hhh.qaq.ovo.repository.MusicListRepository
import hhh.qaq.ovo.ui.adapter.MusicItemAdapter
import hhh.qaq.ovo.ui.rv.RvViewHolder

class MusicListViewModel(app:Application):BaseResViewModel<MusicListRepository>(app,MusicListRepository()) {
    private val mData = mutableListOf<NormalPlaylistVM>()
    private val mAdapter = MusicItemAdapter(R.layout.item_music,mData)
    var mAdapterObservable: ObservableField<RecyclerView.Adapter<RvViewHolder>> = ObservableField()

    override fun onBindViewModel() {
        super.onBindViewModel()
        request {
            val localMusics = repository.getLocalMusics(getApplication())
            if (localMusics.isNotEmpty()) {
                localMusics.forEach {
                    mData.add(NormalPlaylistVM(getApplication(),it).apply {
                        bindData()
                    })
                }
                mAdapterObservable.set(mAdapter)
            }
        }
    }
}