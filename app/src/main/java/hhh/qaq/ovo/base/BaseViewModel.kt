package hhh.qaq.ovo.base

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import hhh.qaq.ovo.BR
import hhh.qaq.ovo.listener.VariableId
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class BaseViewModel(app: Application) : AndroidViewModel(app), VariableId {

    val hasNav: MutableLiveData<Boolean> = MutableLiveData()

    private var mFragment: BaseFragment? = null

    override fun id() = BR.vm

    open fun onBindViewModel() {

    }

    fun setFragment(fragment: BaseFragment?) {
        mFragment = fragment
    }


    protected fun request(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block.invoke()
            } catch (e:Exception) {

            }
        }
    }

    fun nav(actionId: Int, bundle: Bundle? = null) {
        mFragment?.let {
            val nav = NavHostFragment.findNavController(it)
            if (bundle != null) {
                nav.navigate(actionId, bundle)
            } else {
                nav.navigate(actionId)
            }
        }
    }

    fun startAct(clazz: Class<*>, vararg data: Pair<String, Any?>) {
        val app = getApplication<Application>()
        val intent = Intent(app, clazz)
        data.forEach {
            when (it.second) {
                is Boolean -> intent.putExtra(it.first, it.second as Boolean)
                is Byte -> intent.putExtra(it.first, it.second as Byte)
                is Int -> intent.putExtra(it.first, it.second as Int)
                is Short -> intent.putExtra(it.first, it.second as Short)
                is Long -> intent.putExtra(it.first, it.second as Long)
                is Float -> intent.putExtra(it.first, it.second as Float)
                is Double -> intent.putExtra(it.first, it.second as Double)
                is Char -> intent.putExtra(it.first, it.second as Char)
                is String -> intent.putExtra(it.first, it.second as String)
                is Serializable -> intent.putExtra(it.first, it.second as Serializable)
                is Parcelable -> intent.putExtra(it.first, it.second as Parcelable)
            }
        }
        app.startActivity(intent)
    }
}