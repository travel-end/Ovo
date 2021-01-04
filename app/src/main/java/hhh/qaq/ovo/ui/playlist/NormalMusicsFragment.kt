package hhh.qaq.ovo.ui.playlist

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.viewmodel.MusicListViewModel

class NormalMusicsFragment:BaseVMRepositoryFragment<MusicListViewModel>(R.layout.fragment_normal_play_list) {
    override fun initViewModel(app: Application)=MusicListViewModel(app)
}