package hhh.qaq.ovo.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import hhh.qaq.ovo.utils.log

/**
 * @By Journey 2020/12/28
 * @Description
 */
open class BaseViewModelFragment<VM:BaseViewModel>(@LayoutRes private val layoutResId:Int,private val clazz:Class<VM>):BaseFragment() {
    protected lateinit var mViewModel:VM
    protected var mRootView:View?=null
    private var isNavigationViewInit:Boolean = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mRootView==null) {
            mViewModel = ViewModelProvider(this)[clazz]
            val binding :ViewDataBinding= DataBindingUtil.inflate(inflater,layoutResId,container,false)
            binding.lifecycleOwner = this
            binding.setVariable(mViewModel.id(),mViewModel)
            binding.executePendingBindings()
            mRootView = binding.root
        }
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isNavigationViewInit) {
            initBundle()
            initView()
            initData()
            onAction()
            mViewModel.onBindViewModel()
            isNavigationViewInit = true
        }
    }

    open fun initView() {}

    open fun initData() {}

    open fun onAction() {}

    private fun initBundle() {
        arguments?.let {
            getBundle(it)
        }
    }
    open fun getBundle(bundle: Bundle) {
        mViewModel.setBundle(bundle)
    }
}