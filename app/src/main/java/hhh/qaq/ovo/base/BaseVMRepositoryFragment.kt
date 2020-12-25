package hhh.qaq.ovo.base

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
    protected lateinit var mBinding:ViewDataBinding
    abstract fun initViewModel():VM
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vm = initViewModel()
        mViewModel = ViewModelProvider(this,BaseViewModelFactory(vm))[vm::class.java]
        mBinding = DataBindingUtil.inflate(inflater,layoutResId,container,false)
        mBinding.lifecycleOwner = this
        mBinding.setVariable(mViewModel.id(),mViewModel)
        mBinding.executePendingBindings()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        mViewModel.onBindViewModel()
    }

    open fun initView() {

    }
    open fun initData() {}

}