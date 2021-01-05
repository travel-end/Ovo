package hhh.qaq.ovo.ui.main

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseFragment
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.viewmodel.MainFrgViewModel

/**
 * @By Journey 2020/12/25
 * @Description
 */
class MainFragment:BaseVMRepositoryFragment<MainFrgViewModel>(R.layout.fragment_main) {
    override fun initViewModel(app:Application)=MainFrgViewModel(app)
    override fun initView() {
        super.initView()
    }
}