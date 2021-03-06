package hhh.qaq.ovo.base

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import hhh.qaq.ovo.BR
import hhh.qaq.ovo.listener.VariableId
import hhh.qaq.ovo.utils.log
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class BaseViewModel(app: Application) : AndroidViewModel(app), VariableId {
    var isFinish = MutableLiveData<Boolean>()
    var onBackPress = MutableLiveData<Boolean>()
    var mBundle: Bundle? = null
    private var mNavController: NavController? = null
    override fun id() = BR.vm

    open fun onBindViewModel() {

    }

    fun setBundle(bundle: Bundle) {
        mBundle = bundle
    }

    protected fun request(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block.invoke()
            } catch (e: Exception) {
                "request exception:${e.message}".log()
            }
        }
    }

    fun nav(v: View?, actionId: Int, bundle: Bundle? = null) {
        v?.let {
            mNavController = Navigation.findNavController(it)
            if (bundle != null) {
                mNavController?.navigate(actionId, bundle)
            } else {
                mNavController?.navigate(actionId)
            }
        }
    }

    fun nav(f: BaseFragment?, actionId: Int, bundle: Bundle? = null) {
        f?.let {
            mNavController = NavHostFragment.findNavController(it)
            if (bundle != null) {
                mNavController?.navigate(actionId, bundle)
            } else {
                mNavController?.navigate(actionId)
            }
        }
    }


    fun navigationUp() {
        mNavController?.navigateUp()
    }

    fun popBackup() {

    }

    fun finish() {
        isFinish.value=true
    }

    fun onBackPressed() {
        onBackPress.value=true
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