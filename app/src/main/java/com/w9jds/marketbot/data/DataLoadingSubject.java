package com.w9jds.marketbot.data;

/**
 * Created by Jeremy on 2/19/2016.
 */
public interface DataLoadingSubject {
    boolean isDataLoading();

    void registerLoadingCallback(DataLoadingCallbacks callbacks);
    void unregisterLoadingCallback(DataLoadingCallbacks callbacks);

    void registerUpdatingCallback(DataUpdatingCallbacks callbacks);
    void unregisterUpdatingCallback(DataUpdatingCallbacks callbacks);

    interface DataLoadingCallbacks {
        void dataStartedLoading();
        void dataFinishedLoading();
        void dataFailedLoading(String errorMessage);
    }

    interface DataUpdatingCallbacks {
        void dataUpdatingStarted();
        void dataUpdatingFinished();
    }
}