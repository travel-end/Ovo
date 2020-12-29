package hhh.qaq.ovo.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class BaseFragment:Fragment() {
    lateinit var mActivity: BaseActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBundle()
    }

    private fun initBundle() {
        arguments?.let {
            getBundle(it)
        }
    }
    open fun getBundle(bundle: Bundle) {

    }
}