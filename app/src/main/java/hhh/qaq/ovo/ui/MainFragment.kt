package hhh.qaq.ovo.ui

import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.viewmodel.MainFrgViewModel

/**
 * @By Journey 2020/12/25
 * @Description
 */
class MainFragment:BaseVMRepositoryFragment<MainFrgViewModel>(R.layout.fragment_main) {
    override fun initViewModel()=MainFrgViewModel()
}