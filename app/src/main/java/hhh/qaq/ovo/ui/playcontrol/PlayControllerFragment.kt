package hhh.qaq.ovo.ui.playcontrol

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.viewmodel.PlayControllerViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
class PlayControllerFragment :BaseVMRepositoryFragment<PlayControllerViewModel>(R.layout.fragment_playcontroller){
    override fun initViewModel(app:Application)=PlayControllerViewModel(app)
}