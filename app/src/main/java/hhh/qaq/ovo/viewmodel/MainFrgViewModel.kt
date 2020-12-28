package hhh.qaq.ovo.viewmodel

import android.app.Application
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.repository.MainFrgRepository
import hhh.qaq.ovo.utils.log

/**
 * @By Journey 2020/12/25
 * @Description
 */
class MainFrgViewModel(app:Application):BaseResViewModel<MainFrgRepository>(app,MainFrgRepository()) {

    fun gotoSearch() {
        nav(R.id.act_main_to_search_main)
    }
}