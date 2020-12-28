package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import hhh.qaq.ovo.model.SearchInfo
import hhh.qaq.ovo.ui.rv.QuickItemViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
class SearchHotTagViewModel(app:Application,private val bean:SearchInfo):QuickItemViewModel(app) {

    var mTag = ObservableField<String>()
    override fun onItemClick(view: View) {
        super.onItemClick(view)

    }

    fun bindData() {

    }
}