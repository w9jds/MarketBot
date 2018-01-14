package com.w9jds.marketbot.data

interface DataLoadingSubject {
    fun isDataLoading(): Boolean

    fun registerLoadingCallback(callbacks: DataLoadingCallbacks)
    fun unregisterLoadingCallback(callbacks: DataLoadingCallbacks)

    fun registerUpdatingCallback(callbacks: DataUpdatingCallbacks)
    fun unregisterUpdatingCallback(callbacks: DataUpdatingCallbacks)

    interface DataLoadingCallbacks {
        fun dataStartedLoading()
        fun dataFinishedLoading()
        fun dataFailedLoading(errorMessage: String)
    }

    interface DataUpdatingCallbacks {
        fun dataUpdatingStarted()
        fun dataUpdatingFinished()
    }
}