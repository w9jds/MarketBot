package com.w9jds.marketbot.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.atomic.AtomicInteger

abstract class LoaderModel: ViewModel() {

    private var loadingCount: AtomicInteger = AtomicInteger(0)

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        isLoading.value = false
    }

    fun loading(): LiveData<Boolean> {
        return isLoading
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