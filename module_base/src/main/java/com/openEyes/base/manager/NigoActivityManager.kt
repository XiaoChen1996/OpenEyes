package com.openEyes.base.manager

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*

/**
 * NigoLive的Activity管理类
 * 知识点:1.栈是Vector的一个子类，它实现了一个标准的后进先出的栈。堆栈只定义了默认构造函数 Stack()，用来创建一个空栈。但在创建的时候注意注明Stack中的泛型类型
 *       2.WeakReference:弱引用,当垃圾收集器(GC)开始工作，不论当前内存是否足够，都会回收掉只被弱引用关联的对象,保证数据及时被移除
 */
class NigoActivityManager {

    companion object {
        /*NigoActivityManager的实例对象,用双重校验的方式创建*/
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { NigoActivityManager() }

        /*保存Activity实例的Stack对象*/
        val activityStack by lazy { Stack<WeakReference<Activity>>() }
    }


    /**
     * 检查保存Activity的堆栈中是否有该Activity
     * @param activity:检查的Activity对象
     * @return 堆栈中是否存在该Activity
     */
    private fun checkActivityIfInStack(activity: Activity): Boolean {
        activityStack.forEach { if (it.get()?.localClassName ?: "" == activity.localClassName) return true }
        return false
    }

    /**
     * 把Activity压入保存Activity的堆栈
     * @param pushActivity:要压入的Activity对象
     * @param checkIfThere:是否检查要压入的Activity是否再堆栈中存在;true:检查,若存在则不再加入,false:不检查,通通压入。默认为true
     */
    fun pushActivityToStack(pushActivity: Activity, checkIfThere: Boolean = true) {
        //如果需要检查activity是否存在且结果是存在的,不执行后续代码
        if (checkIfThere && checkActivityIfInStack(pushActivity)) return
        activityStack.push(WeakReference(pushActivity))
    }

    /**
     * 将指定的Activity从堆栈中移除
     * 堆栈中如果有多个要移除的Activity对象,以第一次找到的为准
     */
    fun removeActivityFromStack(removeActivity: Activity) {
        //如果堆栈为空或者要移除的Activity在堆栈中不存在,不执行后续的代码
        if (activityStack.empty() || !checkActivityIfInStack(removeActivity)) return
        //for循环找到要移除的Activity并移除
        activityStack.forEach {
            if (it.get()?.localClassName ?: "" == removeActivity.localClassName) {
                activityStack.remove(it)
                return
            }
        }
    }

    /**
     * 移除所有Activity
     * 从堆栈中遍历并逐一销毁,最后清空堆栈
     */
    fun finishAllActivity(){
        activityStack.forEach { it.get()?.finish() }
        activityStack.clear()
    }





}