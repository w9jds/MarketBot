package com.w9jds.marketbot.data

interface DataLoadingSubject {
    fun isDataLoading(): Boolean

    fun registerLoadingCallback(callbacks: DataLoadingCallbacks)
    fun unregisterLoadingCallback(callbacks: DataLoadingCallbacks)

    interface DataLoadingCallbacks {
        fun dataStartedLoading()
        fun dataFinishedLoading()
        fun dataFailedLoading(errorMessage: String)
    }
}