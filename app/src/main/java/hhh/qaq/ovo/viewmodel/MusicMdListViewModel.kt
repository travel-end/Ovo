package hhh.qaq.ovo.viewmodel

import android.app.Application
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.repository.MusicListRepository

class MusicMdListViewModel(app:Application):BaseResViewModel<MusicListRepository>(app,MusicListRepository()) {

    override fun onBindViewModel() {
        super.onBindViewModel()

    }
}