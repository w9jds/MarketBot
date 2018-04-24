package com.w9jds.marketbot.data

import android.content.Context
import android.net.ConnectivityManager
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicInteger

abstract class DataManager(private val context: Context): DataLoadingSubject {

    private var loadingCount: AtomicInteger = AtomicInteger(0)
    private var loadingCallbacks: MutableList<DataLoadingSubject.DataLoadingCallbacks>? = null

    override fun isDataLoading(): Boolean {
        return loadingCount.get() > 0
    }

    protected fun loadStarted() {
        dispatchLoadingStartedCallbacks()
    }

    protected fun loadFinished() {
        dispatchLoadingFinishedCallbacks()
    }

    protected fun loadFailed(errorMessage: String) {
        dispatchLoadingFailedCallbacks(errorMessage)
    }

    protected fun resetLoadingCount() {
        loadingCount.set(0)
    }

    protected fun incrementLoadingCount() {
        loadingCount.incrementAndGet()
    }

    protected fun incrementLoadingCount(count: Int) {
        loadingCount.set(count)
    }

    protected fun decrementLoadingCount() {
        loadingCount.decrementAndGet()
    }

    fun isConnected(): Boolean {
        val manager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = manager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    override fun registerLoadingCallback(callback: DataLoadingSubject.DataLoadingCallbacks) {
        if (loadingCallbacks == null) {
            loadingCallbacks = ArrayList(1)
        }

        loadingCallbacks?.add(callback)
    }

    override fun unregisterLoadingCallback(callback: DataLoadingSubject.DataLoadingCallbacks) {
        if (loadingCallbacks!!.contains(callback)) {
            loadingCallbacks!!.remove(callback)
        }
    }

    private fun dispatchLoadingStartedCallbacks() {
        if (loadingCount.toInt() == 0) {
            if (loadingCallbacks != null && loadingCallbacks?.isNotEmpty()!!) {
                for (loadingCallback in loadingCallbacks!!) {
                    loadingCallback.dataStartedLoading()
                }
            }
        }
    }

    private fun dispatchLoadingFinishedCallbacks() {
        if (loadingCount.toInt() == 0) {
            if (loadingCallbacks?.isNotEmpty()!!) {
                for (loadingCallback in loadingCallbacks!!) {
                    loadingCallback.dataFinishedLoading()
                }
            }
        }
    }

    private fun dispatchLoadingFailedCallbacks(errorMessage: String) {
        if (loadingCallbacks != null && !loadingCallbacks!!.isEmpty()) {
            for (loadingCallback in loadingCallbacks!!) {
                loadingCallback.dataFailedLoading(errorMessage)
            }
        }
    }

}