package com.openEyes.base.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.openEyes.base.viewModel.BaseViewModel

/**
 * 需要创建Jetpack-ViewModel的BaseActivity基类
 */
abstract class BaseVMFragment<VB : ViewBinding,VM: BaseViewModel> : BaseFragment<VB>() {
    /*ViewModel对象*/
    protected lateinit var viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addLifeCycleObserver()
        configViewModel()
        observe()
        dataObtain()
    }

    /**
     * 添加LifeCycle对象
     */
    abstract fun addLifeCycleObserver()

    /**
     * 初始化ViewModel的方法
     */
    abstract fun configViewModel()

    /**
     * 注册观察者
     */
    abstract fun observe()

    /**
     * 数据获取的方法
     */
    abstract fun dataObtain()


    /**
     * 通过activity获取viewModel，跟随activity生命周期
     */
    protected fun <VM: BaseViewModel> getActivityViewModel(clazz: Class<VM>): VM =
        ViewModelProvider(this).get(clazz)
}

