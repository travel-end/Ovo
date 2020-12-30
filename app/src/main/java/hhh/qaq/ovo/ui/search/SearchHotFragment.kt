package hhh.qaq.ovo.ui.search

import android.app.Application
import androidx.lifecycle.Observer
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.event.GlobalEventBus
import hhh.qaq.ovo.viewmodel.SearchHotViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchHotFragment:BaseVMRepositoryFragment<SearchHotViewModel>(R.layout.fragment_search_hot) {
    override fun initViewModel(app: Application)=SearchHotViewModel(app)
    override fun onAction() {
        super.onAction()
        GlobalEventBus.searchEvent.observeInFragment(this,Observer{
            mViewModel.navigation(it)
        })
    }
}