package hhh.qaq.ovo.viewmodel

import android.app.Application
import android.view.View
import hhh.qaq.ovo.BR
import hhh.qaq.ovo.base.BaseViewModel
import hhh.qaq.ovo.listener.OnItemClickListener
import hhh.qaq.ovo.listener.VariableId

/**
 * @By Journey 2020/12/29
 * @Description
 */
open class BaseItemViewModel(app:Application):BaseViewModel(app),OnItemClickListener,VariableId {
    override fun onItemClick() {
    }

    override fun id()=BR.item
}