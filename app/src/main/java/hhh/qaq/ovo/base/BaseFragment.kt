package hhh.qaq.ovo.base

import android.content.Context
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
}