package hhh.qaq.ovo.base

import android.app.Application

/**
 * @By Journey 2020/12/25
 * @Description
 */
abstract class BaseResViewModel<T:BaseRepository>(app:Application,val repository:T):BaseViewModel(app) {




}