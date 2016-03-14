package com.w9jds.marketbot.data;

import com.w9jds.eveapi.Client.Crest;
import com.w9jds.eveapi.Models.MarketItemBase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jeremy on 2/19/2016.
 */
public abstract class BaseDataManager implements DataLoadingSubject {

    private AtomicInteger loadingCount;
    private List<DataLoadingCallbacks> loadingCallbacks;

    public BaseDataManager() {
        // setup the API access objects
        loadingCount = new AtomicInteger(0);
    }

    public abstract void onDataLoaded(List<? extends MarketItemBase> data);
    public abstract void onDataLoaded(Object data);

    @Override
    public boolean isDataLoading() {
        return loadingCount.get() > 0;
    }

    protected void loadStarted() {
        dispatchLoadingStartedCallbacks();
    }

    protected void loadFinished() {
        dispatchLoadingFinishedCallbacks();
    }

    protected void resetLoadingCount() {
        loadingCount.set(0);
    }

    protected void incrementLoadingCount() {
        loadingCount.incrementAndGet();
    }

    protected void decrementLoadingCount() {
        loadingCount.decrementAndGet();
    }

    @Override
    public void registerCallback(DataLoadingSubject.DataLoadingCallbacks callback) {
        if (loadingCallbacks == null) {
            loadingCallbacks = new ArrayList<>(1);
        }
        loadingCallbacks.add(callback);
    }

    @Override
    public void unregisterCallback(DataLoadingSubject.DataLoadingCallbacks callback) {
        if (loadingCallbacks.contains(callback)) {
            loadingCallbacks.remove(callback);
        }
    }

    protected void dispatchLoadingStartedCallbacks() {
        if (loadingCount.intValue() == ) {
            if (loadingCallbacks != null && !loadingCallbacks.isEmpty()) {
                for (DataLoadingCallbacks loadingCallback : loadingCallbacks) {
                    loadingCallback.dataStartedLoading();
                }
            }
        }
    }

    protected void dispatchLoadingFinishedCallbacks() {
        if (loadingCount.intValue() == ) {
            if (loadingCallbacks != null && !loadingCallbacks.isEmpty()) {
                for (DataLoadingCallbacks loadingCallback : loadingCallbacks) {
                    loadingCallback.dataFinishedLoading();
                }
            }
        }
    }
}