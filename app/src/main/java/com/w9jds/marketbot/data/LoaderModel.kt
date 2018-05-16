package com.w9jds.marketbot.data

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import java.util.concurrent.atomic.AtomicInteger

abstract class LoaderModel: ViewModel() {

    private var loadingCount: AtomicInteger = AtomicInteger(0)

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
        private set

    init {
        isLoading.value = false
    }

    fun loadStarted() {
        loadingCount.incrementAndGet()

        if (isLoading.value != true) {
            isLoading.value = true
        }
    }

    fun loadFinished() {
        loadingCount.decrementAndGet()

        if (loadingCount.get() == 0 && isLoading.value != false) {
            isLoading.value = false
        }
    }

}