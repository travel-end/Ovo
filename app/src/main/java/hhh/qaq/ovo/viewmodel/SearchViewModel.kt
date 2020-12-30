package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.os.Bundle
import androidx.databinding.ObservableField
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.event.GlobalEventBus
import hhh.qaq.ovo.utils.getResString
import hhh.qaq.ovo.utils.isNotNullOrEmpty

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchViewModel(app:Application):BaseViewModel(app) {
    val mSearchEtHint = ObservableField(R.string.search_content_hint.getResString())
    val mSearchText = ObservableField("")
    var mShowKeyBoard=ObservableField<Boolean>(true)

    fun toSearch(){

        if (mSearchText.get().isNotNullOrEmpty()) {
            GlobalEventBus.searchEvent.value = mSearchText.get()
//            nav(R.id.hot_to_search_result_fragment, Bundle().apply { putString(Constant.KEY_SEARCH_CONTENT,mSearchText.get()) })
            mShowKeyBoard.set(false)
        }
    }
}