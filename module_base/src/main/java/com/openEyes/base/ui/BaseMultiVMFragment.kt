package com.openEyes.base.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.openEyes.base.viewModel.BaseViewModel

/**
 * 有多个Jetpack-ViewModel的BaseActivity基类
 */
abstract class BaseMultiVMFragment<VB : ViewBinding> : BaseFragment<VB>() {

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
    protected fun <VM: BaseViewModel> getFragmentViewModel(clazz: Class<VM>): VM =
        ViewModelProvider(this).get(clazz)

    /**
     * 创建activity中的vm
     */
    @MainThread
    inline fun <reified VM : BaseViewModel> Fragment.createActivityVM(
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
    ): VM = createViewModelLazy(
        VM::class, { requireActivity().viewModelStore },
        factoryProducer ?: { requireActivity().defaultViewModelProviderFactory }
    ).value
}

