package hhh.qaq.ovo.listener

import androidx.lifecycle.Observer
import hhh.qaq.ovo.base.BaseActivity
import hhh.qaq.ovo.base.BaseViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
interface ViewEventListener {
    fun onEvent()
    fun BaseViewModel.finish(baseActivity: BaseActivity) {
        isFinish.observe(baseActivity, Observer {
            if (it) {
                baseActivity.finish()
                isFinish.value = false
            }
        })
    }

    fun BaseViewModel.onBackPressed(baseActivity: BaseActivity) {
        onBackPress.observe(baseActivity, Observer {
            if (it) {
                baseActivity.onBackPressed()
                onBackPress.value = false
            }
        })
    }
}