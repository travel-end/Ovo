package hhh.qaq.ovo.viewmodel

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.databinding.CommonTitleModel
import hhh.qaq.ovo.repository.SearchHotRepository
import hhh.qaq.ovo.utils.getResString

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchHotViewModel(app:Application):BaseResViewModel<SearchHotRepository>(app,SearchHotRepository()) {
    var mTitleVm = CommonTitleModel(
        leftText = R.string.search_history.getResString()
    )

    override fun onBindViewModel() {
        super.onBindViewModel()
        request {
            getHotSearchTag()
            getHistorySearch()
        }
    }

    private suspend fun getHotSearchTag() {
        SearchHotTagViewModel(getApplication(),repository.getHotSearchTag()).apply {
            bindData()
        }
    }
    private suspend fun getHistorySearch() {
        repository.getHistorySearch()
    }


}