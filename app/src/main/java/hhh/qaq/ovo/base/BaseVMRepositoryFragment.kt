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
    protected lateinit var mRootView:View
    abstract fun initViewModel(app:Application):VM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vm = initViewModel(mActivity.application)
        mViewModel = ViewModelProvider(this,BaseViewModelFactory(mActivity.application,vm))[vm::class.java]
        val binding:ViewDataBinding = DataBindingUtil.inflate(inflater,layoutResId,container,false)
        binding.lifecycleOwner = this
        binding.setVariable(mViewModel.id(),mViewModel)
        binding.executePendingBindings()
        mRootView = binding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        onAction()
        mViewModel.setFragment(this)
        mViewModel.onBindViewModel()
    }

    open fun initView() {

    }
    open fun initData() {}

    open fun onAction(){}

    override fun getBundle(bundle: Bundle) {
        super.getBundle(bundle)
        mViewModel.setBundle(bundle)
    }

}