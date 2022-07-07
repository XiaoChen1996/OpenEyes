package com.openEyes.base.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 * jetpack - ViewModel的基本组件
 * @notice:①使用ViewModel的时候,需要注意的是ViewModel不能持有View、Lifecycle、Activity引用,
 *             而且不能够包含前面内容的类,因为这样很有可能会造成内存泄露。
 *         ②AndroidViewModel:这是一个包含Application的ViewModel
 */
open class BaseViewModel(application: Application) : AndroidViewModel(application) {




    /**
     * 获取AndroidViewModel中的Application
     */
    fun getVMContext() = getApplication<Application>()

    /**
     * 当不再使用此 ViewModel 并将被销毁时，将调用此方法。
    当 ViewModel 观察到一些数据并且您需要清除此订阅以防止此 ViewModel 泄漏
     */
    override fun onCleared() {
        super.onCleared()
    }
}