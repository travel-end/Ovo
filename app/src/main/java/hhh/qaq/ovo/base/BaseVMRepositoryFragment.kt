package hhh.qaq.ovo.base

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

/**
 * @By Journey 2020/12/25
 * @Description
 */
abstract class BaseVMRepositoryFragment<VM:BaseResViewModel<*>>(@LayoutRes private val layoutResId:Int):BaseFragment() {
    protected lateinit var mViewModel:VM
    protected var mRootView:View?=null
    private var isNavigationViewInit:Boolean = false//为了处理Navigation每次返回都刷新生命周期的问题
    abstract fun initViewModel(app:Application):VM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView==null) {
//            "onCreateView".log("BaseVMRepositoryFragment")
            val vm = initViewModel(mActivity.application)
            mViewModel = ViewModelProvider(this,BaseViewModelFactory(mActivity.application,vm))[vm::class.java]
            val binding:ViewDataBinding = DataBindingUtil.inflate(inflater,layoutResId,container,false)
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
//            "onViewCreated".log("BaseVMRepositoryFragment")
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

    open fun onAction(){}

    private fun initBundle() {
        arguments?.let {
            getBundle(it)
        }
    }
    open fun getBundle(bundle: Bundle) {
        mViewModel.setBundle(bundle)
    }


}