package hhh.qaq.ovo.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import hhh.qaq.ovo.utils.log

/**
 * @By Journey 2020/12/25
 * @Description
 */
open class BaseViewModelActivity<VM:BaseViewModel>(@LayoutRes private val layoutResId:Int,private val clazz: Class<VM>):BaseActivity() {
    protected lateinit var mViewModel:VM
    protected lateinit var mBinding:ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this,layoutResId)
        mViewModel = ViewModelProvider(this)[clazz]
        mBinding.setVariable(mViewModel.id(),mViewModel)
        mBinding.lifecycleOwner = this
        mBinding.executePendingBindings()
        initView()
        initData()
        onAction()
        mViewModel.onBindViewModel()
    }

    open fun initView() {

    }

    open fun initData() {

    }

    open fun onAction() {}

}