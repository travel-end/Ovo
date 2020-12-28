package hhh.qaq.ovo.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.utils.getResString

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchViewModel(app:Application):BaseViewModel(app) {
    val mSearchEtHint = ObservableField(R.string.search_content_hint.getResString())
    val mSearchText = ObservableField("")

    override fun onBindViewModel() {
        super.onBindViewModel()

    }


}