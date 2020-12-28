package hhh.qaq.ovo.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @By Journey 2020/12/25
 * @Description
 */
class BaseViewModelFactory(app:Application,private val vm:BaseResViewModel<*>):ViewModelProvider.AndroidViewModelFactory(app) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return vm as T
    }
}