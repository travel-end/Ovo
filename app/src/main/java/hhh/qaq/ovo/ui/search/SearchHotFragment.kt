package hhh.qaq.ovo.ui.search

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.viewmodel.SearchHotViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchHotFragment:BaseVMRepositoryFragment<SearchHotViewModel>(R.layout.fragment_search_hot) {
    override fun initViewModel(app: Application)=SearchHotViewModel(app)
}