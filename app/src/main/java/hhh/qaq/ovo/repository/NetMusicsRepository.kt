package hhh.qaq.ovo.repository

import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.model.Music
import hhh.qaq.ovo.utils.MusicTransformUtil
import hhh.qaq.ovo.utils.log
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * @By Journey 2021/1/6
 * @Description
 */
class NetMusicsRepository : NetRepository() {
    fun getRecommendMusics(): Observable<MutableList<Music>> {
        return netEaseApi.recommendSongs()
            .flatMap {
                Observable.create(ObservableOnSubscribe<MutableList<Music>> { e ->
                    try {
                        if (it.code == 200) {
                            val list = mutableListOf<Music>()
                            it.data?.dailySongs?.forEach {
                                val music = Music()
                                music.mid = it.id
                                music.title = it.name
                                music.type = Constant.NETEASE
                                music.album = it.al?.name
                                music.isOnline = true
                                music.albumId = it.al?.id
                                if (it.ar != null) {
                                    var artistIds = it.ar?.get(0)?.id.toString()
                                    var artistNames = it.ar?.get(0)?.name
                                    for (j in 1 until it.ar?.size!! - 1) {
                                        artistIds += ",${it.ar?.get(j)?.id}"
                                        artistNames += ",${it.ar?.get(j)?.name}"
                                    }
                                    music.artist = artistNames
                                    music.artistId = artistIds
                                }
                                music.coverUri = MusicTransformUtil.getAlbumPic(
                                    it.al?.picUrl,
                                    Constant.NETEASE,
                                    MusicTransformUtil.PIC_SIZE_NORMAL
                                )
                                music.coverBig = MusicTransformUtil.getAlbumPic(
                                    it.al?.picUrl,
                                    Constant.NETEASE,
                                    MusicTransformUtil.PIC_SIZE_BIG
                                )
                                music.coverSmall = MusicTransformUtil.getAlbumPic(
                                    it.al?.picUrl,
                                    Constant.NETEASE,
                                    MusicTransformUtil.PIC_SIZE_SMALL
                                )
                                list.add(music)
                            }
                            e.onNext(list)
                            e.onComplete()
                        } else {
                            "获取推荐音乐异常：${it.message}".log("JG")
                            e.onError(Throwable(it.message))
                        }
                    } catch (ep: Exception) {
                        e.onError(ep)
                    }
                })
            }
    }
}