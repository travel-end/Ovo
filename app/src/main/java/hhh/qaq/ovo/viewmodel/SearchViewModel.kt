package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.event.GlobalEventBus
import hhh.qaq.ovo.utils.getResString
import hhh.qaq.ovo.utils.isNotNullOrEmpty

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchViewModel(app: Application) : BaseViewModel(app) {
    val mSearchEtHint = ObservableField(R.string.search_content_hint.getResString())
    val mSearchText = ObservableField("")
    var mShowKeyBoard = ObservableField<Boolean>(true)
    fun toSearch(v: View) {
        if (mSearchText.get().isNotNullOrEmpty()) {
            GlobalEventBus.searchEvent.value = mSearchText.get()
            mShowKeyBoard.set(false)
        }
    }
}