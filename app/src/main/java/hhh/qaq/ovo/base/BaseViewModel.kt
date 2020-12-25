package hhh.qaq.ovo.base

import androidx.lifecycle.ViewModel
import hhh.qaq.ovo.BR
import hhh.qaq.ovo.listener.VariableId

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class BaseViewModel:ViewModel(),VariableId {

    open fun onBindViewModel() {

    }

    override fun id()=BR.vm
}