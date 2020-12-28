package hhh.qaq.ovo.viewmodel

import android.app.Application
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.repository.PlayControllerRepository

/**
 * @By Journey 2020/12/28
 * @Description
 */
class PlayControllerViewModel(app:Application):BaseResViewModel<PlayControllerRepository>(app,PlayControllerRepository()) {
}