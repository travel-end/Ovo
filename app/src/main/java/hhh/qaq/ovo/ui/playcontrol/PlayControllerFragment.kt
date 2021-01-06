package hhh.qaq.ovo.ui.playcontrol

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.databinding.FragmentPlaycontrollerBinding
import hhh.qaq.ovo.event.GlobalEventBus
import hhh.qaq.ovo.listener.OnPlayProgressListener
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.service.PlayerService
import hhh.qaq.ovo.utils.log
import hhh.qaq.ovo.viewmodel.PlayControllerViewModel

/**
 * @By Journey 2020/12/28
 * @Description
 */
class PlayControllerFragment :
    BaseVMRepositoryFragment<PlayControllerViewModel>(R.layout.fragment_playcontroller),
    OnPlayProgressListener {
    private lateinit var mPlayControllerBinding:FragmentPlaycontrollerBinding
    override fun initViewModel(app: Application) = PlayControllerViewModel(app)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlayerService.addProgressListener(this)
    }
    override fun initView() {
        super.initView()
        mPlayControllerBinding = mBinding as FragmentPlaycontrollerBinding
        mPlayControllerBinding.viewRv.onFlingListener = null
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(mPlayControllerBinding.viewRv)
        mPlayControllerBinding.viewRv.scrollToPosition(PlayManager.position())
    }

    override fun onAction() {
        super.onAction()
        playListChangeObserve()
        playStateChangeObserve()
        playMusicChangeObserve()
        mPlayControllerBinding.viewRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val lm = recyclerView.layoutManager as LinearLayoutManager
                    val first = lm.findFirstVisibleItemPosition()
                    val last = lm.findLastVisibleItemPosition()
                    if (first == last && first != PlayManager.position()) {
                        PlayManager.play(first)
                    }
                }
            }
        })
    }

    private fun playListChangeObserve() {
        // 歌单改变
        GlobalEventBus.playListChanged.observeInFragment(this, Observer {
//            "---PlayControlFragment---PlaylistEvent---${it?.type}".log(TAG)
            it?.let { event ->
                if (event.type == Constant.PLAYLIST_QUEUE_ID) {
                    mViewModel.setMusicList()
                    "当前播放位置:${PlayManager.position()}".log("JG")
                    mPlayControllerBinding.viewRv.scrollToPosition(PlayManager.position())
                }
            }
        })
    }

    private fun playStateChangeObserve() {
        // 歌曲状态改变
        GlobalEventBus.stateChanged.observeInFragment(this, Observer {
//            "---PlayControlFragment---stateChanged---${it?.isPrepared}".log(TAG)
            it?.let {
                mPlayControllerBinding.playControllerView.setLoading(!it.isPrepared)
                mViewModel.setPlaying(it.isPlaying)
            }
        })
    }

    private fun playMusicChangeObserve() {
        // 换歌
        GlobalEventBus.metaChanged.observeInFragment(this, Observer {
//            "---PlayControlFragment---metaChanged---".log(TAG)
            it?.let {
                mViewModel.mIsShowController.set(it.music != null)// TODO 如果为null 改为默认的状态 而不是隐藏视图
                mPlayControllerBinding.viewRv.scrollToPosition(PlayManager.position())
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        PlayerService.removeProgressListener(this)
    }

    override fun onProgressUpdate(position: Int, duration: Int) {
        mPlayControllerBinding.playControllerView.setProgress(1.0f * position / duration)
    }

}