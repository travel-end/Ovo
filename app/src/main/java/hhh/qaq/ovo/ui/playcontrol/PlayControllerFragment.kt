package hhh.qaq.ovo.ui.playcontrol

import android.app.Application
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseVMRepositoryFragment
import hhh.qaq.ovo.constant.Constant
import hhh.qaq.ovo.event.GlobalEventBus
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.utils.log
import hhh.qaq.ovo.viewmodel.PlayControllerViewModel
import hhh.qaq.ovo.widget.PlayPauseView

/**
 * @By Journey 2020/12/28
 * @Description
 */
class PlayControllerFragment :BaseVMRepositoryFragment<PlayControllerViewModel>(R.layout.fragment_playcontroller){
    private val TAG = "PlayControlFragment"
    private lateinit var mControllerRv:RecyclerView
    override fun initViewModel(app:Application)=PlayControllerViewModel(app)

    override fun initView() {
        super.initView()
        mControllerRv=mRootView.findViewById(R.id.view_rv)
        mControllerRv.onFlingListener = null
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(mControllerRv)
        mControllerRv.scrollToPosition(PlayManager.position())
    }
    override fun onAction() {
        super.onAction()
        GlobalEventBus.playListChanged.observeInFragment(this, Observer{
            "---PlayControlFragment---PlaylistEvent---${it?.type}".log(TAG)
            it?.let {event->
                if (event.type == Constant.PLAYLIST_QUEUE_ID) {
                    mViewModel.setMusicList()
                    mControllerRv.scrollToPosition(PlayManager.position())
                }
            }
        })
        GlobalEventBus.stateChanged.observeInFragment(this,Observer{
            "---PlayControlFragment---stateChanged---${it?.isPrepared}".log(TAG)
            it?.let {
                mRootView.findViewById<PlayPauseView>(R.id.play_controller_view).setLoading(!it.isPrepared)
                mViewModel.mIsPlaying.set(it.isPlaying)
            }
        })
        GlobalEventBus.metaChanged.observeInFragment(this,Observer{
            "---PlayControlFragment---metaChanged---${it?.music}".log(TAG)
            it?.let {
                mViewModel.mIsShowController.set(it.music != null)
                mControllerRv.scrollToPosition(PlayManager.position())
            }
        })
        mControllerRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val lm = recyclerView.layoutManager as LinearLayoutManager
                    val first = lm.findFirstVisibleItemPosition()
                    val last = lm.findLastVisibleItemPosition()
                    if (first == last && first != PlayManager.position()) {
                        // TODO: 2020/12/30
//                    PlayManager.play(first)
                    }
                }
            }
        })
    }
}