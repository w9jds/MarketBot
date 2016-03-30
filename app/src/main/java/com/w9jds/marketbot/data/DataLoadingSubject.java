package com.w9jds.marketbot.data;

/**
 * Created by Jeremy on 2/19/2016.
 */
public interface DataLoadingSubject {
    boolean isDataLoading();

    void registerCallback(DataLoadingCallbacks callbacks);
    void unregisterCallback(DataLoadingCallbacks callbacks);

    void registerCallback(DataUpdatingCallbacks callbacks);
    void unregisterCallback(DataUpdatingCallbacks callbacks);

    interface DataLoadingCallbacks {
        void dataStartedLoading();
        void dataFinishedLoading();
    }

    interface DataUpdatingCallbacks {
        void dataUpdatingStarted();
        void dataUpdatingFinished();
    }
}