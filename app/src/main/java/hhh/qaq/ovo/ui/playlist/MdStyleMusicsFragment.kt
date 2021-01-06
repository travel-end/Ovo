package hhh.qaq.ovo.ui.playlist

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.viewmodel.NetMusicsViewModel

/**
 * @By Journey 2021/1/6
 * @Description
 */
class MdStyleMusicsFragment:BaseVMRepositoryFragment<NetMusicsViewModel>(R.layout.fragment_mdstyle_play_list) {
    override fun initViewModel(app: Application)=NetMusicsViewModel(app)
}