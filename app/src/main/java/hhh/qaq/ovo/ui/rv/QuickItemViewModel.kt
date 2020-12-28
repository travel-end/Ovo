package hhh.qaq.ovo.ui.rv

import android.app.Application
import android.view.View
import hhh.qaq.ovo.BR
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.listener.OnItemClickListener
import hhh.qaq.ovo.listener.VariableId

/**
 * @By Journey 2020/12/28
 * @Description
 */
abstract class QuickItemViewModel(app:Application):BaseViewModel(app),OnItemClickListener,VariableId {
    override fun onItemClick(view: View) {
    }

    override fun id()=BR.item
}