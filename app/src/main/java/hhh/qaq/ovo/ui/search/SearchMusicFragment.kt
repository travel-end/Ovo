package hhh.qaq.ovo.ui.search

import android.app.Application
import android.os.Bundle
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.viewmodel.SearchMusicViewModel

/**
 * @By Journey 2020/12/29
 * @Description
 */
class SearchMusicFragment:BaseVMRepositoryFragment<SearchMusicViewModel>(R.layout.fragment_search_music) {
    companion object {
        fun newInstance(searchText: String): SearchMusicFragment {
            val fragment = SearchMusicFragment()
            val bundle = Bundle().apply {
                putString(Constant.KEY_SEARCH_CONTENT, searchText)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
    override fun initViewModel(app: Application)=SearchMusicViewModel(app)
}