package com.openEyes.base.repository

import com.openEyes.base.GlobalInstance
import com.openEyes.base.R
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * MVVM架构中的Repository基类
 */
open class BaseRepository {

    /**
     * 处理网络请求的总方法,错误信息只需要提示即可
     * suspend CoroutineScope.()->Unit : 这一段分两块看,suspend CoroutineScope. 表示可挂起的协程的扩展函数,
     *                                                ()->Unit 表示匿名函数.
     *                                                连起来就是 可挂起的协程下的匿名函数
     */
    fun netWorkRequest(dataAcquisition: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                dataAcquisition.invoke(this)
            } catch (e: Exception) {
                //e.printStackTrace()的意思是：在命令行打印异常信息在程序中出错的位置及原因
                e.printStackTrace()
                //判断e的类型以便做后续操作
                GlobalInstance.appInstance.apply {
                    val errorMsg = when (e) {
                        is SocketTimeoutException -> { //服务器响应超时
                            resources.getString(R.string.connection_time_out)
                        }
                        is UnknownHostException, is IOException -> {//IO流读取异常、 DNS 解析失败、网络中断
                            resources.getString(R.string.check_network_info)
                        }
                        else -> {
                            e.message ?: ""
                        }
                    }
                    Toasty.error(GlobalInstance.appInstance, errorMsg).show()
                }
            }
        }
    }

    /**
     * 处理网络请求的总方法,错误信息自己处理
     */
    fun netWorkWithErrorHandle(dataAcquisition: suspend CoroutineScopeWrap.() -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            val block = CoroutineScopeWrap()
            dataAcquisition.invoke(block)
            try {
                block.netWork.invoke(this)
            } catch (e: Exception) {
                e.printStackTrace()
                //判断e的类型以便做后续操作
                GlobalInstance.appInstance.apply {
                    when (e) {
                        is SocketTimeoutException -> { //服务器响应超时
                            Toasty.error(this, resources.getString(R.string.connection_time_out))
                                .show()
                        }
                        is UnknownHostException, is IOException -> {//IO流读取异常、 DNS 解析失败、网络中断
                            Toasty.error(this, resources.getString(R.string.check_network_info))
                                .show()
                        }
                        else -> {
                        }
                    }
                    block.errorHandle.invoke(e)
                }
            } finally {
                block.complete.invoke()
            }
        }
    }

    inner class CoroutineScopeWrap {
        var netWork: (suspend CoroutineScope.() -> Unit) = {}
        var errorHandle: (e: Exception) -> Unit = {}
        var complete: () -> Unit = {}
    }
}