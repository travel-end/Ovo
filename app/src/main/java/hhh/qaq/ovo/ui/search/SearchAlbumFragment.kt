package hhh.qaq.ovo.ui.search

import android.app.Application
import android.os.Bundle
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.viewmodel.SearchAlbumViewModel
import hhh.qaq.ovo.viewmodel.SearchMusicViewModel

/**
 * @By Journey 2020/12/29
 * @Description
 */
class SearchAlbumFragment:BaseVMRepositoryFragment<SearchAlbumViewModel>(R.layout.view_rv) {
    companion object {
        fun newInstance(searchText:String):SearchAlbumFragment {
            val fragment = SearchAlbumFragment()
            val bundle = Bundle().apply {
                putString(Constant.KEY_SEARCH_CONTENT,searchText)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun initViewModel(app: Application)=SearchAlbumViewModel(app)
}