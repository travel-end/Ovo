package hhh.qaq.ovo.base

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class BaseActivity:AppCompatActivity(), ServiceConnection {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
    }

    override fun onServiceDisconnected(name: ComponentName?) {
    }
}