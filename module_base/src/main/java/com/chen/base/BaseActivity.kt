package com.chen.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * BaseActivity
 */
class BaseActivity<VB: ViewBinding> : AppCompatActivity() {
    /*ViewBinding对象*/
    protected lateinit var binding:VB

    /**
     * 当活动第一启动的时候,调用该方法,可以在此时完成活动的初始化工作
     * @param savedInstanceState:该参数可以为空(null),也可以是之前调用 onSaveInstanceState()方法保存的状态信息
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBindingForActivity(layoutInflater)
    }

    @Suppress("UNCHECKED_CAST")
    private fun getViewBindingForActivity(layoutInflater: LayoutInflater): VB {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    /**
     * 当处于停止状态的活动需要再次展现给用户的时候,触发该方法
     */
    override fun onRestart() {
        super.onRestart()
    }

    /**
     * 该方法的触发表示所属活动将被展现给用户
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * 当一个活动和用户发生交互的时候,触发该方法
     */
    override fun onResume() {
        super.onResume()
    }

    /**
     * 当一个正在前台运行的活动因为其他的活动需要前台运行而转入后台运行的时候,触发该方法。
     * 这时候需要将该活动的状态持久化,比如正在编辑的数据库记录等
     */
    override fun onPause() {
        super.onPause()
    }

    /**
     * 当一个活动不再需要展示给用户的时候,触发该方法。
     * 如果内存紧张,系统会直接结束这个活动,而不会触发 onStop 方法。
     * 所以保存状态信息是应该在onPause时做,而不是在onStop时做。
     * 活动如果没有在前台运行,都将被停止或者Linux管理进程为了给新的活动预留足够的存储空间而随时结束这些活动
     * 因此对于开发者来说,在设计应用程序的时候,必须牢记这一原则
     * 在一些情况下,onPause方法或许是活动触发的最后的方法,因此开发者需要在这个时候保存需要保存的信息
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * 当活动销毁的时候,触发该方法
     * 和 onStop 方法一样,如果内存紧张,系统会直接结束这个活动而不会触发该方法
     */
    override fun onDestroy() {
        super.onDestroy()
    }

}