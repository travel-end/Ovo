package hhh.qaq.ovo.listener

import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import hhh.qaq.ovo.base.BaseFragment
import hhh.qaq.ovo.base.BaseViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
interface ViewEventListener {
    fun onEvent()

//    fun BaseViewModel.nav(fragment: BaseFragment){
//        hasNav.observe(fragment,Observer{
//            if (it==true) {
//                val nav = NavHostFragment.findNavController(it)
//                if (bundle != null) {
//                    nav.navigate(actionId,bundle)
//                } else {
//                    nav.navigate(actionId)
//                }
//
//            }
//        })
//    }
}