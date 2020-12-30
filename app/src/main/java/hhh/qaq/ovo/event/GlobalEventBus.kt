package hhh.qaq.ovo.event

import hhh.qaq.ovo.livedata.UnPeekLiveData


/**
 * @By Journey 2020/12/7
 * @Description
 */
object GlobalEventBus {
    val playListChanged: UnPeekLiveData<PlaylistEvent> = UnPeekLiveData()

    val stateChanged:UnPeekLiveData<StatusChangedEvent> = UnPeekLiveData()

    val metaChanged:UnPeekLiveData<MetaChangedEvent> = UnPeekLiveData()


    val searchEvent:UnPeekLiveData<String> = UnPeekLiveData()
}