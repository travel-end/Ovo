package hhh.qaq.ovo.base

import android.app.Application
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

/**
 * @By Journey 2020/12/25
 * @Description 需要带Repository的ViewModel实现
 */
abstract class BaseVMRepositoryActivity<VM:BaseResViewModel<*>>(@LayoutRes private val layoutResId:Int):BaseActivity() {
    protected lateinit var mViewModel:VM
    protected lateinit var mBinding:ViewDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm = initViewModel(application)
        mBinding = DataBindingUtil.setContentView(this,layoutResId)
        mViewModel = ViewModelProvider(this,BaseViewModelFactory(application,vm))[vm::class.java]
        mBinding.lifecycleOwner = this
        mBinding.setVariable(mViewModel.id(),mViewModel)
        mBinding.executePendingBindings()

        initView()
        initData()
        mViewModel.onBindViewModel()
    }
    abstract fun initViewModel(app: Application):VM

    open fun initView() {

    }
    open fun initData() {

    }
}