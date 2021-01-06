package hhh.qaq.ovo.ui.player

import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import hhh.qaq.ovo.R
import hhh.qaq.ovo.base.BaseViewModelActivity
import hhh.qaq.ovo.databinding.ActivityPlayerBinding
import hhh.qaq.ovo.event.GlobalEventBus
import hhh.qaq.ovo.listener.OnPlayProgressListener
import hhh.qaq.ovo.playmedia.PlayManager
import hhh.qaq.ovo.service.PlayerService
import hhh.qaq.ovo.ui.adapter.PlayerPagerAdapter
import hhh.qaq.ovo.utils.loadBigImageView
import hhh.qaq.ovo.utils.log
import hhh.qaq.ovo.utils.setTranslateAnimation
import hhh.qaq.ovo.viewmodel.PlayerViewModel

/**
 * @By Journey 2020/12/30
 * @Description
 */
class PlayerActivity :
    BaseViewModelActivity<PlayerViewModel>(R.layout.activity_player, PlayerViewModel::class.java),
    OnPlayProgressListener {
    private val coverFragment: PlayerCoverFragment = PlayerCoverFragment()
    private val lyricFragment: PlayerLyricFragment = PlayerLyricFragment()
    private val mFragments = mutableListOf<Fragment>()
    private lateinit var mPlayerBinding :ActivityPlayerBinding
    private val mPlayingMusic = PlayManager.getPlayingMusic()
    var mIsDraggingSeekBar = false
    override fun initView() {
        super.initView()
        PlayerService.addProgressListener(this)
        mPlayerBinding = mBinding as ActivityPlayerBinding
        mPlayerBinding.detailView.setTranslateAnimation()
        initCover()
        initViewPager2()
        mViewModel.setPlayingMusicInfo(mPlayingMusic)
    }

    private fun initCover() {
        loadBigImageView(this, mPlayingMusic) {
            coverFragment.setCoverBitmap(it)
            mViewModel.setPlayBgDrawable(it)
        }
    }

    override fun onAction() {
        super.onAction()
        GlobalEventBus.stateChanged.observeInActivity(this, Observer {
            it?.let {
                mViewModel.setSecondaryProgress(it.percent)
                mViewModel.setPlayBtnStatus(it.isPlaying)
                if (it.isPlaying) {
                    coverFragment.resumeCoverRotation()
                } else {
                    coverFragment.stopCoverRotation()
                }
            }
        })
        mPlayerBinding.progressSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mIsDraggingSeekBar = true
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let { sb ->
                    val progress = sb.progress
                    lyricFragment.updateLyricProgress(progress.toLong() * 1000)
                    PlayManager.seekTo(progress * 1000)
                    mIsDraggingSeekBar = false
                }
            }
        })
    }

    private fun initViewPager2() {
        mFragments.clear()
        mFragments.add(coverFragment)
        mFragments.add(lyricFragment)
        mPlayerBinding.playerViewPager.apply {
            adapter = PlayerPagerAdapter(this@PlayerActivity, mFragments)
            offscreenPageLimit = 1
            currentItem = 0
        }
        var height = 0
        mPlayerBinding.bottomOpView.post { height = mPlayerBinding.bottomOpView.height }
        mPlayerBinding.playerViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (positionOffset <= 1 && position == 0) {
                    mPlayerBinding.detailView.translationY = height * positionOffset
                } else {
                    mPlayerBinding.detailView.translationY = height * 1f
                }
            }
        })
    }

    override fun onProgressUpdate(position: Int, duration: Int) {
//        "position1:$position".log()
        if (!mIsDraggingSeekBar) {
            mViewModel.updatePlayProgress(position / 1000)
            lyricFragment.updateLyricProgress(position.toLong())
        }
    }

    override fun onDestroy() {
        "PlayerActivity onDestroy".log("JG")
        super.onDestroy()
        PlayerService.removeProgressListener(this)
    }
}