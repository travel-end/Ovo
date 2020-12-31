package hhh.qaq.ovo.viewmodel

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.databinding.MusicItemVM
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.repository.SearchResultRepository
import hhh.qaq.ovo.ui.adapter.MusicItemAdapter
import hhh.qaq.ovo.ui.rv.RvViewModel
import hhh.qaq.ovo.utils.MusicTransformUtil
import hhh.qaq.ovo.utils.isNotNullOrEmpty
import hhh.qaq.ovo.utils.toast

/**
 * @By Journey 2020/12/29
 * @Description
 */
class SearchResultViewModel(app: Application) :
    BaseResViewModel<SearchResultRepository>(app, SearchResultRepository()) {
}

class SearchMusicViewModel(app: Application) :
    BaseResViewModel<SearchResultRepository>(app, SearchResultRepository()) {
    var pageOffset: Int = 1
    var pageSize: Int = 0
    var keyText: String? = ""
    var mData = arrayListOf<MusicItemVM>()
    private val mAdapter = MusicItemAdapter(R.layout.item_music, mData)
    var mRvViewModel = RvViewModel(app).apply {
        mAdapterObservable.set(mAdapter)
        mOnLoadMoreListener = {
            if (pageSize < Constant.SEARCH_SONG_PAGE_SIZE) {
                "没有更多了".toast()
            } else {
                pageOffset++
                requestServer()
            }
        }
    }

    override fun onBindViewModel() {
        super.onBindViewModel()
        request {
            requestServer()
        }
    }

    private fun requestServer() {
        keyText = mBundle?.getString(Constant.KEY_SEARCH_CONTENT)
        if (!keyText.isNullOrBlank()) {
            request {
                val result = repository.searchKeyMusic(keyText!!, pageOffset).data?.song?.list
                if (isNotNullOrEmpty(result)) {
                    pageSize = result!!.size
                    result.forEach {
                        it.mainType = Constant.QQ
                        bindData(MusicTransformUtil.getMusic(it))
                    }
                    mAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun bindData(bean: Music) {
        mData.add(MusicItemVM(getApplication(), bean, keyText ?: "").apply {
            bindData()
        })
    }
}

class SearchAlbumViewModel(app: Application) :
    BaseResViewModel<SearchResultRepository>(app, SearchResultRepository()) {

}