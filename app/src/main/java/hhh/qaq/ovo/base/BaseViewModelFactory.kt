package hhh.qaq.ovo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @By Journey 2020/12/25
 * @Description
 */
class BaseViewModelFactory(private val vm:BaseResViewModel<*>):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return vm as T
    }
}