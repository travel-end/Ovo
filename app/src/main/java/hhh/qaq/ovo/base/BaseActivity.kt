package hhh.qaq.ovo.base

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import hhh.qaq.ovo.IMusicService
import hhh.qaq.ovo.playmedia.PlayManager

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class BaseActivity:AppCompatActivity(), ServiceConnection {
    private var mToken:PlayManager.ServiceToken?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindService()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        PlayManager.mService = IMusicService.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        PlayManager.mService = null
    }
    private fun bindService() {
        mToken = PlayManager.bindToService(this,this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mToken != null) {
            PlayManager.unbindFromService(mToken)
            mToken = null
        }
    }
}