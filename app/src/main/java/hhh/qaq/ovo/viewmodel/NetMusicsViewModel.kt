package hhh.qaq.ovo.viewmodel

import android.app.Application
import hhh.qaq.ovo.base.BaseResViewModel
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.constant.PlayConstant
import hhh.qaq.ovo.data.NetEaseRetrofitClient
import hhh.qaq.ovo.listener.RequestCallback
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.model.Playlist
import hhh.qaq.ovo.repository.NetMusicsRepository
import hhh.qaq.ovo.utils.log

/**
 * @By Journey 2021/1/6
 * @Description
 */
class NetMusicsViewModel(app: Application) :
    BaseResViewModel<NetMusicsRepository>(app, NetMusicsRepository()) {

    override fun onBindViewModel() {
        super.onBindViewModel()
        val playList = mBundle?.getParcelable<Playlist>(PlayConstant.PLAYLIST)
        playList?.let {
            when (it.type) {
                // 网易云推荐歌曲
                Constant.PLAYLIST_WY_RECOMMEND_ID -> {
                    loadRecommendMusics()
                }
            }
        }
    }

    // 获取推荐音乐
    private fun loadRecommendMusics() {
        val observable = repository.getRecommendMusics()
        NetEaseRetrofitClient.request(observable, object : RequestCallback<MutableList<Music>> {
            override fun success(result: MutableList<Music>) {
                "网易云推荐音乐数目：${result.size}".log("JG")
            }

            override fun error(msg: String) {
                "网易云推荐音乐数目：$msg".log("JG")
            }
        })
    }
}